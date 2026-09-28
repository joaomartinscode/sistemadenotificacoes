package com.example.sistemadenotificacoes.services;

import com.example.sistemadenotificacoes.dao.NotificationRepository;
import com.example.sistemadenotificacoes.delivery.DeliveryChannel;
import com.example.sistemadenotificacoes.domain.ChannelType;
import com.example.sistemadenotificacoes.domain.Notification;
import com.example.sistemadenotificacoes.domain.Recipient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final List<DeliveryChannel> deliveryChannels;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, List<DeliveryChannel> deliveryChannels) {
        this.notificationRepository = notificationRepository;
        this.deliveryChannels = deliveryChannels;
    }

    @Transactional
    public Notification processNotification(List<Recipient> recipients, String subject, String content, ChannelType channelType) {
        Notification notification = new Notification(subject, content, channelType);

        if (recipients != null) {
            for (Recipient recipient : recipients) {
                notification.addRecipient(recipient);
            }
        }

        submitNotification(notification);

        return notificationRepository.save(notification);
    }

    private void submitNotification(Notification notification) {
        try {
            DeliveryChannel channel = getDeliveryChannel(notification.getChannelType());
            channel.send(notification);
            notification.markSent();
        } catch (Exception e) {
            notification.markFailed(e.getMessage());
        }
    }

    private DeliveryChannel getDeliveryChannel(ChannelType channelType) {
        for (DeliveryChannel channel : deliveryChannels) {
            if (channel.supports(channelType)) {
                return channel;
            }
        }
        throw new IllegalArgumentException("Nenhum canal de entrega suporta o tipo: " + channelType);
    }

    public List<Notification> findAll() {
        return notificationRepository.findAllOrderedByCreatedAtDesc();
    }

    public Notification findById(Integer id) {
        return notificationRepository.findById(id).orElse(null);
    }
}
