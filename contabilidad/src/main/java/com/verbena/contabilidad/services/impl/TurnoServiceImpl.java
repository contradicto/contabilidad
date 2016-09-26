package com.verbena.contabilidad.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.verbena.contabilidad.entity.Plantilla;
import com.verbena.contabilidad.entity.Turno;
import com.verbena.contabilidad.services.TurnoService;

@Service("turnoService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class TurnoServiceImpl implements TurnoService {

	@Autowired
	TurnoDao dao;

	public Turno addTurno(Turno turno) {
		return dao.save(turno);
	}

	public List<Turno> getTurnos() {
		return dao.queryAll();
	}

	public void deleteTurno(Turno turno) {
		dao.delete(turno);
	}
	
	public Turno findByNombre(String nombre) {
		return dao.findByNombre(nombre);
	}

	public List<Turno> findByFecha(Date fecha) {
		return dao.findByFecha(fecha);
	}
	
	public Turno findByPlantilla(Plantilla plantilla) {
		return dao.findByPlantilla(plantilla);
	}
}
