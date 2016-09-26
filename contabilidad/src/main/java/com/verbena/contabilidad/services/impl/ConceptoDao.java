package com.verbena.contabilidad.services.impl;

import com.verbena.contabilidad.entity.Concepto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ConceptoDao {

	@PersistenceContext
	private EntityManager em;

	@Transactional(readOnly = true)
	public List<Concepto> queryAll() {
		Query query = em.createQuery("SELECT l FROM Concepto l");
		List<Concepto> result = query.getResultList();
		return result;
	}
	@Transactional(readOnly = true)
	public Concepto findByDescripcion(String descripcion) {
		Query query = em.createQuery("SELECT l FROM Concepto l WHERE l.descripcion = :descripcion");
		try{
			return (Concepto)query.setParameter("descripcion", descripcion).getSingleResult();
		}catch(Exception e){
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	public Concepto get(Integer id) {
		return em.find(Concepto.class, id);
	}

	@Transactional
	public Concepto save(Concepto concepto) {
		em.persist(concepto);
		em.flush();
		return concepto;
	}

	@Transactional
	public void delete(Concepto concepto) {
		Concepto r = get(concepto.getId());
		if(r != null) {
			em.remove(r);
		}
	}

}
