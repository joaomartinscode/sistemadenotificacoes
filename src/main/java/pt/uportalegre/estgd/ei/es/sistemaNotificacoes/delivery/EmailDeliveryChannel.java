package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.delivery;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.ChannelType;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.Notification;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.Recipient;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.RecipientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailDeliveryChannel implements DeliveryChannel{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public boolean supports(ChannelType channelType) {
        return ChannelType.EMAIL.equals(channelType);
    }

    @Override
    public void send(Notification notification) {
        SimpleMailMessage message = new SimpleMailMessage();
        String subject = notification.getSubject();
        message.setSubject(subject != null ? subject : "");
        message.setText(notification.getContent());

        String[] to = getRecipientsByType(notification, RecipientType.TO);
        String[] cc = getRecipientsByType(notification, RecipientType.CC);
        String[] bcc = getRecipientsByType(notification, RecipientType.BCC);

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

    private String[] getRecipientsByType(Notification notification, RecipientType type) {
        List<String> result = new ArrayList<>();
        if (notification.getRecipients() != null) {
            for (Recipient r : notification.getRecipients()) {
                if (r.getType() == type) {
                    result.add(r.getDestination());
                }
            }
        }
        return result.toArray(new String[0]);
    }
}
