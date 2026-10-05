package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dao;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.Notification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {

    @Query("SELECT n FROM Notification n ORDER BY n.createdAt DESC")
    List<Notification> findAllOrderedByCreatedAtDesc();
}
