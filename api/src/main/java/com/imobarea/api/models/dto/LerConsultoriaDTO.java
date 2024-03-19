package com.imobarea.api.models.dto;

import lombok.Getter;

@Getter
public class LerConsultoriaDTO extends CriarConsultoriaDTO{
    private long id;
    
    private String cpfCliente;

    public LerConsultoriaDTO(String creciAgente, String data, String hora, String cpfCliente, long id) {
        super(creciAgente, data, hora);
        this.cpfCliente = cpfCliente;
        this.id = id;
    }
}
