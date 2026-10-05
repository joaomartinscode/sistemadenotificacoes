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
    public void send(Notification notification){
        List<String> to = new ArrayList<>();
        List<String> cc = new ArrayList<>();
        List<String> bcc = new ArrayList<>();

        for (Recipient recipient : notification.getRecipients()){
            if (recipient.getType().equals(RecipientType.TO)){
                to.add(recipient.getDestination());
            } else if (recipient.getType().equals(RecipientType.CC)){
                cc.add(recipient.getDestination());
            } else if (recipient.getType().equals(RecipientType.BCC)){
                bcc.add(recipient.getDestination());
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
