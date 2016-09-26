package com.verbena.contabilidad.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.verbena.contabilidad.entity.Importe;
import com.verbena.contabilidad.services.ImporteService;

@Service("importeService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ImporteServiceImpl implements ImporteService {

	@Autowired
	ImporteDao dao;

	public Importe addImporte(Importe importe) {
		return dao.save(importe);
	}

	public List<Importe> getImportes() {
		return dao.queryAll();
	}

	public void deleteImporte(Importe importe) {
		dao.delete(importe);
	}

	public List<Importe> getImportebyFecha(Date fecha, Boolean entrada){
		List<Importe> tempList = new ArrayList<Importe>();
		List<Importe> resultList = new ArrayList<Importe>();
		tempList = dao.getImportebyFecha(fecha);
		
		for(Importe importe: tempList){
			if(importe.getEntrada().equals(entrada)){
				resultList.add(importe);
			}
		}
		
		return resultList;
	}
	
	public Double getTotalMes(Date fecha, Boolean entrada, Boolean extra){
		return dao.getTotalMes(fecha, entrada, extra);
	}
	public Double getTotalMesFijo(Date fecha) {
		return dao.getTotalMesFijo(fecha);
	}
}
