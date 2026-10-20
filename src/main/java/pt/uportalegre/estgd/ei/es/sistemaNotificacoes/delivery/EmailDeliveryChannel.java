package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.delivery;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EmailDeliveryChannel implements DeliveryChannel {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean supports(ChannelType channelType) {
        return channelType == ChannelType.EMAIL;
    }

    @Override
    public void send(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = notification.getSubject() != null ? notification.getSubject() : "";
        message.setSubject(subject);
        message.setText(notification.getContent());

        String[] to = getRecipientsByType(notification, "TO");
        String[] cc = getRecipientsByType(notification, "CC");
        String[] bcc = getRecipientsByType(notification, "BCC");

        if (to.length > 0) {
            message.setTo(to);
        }
        if (cc.length > 0) {
            message.setCc(cc);
        }
        if (bcc.length > 0) {
            message.setBcc(bcc);
        }

        mailSender.send(message);
    }

    private String[] getRecipientsByType(Notification notification, String type) {
        List<String> result = new ArrayList<>();
        List<Recipient> recipients = notification.getRecipients();
        if (recipients != null) {
            for (Recipient r : recipients) {
                String recipientType = r.getType();
                if (type.equalsIgnoreCase(recipientType) || ("TO".equalsIgnoreCase(type) && recipientType == null)) {
                    result.add(r.getDestination());
                }
            }
        }
        return result.toArray(new String[0]);
    }
}
