package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.delivery;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.ChannelType;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.Notification;

public interface DeliveryChannel {
    boolean supports(ChannelType channelType);
    void send(Notification notification);
}
