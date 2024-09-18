package br.com.lbomfim.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.com.lbomfim.dao.generic.IGenericDAO;
import br.com.lbomfim.domain.Computador;

/**
 * @author Lucas Bomfim 
 */

public class ComputadorDAO implements IGenericDAO<Computador> {
	public static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ExemploJPA");

	@Override
	public Computador cadastrar(Computador computador) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.persist(computador);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new PersistenceException("Erro ao cadastrar o aluno: " + 
                    (computador != null ? computador.getDescricao() : "Computador desconhecido") + ". Detalhes: " + erro.getMessage());
		} finally {
			entityManager.close();
		}

		return computador;
	}

	@Override
	public Computador consultar(Long id) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Computador computador = null;

		try {
			computador = entityManager.find(Computador.class, id);
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new PersistenceException("Erro ao consultar o computador de ID: " + id + ". Detalhes: " + erro.getMessage());
		} finally {
			entityManager.close();
		}

		return computador;
	}

	@Override
	public Computador atualizar(Computador computador) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			entityManager.merge(computador);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new PersistenceException("Erro ao atualizar o aluno: " + 
                    (computador != null ? computador.getDescricao() : "Computador desconhecido") + ". Detalhes: " + erro.getMessage());
		} finally {
			entityManager.close();
		}

		return computador;
	}

	@Override
	public List<Computador> buscarTodas() throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		try {
			TypedQuery<Computador> query = entityManager.createQuery("SELECT c FROM Computador c", Computador.class);
			return query.getResultList();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new PersistenceException("Erro ao buscar a lista de computadores. Detalhes: " + erro.getMessage());
		} finally {
			entityManager.close();
		}
	}

	@Override
	public Integer excluir(Long id) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		Computador computador = null;

		try {
			computador = entityManager.find(Computador.class, id);
			entityManager.getTransaction().begin();
			entityManager.remove(computador);
			entityManager.getTransaction().commit();
		} catch (Exception erro) {
			if (entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new PersistenceException("Erro ao excluir o computador de ID: " + id + ". Detalhes: " + erro.getMessage());
		} finally {
			entityManager.close();
		}

		return 1;
	}
}
