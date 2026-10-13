package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String, Object> payload = new HashMap<>();

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChannelType channelType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private List<Recipient> recipients;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "failure_reason")
    private String failureReason;

    public Notification() {
        this.recipients = new ArrayList<>();
        this.status = DeliveryStatus.CREATED;
        this.createdAt = LocalDateTime.now();
        this.payload = new HashMap<>();
    }

    public Notification(String subject, String content, ChannelType channelType) {
        this();
        this.content = content;
        this.channelType = channelType;
        setSubject(subject);
    }

    public Notification(Map<String, Object> payload, String content, ChannelType channelType) {
        this();
        this.content = content;
        this.channelType = channelType;
        if (payload != null) {
            this.payload = new HashMap<>(payload);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, Object> getPayload() {
        if (this.payload == null) {
            this.payload = new HashMap<>();
        }
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload != null ? payload : new HashMap<>();
    }

    @Transient
    public String getSubject() {
        if (this.payload != null && this.payload.containsKey("subject")) {
            Object s = this.payload.get("subject");
            return s != null ? s.toString() : null;
        }
        return null;
    }

    public void setSubject(String subject) {
        if (this.payload == null) {
            this.payload = new HashMap<>();
        }
        if (subject != null) {
            this.payload.put("subject", subject);
        } else {
            this.payload.remove("subject");
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }
}
