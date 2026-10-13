package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.Recipient;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.RecipientType;

public record RecipientDTO(
        String destination,
        RecipientType type
) {
    public static RecipientDTO fromEntity(Recipient recipient) {
        if (recipient == null) return null;
        return new RecipientDTO(recipient.getDestination(), recipient.getType());
    }

    public Recipient toEntity() {
        return new Recipient(this.destination, this.type);
    }
}
