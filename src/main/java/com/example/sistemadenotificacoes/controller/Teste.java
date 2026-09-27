package com.example.sistemadenotificacoes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Teste {

    @GetMapping("/teste")
    public String teste() {
        return "Olá mundo";
    }
}
