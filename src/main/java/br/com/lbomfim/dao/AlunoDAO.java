package br.com.lbomfim.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.lbomfim.dao.generic.IGenericDAO;
import br.com.lbomfim.domain.Aluno;

/**
 * @author Lucas Bomfim 
 */

public class AlunoDAO implements IGenericDAO<Aluno> {
	public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ExemploJPA");

	@Override
	public Aluno cadastrar(Aluno aluno) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(aluno);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new PersistenceException("Erro ao cadastrar o aluno: " + 
                    (aluno != null ? aluno.getNome() : "Aluno desconhecido") + ". Detalhes: " + erro.getMessage());
		} finally {
			entityManager.close();
		}

		return aluno;
	}

	@Override
	public Aluno consultar(Long id) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Aluno aluno = null;

		try {
			aluno = entityManager.find(Aluno.class, id);
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new PersistenceException("Erro ao consultar o aluno de ID: " + id + ". Detalhes: " + erro.getMessage());
		} finally {
			entityManager.close();
		}

		return aluno;
	}

	@Override
	public Aluno atualizar(Aluno aluno) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(aluno);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new PersistenceException("Erro ao atualizar o aluno: " + 
                    (aluno != null ? aluno.getNome() : "Aluno desconhecido") + ". Detalhes: " + erro.getMessage());
		} finally {
			entityManager.close();
		}

		return aluno;
	}

	@Override
	public List<Aluno> buscarTodas() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			TypedQuery<Aluno> query = entityManager.createQuery("SELECT a FROM Aluno a", Aluno.class);
			return query.getResultList();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new PersistenceException("Erro ao buscar a lista de alunos. Detalhes: " + erro.getMessage());
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Integer excluir(Long id) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Aluno aluno = null;

		try {
			aluno = entityManager.find(Aluno.class, id);
			entityManager.getTransaction().begin();
			entityManager.remove(aluno);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new PersistenceException("Erro ao excluir o aluno de ID: " + id + ". Detalhes: " + erro.getMessage());
		} finally {
			entityManager.close();
		}

		return 1;
	}
}
