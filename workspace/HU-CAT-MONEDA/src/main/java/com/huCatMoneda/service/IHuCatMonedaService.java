package com.huCatMoneda.service;

import java.util.List;
import java.util.Optional;

import com.huCatMoneda.entity.HuCatMoneda;
import com.huCatMoneda.entity.HuCatMonedaId;

public interface IHuCatMonedaService {
	
	public List<HuCatMoneda> listar();
	public HuCatMoneda guardar(HuCatMoneda moneda);
	public Optional<HuCatMoneda> actualizar(HuCatMoneda moneda);
	public void eliminar(HuCatMonedaId id);
	public Optional<HuCatMoneda> obtenerPorId(HuCatMonedaId id);
	
}
