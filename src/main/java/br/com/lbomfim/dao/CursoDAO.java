package br.com.lbomfim.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import br.com.lbomfim.dao.generic.IGenericDAO;
import br.com.lbomfim.domain.Curso;

/**
 * @author Lucas Bomfim
 */

public class CursoDAO implements IGenericDAO<Curso> {

	public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ExemploJPA");

	@Override
	public Curso cadastrar(Curso curso) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(curso);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new Exception("Erro ao cadastrar o curso:" + curso.getNome() + "." + "Decrição do erro: " + erro);
		} finally {
			entityManager.close();
		}

		return curso;
	}

	@Override
	public Curso consultar(Long id) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Curso curso = null;

		try {
			curso = entityManager.find(Curso.class, id);
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new Exception("Erro ao cadastrar o curso:" + curso.getNome() + "." + "Decrição do erro: " + erro);
		} finally {
			entityManager.close();
		}

		return curso;
	}

	@Override
	public Curso atualizar(Curso curso) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(curso);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new Exception("Erro ao atualizar o curso: " + erro);
		} finally {
			entityManager.close();
		}

		return curso;
	}

	@Override
	public List<Curso> buscarTodas() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			TypedQuery<Curso> query = entityManager.createQuery("SELECT c FROM Curso c", Curso.class);
			return query.getResultList();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new Exception("Erro ao buscar a lista de cursos: " + erro);
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Integer excluir(Long id) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Curso curso = null;

		try {
			curso = entityManager.find(Curso.class, id);
			entityManager.getTransaction().begin();
			entityManager.remove(curso);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new Exception("Erro ao buscar a lista de cursos: " + erro);
		} finally {
			entityManager.close();
		}

		return 1;
	}

}
