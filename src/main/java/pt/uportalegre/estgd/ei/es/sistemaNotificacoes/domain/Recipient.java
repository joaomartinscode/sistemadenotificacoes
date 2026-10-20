package pt.uportalegre.estgd.ei.es.sistemaNotificacoes.domain;

import jakarta.persistence.*;

@Entity
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String destination;

    @Column(name = "type")
    private String type;

    public Recipient() {
    }

    public Recipient(String destination) {
        this(destination, "TO");
    }

    public Recipient(String destination, String type) {
        this.destination = destination;
        this.type = type != null ? type : "TO";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
