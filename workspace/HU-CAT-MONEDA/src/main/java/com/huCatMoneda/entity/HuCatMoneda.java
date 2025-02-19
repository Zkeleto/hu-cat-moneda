package com.huCatMoneda.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
@Entity
@Data
@IdClass(HuCatMonedaId.class) // Define la clave primaria compuesta
public class HuCatMoneda {
	

    @Id
    @Column(length = 4, nullable = false)
    private int numCia;

    @Id
    @Column(length = 3, nullable = false)
    private String claveMoneda;

    @Column(length = 30)
    private String descripcion;

    @Column(length = 5)
    private String simbolo;

    @Column(length = 5)
    private String abreviacion;

    @Column(length = 1)
    private String monedaCorriente;

    @Column(length = 1)
    private String status;
    
 // Constructor vacío (sin parámetros)
    public HuCatMoneda() {}

    // Constructor con parámetros
    public HuCatMoneda(int numCia, String claveMoneda) {
        this.numCia = numCia;
        this.claveMoneda = claveMoneda;
    }
	
}
