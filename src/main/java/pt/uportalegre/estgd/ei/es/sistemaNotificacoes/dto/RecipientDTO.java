package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.dto;

import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.Recipient;
import pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain.RecipientType;

public class RecipientDTO {
    private String destination;
    private RecipientType type;

    public RecipientDTO(String destination, RecipientType type) {
        this.destination = destination;
        this.type = type;
    }

    public static RecipientDTO fromEntity(Recipient recipient) {
        if (recipient == null) return null;
        return new RecipientDTO(recipient.getDestination(), recipient.getType());
    }

    public Recipient toEntity() {
        return new Recipient(this.destination, this.type);
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public RecipientType getType() {
        return type;
    }

    public void setType(RecipientType type) {
        this.type = type;
    }
}
