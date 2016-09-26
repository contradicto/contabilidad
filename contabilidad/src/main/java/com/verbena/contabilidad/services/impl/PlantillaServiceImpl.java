package com.verbena.contabilidad.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.verbena.contabilidad.entity.Plantilla;
import com.verbena.contabilidad.services.PlantillaService;

@Service("plantillaService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PlantillaServiceImpl implements PlantillaService {

	@Autowired
	PlantillaDao dao;

	public Plantilla addPlantilla(Plantilla plantilla) {
		return dao.save(plantilla);
	}

	public List<Plantilla> getPlantillas() {
		return dao.queryAll();
	}

	public void deletePlantilla(Plantilla plantilla) {
		dao.delete(plantilla);
	}
	
	public Plantilla findByNombre(String nombre) {
		return dao.findByNombre(nombre);
	}
}
