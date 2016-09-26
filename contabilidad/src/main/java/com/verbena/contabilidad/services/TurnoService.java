package com.verbena.contabilidad.services;

import java.util.Date;
import java.util.List;

import com.verbena.contabilidad.entity.Plantilla;
import com.verbena.contabilidad.entity.Turno;

public interface TurnoService {

	Turno addTurno(Turno turno);

	List<Turno> getTurnos();

	void deleteTurno(Turno turno);
	
	public Turno findByNombre(String nombre);
	
	public List<Turno> findByFecha(Date fecha);
	
	public Turno findByPlantilla(Plantilla plantilla);
}
