package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.ChannelType;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.RecipientType;

import java.util.ArrayList;
import java.util.List;

public record EmailNotificationRequestDTO(
        ChannelType channelType,
        String to,
        String cc,
        String bcc,
        String subject,
        String content
) {
    public NotificationDTO toNotificationDTO() {
        List<RecipientDTO> recipientDTOs = new ArrayList<>();
        addDestinations(recipientDTOs, this.to, RecipientType.TO);
        addDestinations(recipientDTOs, this.cc, RecipientType.CC);
        addDestinations(recipientDTOs, this.bcc, RecipientType.BCC);

        if (recipientDTOs.isEmpty()) {
            throw new IllegalArgumentException("A notificação tem de ter pelo menos um destinatário.");
        }

        return new NotificationDTO(
                this.subject,
                this.content,
                this.channelType,
                recipientDTOs
        );
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
}
