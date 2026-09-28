package com.example.sistemadenotificacoes.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String subject;

    private String content;

    @Enumerated(EnumType.STRING)
    private ChannelType channelType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "notification_id")
    private List<Recipient> recipients;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime sentAt;

    private String failureReason;

    public Notification() {
        this.recipients = new ArrayList<>();
        this.status = DeliveryStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public Notification(String subject, String content, ChannelType channelType) {
        this.subject = subject;
        this.content = content;
        this.channelType = channelType;
        this.recipients = new ArrayList<>();
        this.status = DeliveryStatus.CREATED;
        this.createdAt = LocalDateTime.now();
    }

    public void addRecipient(Recipient recipient) {
        if (recipient != null) {
            this.recipients.add(recipient);
        }
    }

    public void markSent() {
        this.status = DeliveryStatus.SENT;
        this.sentAt = LocalDateTime.now();
        this.failureReason = null;
    }

    public void markFailed(String failureReason) {
        this.status = DeliveryStatus.FAILED;
        this.sentAt = null;
        this.failureReason = failureReason;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
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
}
