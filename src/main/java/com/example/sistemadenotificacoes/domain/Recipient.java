package com.example.sistemadenotificacoes.domain;

import jakarta.persistence.*;

@Entity
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String address;

    @Enumerated(EnumType.STRING)
    private RecipientType type;

    public Recipient() {
    }

    public Recipient(String address, RecipientType type) {
        this.address = address;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RecipientType getType() {
        return type;
    }

    public void setType(RecipientType type) {
        this.type = type;
    }
}
