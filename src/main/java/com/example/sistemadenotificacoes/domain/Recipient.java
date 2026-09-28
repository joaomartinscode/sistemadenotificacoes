package com.example.sistemadenotificacoes.domain;

import jakarta.persistence.*;

@Entity
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String destination;

    @Enumerated(EnumType.STRING)
    private RecipientType type;

    public Recipient() {
    }

    public Recipient(String destination, RecipientType type) {
        this.destination = destination;
        this.type = type;
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

    public RecipientType getType() {
        return type;
    }

    public void setType(RecipientType type) {
        this.type = type;
    }
}
