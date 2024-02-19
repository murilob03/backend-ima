package com.imobarea.api.imoveis;

import jakarta.persistence.*;

@Table(name = "imoveis")
@Entity
public class imoveis {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idImobiliaria;

    private String nome;

    private String localizacao;
    
    private String preco;

    private String nQuartos;

    private String nVagas;
}
