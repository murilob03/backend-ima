package com.imobarea.api.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
public class Consultoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cpf")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "creci")
    private AgenteImobiliario agenteImobiliario;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date dataHora;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public AgenteImobiliario getAgenteImobiliario() {
        return agenteImobiliario;
    }

    public void setAgenteImobiliario(AgenteImobiliario agenteImobiliario) {
        this.agenteImobiliario = agenteImobiliario;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }
}
