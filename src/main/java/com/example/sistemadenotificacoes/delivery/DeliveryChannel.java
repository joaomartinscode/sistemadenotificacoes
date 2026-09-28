package com.example.sistemadenotificacoes.delivery;

import com.example.sistemadenotificacoes.domain.Notification;

public interface DeliveryChannel {
    void send(Notification notification);
}
