package com.huCatMoneda.entity;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@SuppressWarnings("serial")
@Data
public class HuCatMonedaId implements Serializable {

	  private int numCia;
	  private String claveMoneda;
	  
	  // Constructor vacío
	  public HuCatMonedaId() {}

	  // Constructor con parámetros
	  public HuCatMonedaId(int numCia, String claveMoneda) {
		  this.numCia = numCia;
	      this.claveMoneda = claveMoneda;
	  }
	  
	  // Sobrescribir equals y hashCode para comparación de objetos
	  // equals compara dos objetos para ver si son iguales
	  // se necesita para comparar 2 instancias de HuCatMonedaId para ver si tienen el mismo valor en numCia y claveMoneda
	  @Override
	  public boolean equals(Object o) {
		  if (this == o) return true;
		  if (o == null || getClass() != o.getClass()) return false;
	      HuCatMonedaId monedaId = (HuCatMonedaId) o;
	      return numCia == monedaId.numCia && Objects.equals(claveMoneda, monedaId.claveMoneda);
	  }

	  //Genera un código hash único basado en los valores de los campos del objeto.
	  //se usa para almacenar entidades en caches internas para evitar duplicados
	  @Override
	  public int hashCode() {
		  return Objects.hash(numCia, claveMoneda);
	  }
}
