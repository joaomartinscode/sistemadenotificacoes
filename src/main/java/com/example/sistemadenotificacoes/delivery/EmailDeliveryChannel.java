package com.example.sistemadenotificacoes.delivery;

import com.example.sistemadenotificacoes.domain.Notification;
import com.example.sistemadenotificacoes.domain.Recipient;
import com.example.sistemadenotificacoes.domain.RecipientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailDeliveryChannel implements DeliveryChannel{
    private final JavaMailSender mailSender;

    @Autowired
    public EmailDeliveryChannel(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Override
    public void send(Notification notification){
        List<String> to = new ArrayList<String>();
        List<String> cc = new ArrayList<String>();
        List<String> bcc = new ArrayList<String>();

        for (Recipient recipient : notification.getRecipients()){
            if (recipient.getType().equals(RecipientType.TO)){
                to.add(recipient.getAddress());
            } else if (recipient.getType().equals(RecipientType.CC)){
                cc.add(recipient.getAddress());
            } else if (recipient.getType().equals(RecipientType.BCC)){
                bcc.add(recipient.getAddress());
            }
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(notification.getSubject());
        message.setText(notification.getContent());

        if(!to.isEmpty()){
            message.setTo(to.toArray(new String[0]));
        }

        if(!cc.isEmpty()){
            message.setCc(cc.toArray(new String[0]));
        }

        if(!bcc.isEmpty()){
            message.setBcc(bcc.toArray(new String[0]));
        }

        mailSender.send(message);
    }
}
