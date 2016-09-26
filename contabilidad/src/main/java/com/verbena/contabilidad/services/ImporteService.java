package com.verbena.contabilidad.services;

import com.verbena.contabilidad.entity.Importe;

import java.util.Date;
import java.util.List;

public interface ImporteService {

	Importe addImporte(Importe importe);

	List<Importe> getImportes();

	void deleteImporte(Importe importe);
	public List<Importe> getImportebyFecha(Date fecha, Boolean entrada);
	public Double getTotalMes(Date fecha, Boolean entrada, Boolean extra);
	public Double getTotalMesFijo(Date fecha);
}
