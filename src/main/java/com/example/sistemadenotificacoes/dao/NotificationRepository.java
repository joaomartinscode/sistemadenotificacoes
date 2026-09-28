package com.example.sistemadenotificacoes.dao;

import com.example.sistemadenotificacoes.domain.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n ORDER BY n.createdAt DESC")
    Iterable<Notification> findAllOrderedByCreatedAtDesc();
}
