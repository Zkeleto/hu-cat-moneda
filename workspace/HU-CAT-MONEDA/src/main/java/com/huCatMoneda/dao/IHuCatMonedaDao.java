package com.huCatMoneda.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.huCatMoneda.entity.HuCatMoneda;
import com.huCatMoneda.entity.HuCatMonedaId;

public interface IHuCatMonedaDao extends JpaRepository<HuCatMoneda, HuCatMonedaId> {

}
