package com.example.sistemadenotificacoes.services;

import com.example.sistemadenotificacoes.dao.NotificationRepository;
import com.example.sistemadenotificacoes.delivery.DeliveryChannel;
import com.example.sistemadenotificacoes.domain.ChannelType;
import com.example.sistemadenotificacoes.domain.Notification;
import com.example.sistemadenotificacoes.domain.Recipient;
import com.example.sistemadenotificacoes.domain.RecipientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private DeliveryChannel deliveryChannel;

    @Transactional
    public Notification processNotification(String to, String cc, String bcc, String subject, String content, ChannelType channelType){
        Notification notification = createNotification(to, cc, bcc, subject, content, channelType);

        submitNotification(notification);

        return notificationRepository.save(notification);
    }

    private Notification createNotification(String to, String cc, String bcc, String subject, String content, ChannelType channelType){
        Notification notification = new Notification(subject, content, channelType);

        addRecipients(notification, to, RecipientType.TO);
        addRecipients(notification, cc, RecipientType.CC);
        addRecipients(notification, bcc, RecipientType.BCC);

        return notification;
    }

    private void addRecipients(Notification notification, String emails, RecipientType type){
        if(emails == null || emails.isEmpty()) {
            return;
        }

        String[] emailList = emails.split(",");

        for(String email : emailList){
            String clearEmail = email.trim();
            notification.addRecipient(new Recipient(clearEmail, type));
        }
    }

    private void submitNotification(Notification notification){
        try {
            deliveryChannel.send(notification);
            notification.markSent();
        } catch (Exception e) {
            notification.markFailed(e.getMessage());
        }
    }

    public List<Notification> findAll() {
        Iterable<Notification> iterable = notificationRepository.findAllOrderedByCreatedAtDesc();
        List<Notification> notifications = new ArrayList<Notification>();
        for( Notification notification : iterable){
            notifications.add(notification);
        }
        return notifications;
    }

    public Notification findById(Integer id){
        return notificationRepository.findById(id).orElse(null);
    }

}
