package com.verbena.contabilidad.services;

import java.util.List;

import com.verbena.contabilidad.entity.Plantilla;

public interface PlantillaService {

	Plantilla addPlantilla(Plantilla plantilla);

	List<Plantilla> getPlantillas();

	void deletePlantilla(Plantilla plantilla);
	
	public Plantilla findByNombre(String nombre);
}
