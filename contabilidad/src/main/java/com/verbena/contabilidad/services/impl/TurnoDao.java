package com.verbena.contabilidad.services.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.verbena.contabilidad.entity.Plantilla;
import com.verbena.contabilidad.entity.Turno;

@Repository
public class TurnoDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<Turno> queryAll() {
		Query query = em.createQuery("SELECT l FROM Turno l");
		List<Turno> result = query.getResultList();
		return result;
	}
	@Transactional(readOnly = true)
	public Turno findByNombre(String turno) {
		Query query = em.createQuery("SELECT l FROM Turno l WHERE l.turno = :turno");
		try{
			return (Turno)query.setParameter("turno", turno).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public List<Turno> findByFecha(Date fecha) {
		Query query = em.createQuery("SELECT l FROM Turno l WHERE l.fecha = :fecha");
		try{
			return query.setParameter("fecha", fecha).getResultList();
		}catch(Exception e){
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public Turno findByPlantilla(Plantilla plantilla) {
		Query query = em.createQuery("SELECT l FROM Turno l WHERE l.plantilla = :plantilla");
		try{
			return (Turno)query.setParameter("plantilla", plantilla).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public Turno get(Integer id) {
		return em.find(Turno.class, id);
	}

	@Transactional
	public Turno save(Turno turno) {
		em.persist(turno);
		em.flush();
		return turno;
	}

	@Transactional
	public void delete(Turno turno) {
		Turno r = get(turno.getId());
		if(r != null) {
			em.remove(r);
		}
	}

}
