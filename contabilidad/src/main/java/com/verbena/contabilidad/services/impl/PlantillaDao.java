package com.verbena.contabilidad.services.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.verbena.contabilidad.entity.Plantilla;

@Repository
public class PlantillaDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<Plantilla> queryAll() {
		Query query = em.createQuery("SELECT l FROM Plantilla l");
		List<Plantilla> result = query.getResultList();
		return result;
	}
	@Transactional(readOnly = true)
	public Plantilla findByNombre(String nombre) {
		Query query = em.createQuery("SELECT l FROM Plantilla l WHERE l.nombre = :nombre");
		try{
			return (Plantilla)query.setParameter("nombre", nombre).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public Plantilla get(Integer id) {
		return em.find(Plantilla.class, id);
	}

	@Transactional
	public Plantilla save(Plantilla plantilla) {
		em.persist(plantilla);
		em.flush();
		return plantilla;
	}

	@Transactional
	public void delete(Plantilla plantilla) {
		Plantilla r = get(plantilla.getId());
		if(r != null) {
			em.remove(r);
		}
	}

}
