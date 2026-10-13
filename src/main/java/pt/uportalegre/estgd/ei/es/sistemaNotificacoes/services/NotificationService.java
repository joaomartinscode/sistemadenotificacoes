package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.services;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dao.NotificationRepository;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.delivery.DeliveryChannel;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.ChannelType;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.DeliveryStatus;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto.NotificationDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private List<DeliveryChannel> deliveryChannels;

    public NotificationDTO processNotification(NotificationDTO dto) {
        Notification notification = dto.toEntity();
        notification.setStatus(DeliveryStatus.CREATED);

        Notification saved = notificationRepository.save(notification);

        submitNotification(saved);

        Notification updated = notificationRepository.save(saved);

        return NotificationDTO.fromEntity(updated);
    }

    private void submitNotification(Notification notification) {
        try {
            DeliveryChannel channel = getDeliveryChannel(notification.getChannelType());
            channel.send(notification);
            notification.setStatus(DeliveryStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
            notification.setFailureReason(null);
        } catch (Exception e) {
            notification.setStatus(DeliveryStatus.FAILED);
            notification.setSentAt(null);
            notification.setFailureReason(e.getMessage());
        }
    }

    private DeliveryChannel getDeliveryChannel(ChannelType channelType) {
        for (DeliveryChannel channel : deliveryChannels) {
            if (channel.supports(channelType)) {
                return channel;
            }
        }
        throw new IllegalArgumentException("Canal de entrega não suportado: " + channelType);
    }

    @Transactional
    public List<NotificationDTO> findAll() {
        List<Notification> notifications = notificationRepository.findAllOrderedByCreatedAtDesc();
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        for (Notification n : notifications) {
            notificationDTOS.add(NotificationDTO.fromEntity(n));
        }
        return notificationDTOS;
    }

    @Transactional
    public NotificationDTO findById(Integer id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        return NotificationDTO.fromEntity(notification);
    }
}

