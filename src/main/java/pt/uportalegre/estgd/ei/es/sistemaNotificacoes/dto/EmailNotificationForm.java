package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.ChannelType;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.RecipientType;

import java.util.ArrayList;
import java.util.List;

public class EmailNotificationForm {
    private ChannelType channelType;
    private String to;
    private String cc;
    private String bcc;
    private String subject;
    private String content;

    public EmailNotificationForm() {
    }

    public NotificationDTO toDTO() {
        List<RecipientDTO> recipientDTOs = new ArrayList<>();
        addDestinations(recipientDTOs, this.to, RecipientType.TO);
        addDestinations(recipientDTOs, this.cc, RecipientType.CC);
        addDestinations(recipientDTOs, this.bcc, RecipientType.BCC);

        NotificationDTO dto = new NotificationDTO();
        dto.setChannelType(this.channelType);
        dto.setSubject(this.subject);
        dto.setContent(this.content);
        dto.setRecipients(recipientDTOs);
        return dto;
    }

    private void addDestinations(List<RecipientDTO> list, String rawDestinations, RecipientType type) {
        if (rawDestinations == null || rawDestinations.trim().isEmpty()) {
            return;
        }
        for (String dest : rawDestinations.split(",")) {
            String clean = dest.trim();
            if (!clean.isEmpty()) {
                list.add(new RecipientDTO(clean, type));
            }
        }
    }

    public ChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(ChannelType channelType) {
        this.channelType = channelType;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
