package com.huCatMoneda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.huCatMoneda.dao.IHuCatMonedaDao;
import com.huCatMoneda.entity.HuCatMoneda;
import com.huCatMoneda.entity.HuCatMonedaId;

@Service
public class HuCatMonedaServiceImpl implements IHuCatMonedaService {
	
	@Autowired
	private IHuCatMonedaDao dao;
	
	@Override
	public List<HuCatMoneda> listar(){
		
		return dao.findAll(Sort.by(Sort.Direction.ASC,"numCia"));
	}

	@Override
	public HuCatMoneda guardar(HuCatMoneda moneda) {
		return dao.save(moneda);
		
	}

	@Override
	public Optional<HuCatMoneda> actualizar(HuCatMoneda moneda) {
		if(dao.existsById(new HuCatMonedaId(moneda.getNumCia(),moneda.getClaveMoneda()))) {
			return Optional.of(dao.save(moneda));
		}else {
			return Optional.empty();		}
		
	}
	
	
	 @Override
	    public void eliminar(HuCatMonedaId id) {
	        if (dao.existsById(id)) {
	            dao.deleteById(id);
	        } else {
	            throw new RuntimeException("Moneda no encontrada con ID: " + id);
	        }
	    }
	 
	 @Override
	 public Optional<HuCatMoneda> obtenerPorId(HuCatMonedaId id) {
	     return dao.findById(id);
	 }
	
}
