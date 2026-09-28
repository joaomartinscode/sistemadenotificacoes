package com.example.sistemadenotificacoes.delivery;

import com.example.sistemadenotificacoes.domain.ChannelType;
import com.example.sistemadenotificacoes.domain.Notification;

public interface DeliveryChannel {
    boolean supports(ChannelType channelType);
    void send(Notification notification);
}
