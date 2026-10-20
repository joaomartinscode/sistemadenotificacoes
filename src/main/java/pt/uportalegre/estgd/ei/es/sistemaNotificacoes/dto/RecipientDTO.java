package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.Recipient;

public record RecipientDTO(
        String destination,
        String type
) {
    public RecipientDTO(String destination) {
        this(destination, "TO");
    }

    public static RecipientDTO fromEntity(Recipient recipient) {
        if (recipient == null) {
            return null;
        }
        return new RecipientDTO(recipient.getDestination(), recipient.getType());
    }

    public Recipient toEntity() {
        return new Recipient(this.destination, this.type);
    }
}
