package com.verbena.contabilidad.services.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.verbena.contabilidad.entity.Importe;

@Repository
public class ImporteDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<Importe> queryAll() {
		Query query = em.createQuery("SELECT l FROM Importes l");
		List<Importe> result = query.getResultList();
		return result;
	}

	@Transactional(readOnly = true)
	public Importe get(Integer id) {
		return em.find(Importe.class, id);
	}

	@Transactional
	public Importe save(Importe importe) {
		em.persist(importe);
		em.flush();
		return importe;
	}

	@Transactional
	public void delete(Importe importe) {
		Importe r = get(importe.getId());
		if(r != null) {
			em.remove(r);
		}
	}

	@Transactional(readOnly = true)
	public List<Importe> getImportebyFecha(Date fecha) {
		Query query = em.createQuery("SELECT l FROM Importe l where l.fecha =:fecha");
		List<Importe> result = ((Query) query.setParameter("fecha",fecha)).getResultList();
		return result;
	}
	
	@Transactional(readOnly = true)
	public Double getTotalMes(Date fecha, Boolean entrada, Boolean extra) {
		Query query = em.createQuery("SELECT sum(i.cantidad) as cantidad FROM Importe i, Concepto C WHERE month(i.fecha)=:mes AND YEAR(i.fecha)=:anio AND i.entrada =:entrada and i.extra=:extra AND I.concepto_id=C.id AND C.gastoFijo=false");
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(fecha);
		query.setParameter("mes", Integer.parseInt(new SimpleDateFormat("MM").format(cal.getTime())));
		query.setParameter("anio", Integer.parseInt(new SimpleDateFormat("YYYY").format(cal.getTime())));
		query.setParameter("entrada",entrada);
		query.setParameter("extra",extra);
		Double result =  (Double) query.getSingleResult();
		if(result==null){
			result = 0.0D;
		}
		
		return result;
	}
	
	@Transactional(readOnly = true)
	public Double getTotalMesFijo(Date fecha) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(fecha);
		Query query = em.createQuery("SELECT sum(I.cantidad) FROM Importe I, Concepto C WHERE month(I.fecha)=:mes AND YEAR(I.fecha)=:anio AND I.entrada = false and I.extra=false AND I.concepto_id=C.id AND C.gastoFijo=true");
		query.setParameter("mes", Integer.parseInt(new SimpleDateFormat("MM").format(cal.getTime())));
		query.setParameter("anio", Integer.parseInt(new SimpleDateFormat("YYYY").format(cal.getTime())));
		Double result = (Double) query.getSingleResult();
		if(result==null){
			result = 0.0D;
		}
		return result;
	}
}
