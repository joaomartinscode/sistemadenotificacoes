package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.ChannelType;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.DeliveryStatus;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.Notification;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.Recipient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationDTO {
    private Integer id;
    private String subject;
    private String content;
    private ChannelType channelType;
    private DeliveryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private String failureReason;
    private List<RecipientDTO> recipients;

    public NotificationDTO() {
        this.recipients = new ArrayList<>();
    }

    public NotificationDTO(Integer id, String subject, String content, ChannelType channelType,
                           DeliveryStatus status, LocalDateTime createdAt, LocalDateTime sentAt,
                           String failureReason, List<RecipientDTO> recipients) {
        this.id = id;
        this.subject = subject;
        this.content = content;
        this.channelType = channelType;
        this.status = status;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
        this.failureReason = failureReason;
        this.recipients = recipients;
    }

    public static NotificationDTO fromEntity(Notification notification) {
        if (notification == null) return null;

        List<RecipientDTO> recipientDTOs = new ArrayList<>();
        if (notification.getRecipients() != null) {
            for (Recipient r : notification.getRecipients()) {
                recipientDTOs.add(RecipientDTO.fromEntity(r));
            }
        }

        return new NotificationDTO(
                notification.getId(),
                notification.getSubject(),
                notification.getContent(),
                notification.getChannelType(),
                notification.getStatus(),
                notification.getCreatedAt(),
                notification.getSentAt(),
                notification.getFailureReason(),
                recipientDTOs
        );
    }

    public Notification toEntity(){
        Notification entity = new Notification(this.subject, this.content, this.channelType);

        entity.setId(this.id);
        if (this.status != null) {
            entity.setStatus(this.status);
        }

        List<Recipient> recipientEntities = new ArrayList<>();
        if (this.recipients != null) {
            for (RecipientDTO dto : this.recipients) {
                recipientEntities.add(dto.toEntity());
            }
        }
        entity.setRecipients(recipientEntities);
        return entity;
    }

    public List<RecipientDTO> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<RecipientDTO> recipients) {
        this.recipients = recipients;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
