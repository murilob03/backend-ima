package com.imobarea.api.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Consultoria {

    public Consultoria(Cliente cliente, AgenteImobiliario agenteImobiliario, String data, String hora) {
        this.cliente = cliente;
        this.agenteImobiliario = agenteImobiliario;
        this.data = data;
        this.hora = hora;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "cpf")
    @Column(nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "creci")
    @Column(nullable = false)
    private AgenteImobiliario agenteImobiliario;

    @NotBlank 
    @Pattern(regexp = "\\d{4}/\\d{2}/\\d{2}")
    @Column(nullable = false)
    private String data;

    @NotBlank 
    @Pattern(regexp = "\\d{2}:\\d{2}") 
    @Column(nullable = false)
    private String hora;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
