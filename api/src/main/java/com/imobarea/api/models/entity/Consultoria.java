package com.imobarea.api.models.entity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Consultoria {

    public Consultoria(Cliente cliente, AgenteImobiliario agenteImobiliario, Date dataHora) {
        this.cliente = cliente;
        this.agenteImobiliario = agenteImobiliario;
        this.dataHora = dataHora;
    }

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