package br.com.lbomfim.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.com.lbomfim.domain.Curso;
import br.com.lbomfim.domain.Matricula;

/**
 * @author Lucas Bomfim 
 */

public class MatriculaDAO implements IMatriculaDAO {
	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ExemploJPA");

	@Override
	public Matricula cadastrar(Matricula matricula) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		entityManager.getTransaction().begin();
		entityManager.persist(matricula);
		entityManager.getTransaction().commit();
		 
		entityManager.close();
		return matricula;
	}

	@Override
	public Matricula consultarPorCodigoCurso(String codigoCurso) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT m FROM Matricula m ");
		sb.append("INNER JOIN Curso c on c = m.curso ");
		sb.append("WHERE c.codigo = :codigoCurso");
		
		entityManager.getTransaction().begin();
		TypedQuery<Matricula> query = entityManager.createQuery(sb.toString(), Matricula.class);
		query.setParameter("codigoCurso", codigoCurso);
		Matricula matricula = query.getSingleResult();    
		
		entityManager.close();
		
		return matricula;
	}

	@Override
	public Matricula consultarPorCurso(Curso curso) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT m FROM Matricula m ");
		sb.append("INNER JOIN Curso c on c = m.curso ");
		sb.append("WHERE c = :curso");
		
		entityManager.getTransaction().begin();
		TypedQuery<Matricula> query = entityManager.createQuery(sb.toString(), Matricula.class);
		query.setParameter("curso", curso);
		Matricula matricula = query.getSingleResult();    
		
		entityManager.close();
		
		return matricula;
	}
	
	@Override
	public Matricula consultarPorCodigoCursoCriteria(String codigoCurso) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Matricula> query = builder.createQuery(Matricula.class);
		Root<Matricula> root = query.from(Matricula.class);
		Join<Object, Object> join = root.join("curso", JoinType.INNER);
		query.select(root).where(builder.equal(join.get("codigo"), codigoCurso));
		
		TypedQuery<Matricula> tpQuery = 
				entityManager.createQuery(query);
		Matricula matricula = tpQuery.getSingleResult();    
		
		entityManager.close();
		
		return matricula;
	}

	@Override
	public Matricula consultarPorCursoCriteria(Curso curso) {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Matricula> query = builder.createQuery(Matricula.class);
		Root<Matricula> root = query.from(Matricula.class);
		Join<Object, Object> join = root.join("curso", JoinType.INNER);
		query.select(root).where(builder.equal(join, curso));
		
		TypedQuery<Matricula> tpQuery = 
				entityManager.createQuery(query);
		Matricula matricula = tpQuery.getSingleResult();    
		
		entityManager.close();
		
		return matricula;
	}

	@Override
	public List<Matricula> buscarTodas() {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Matricula> query = builder.createQuery(Matricula.class);
		Root<Matricula> root = query.from(Matricula.class);
		query.select(root);
		
		TypedQuery<Matricula> tpQuery = entityManager.createQuery(query);
		List<Matricula> list = tpQuery.getResultList();
		
		entityManager.close();
		
		return list;
	}
}