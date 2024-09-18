package br.com.lbomfim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.com.lbomfim.dao.CursoDAO;
import br.com.lbomfim.dao.generic.IGenericDAO;
import br.com.lbomfim.domain.Curso;

/**
 * @author Lucas Bomfim 
 */

public class CursoTest {
	IGenericDAO<Curso> dao = new CursoDAO();

	@Test
	public void CRUDCursoTest() throws Exception {
		// CRIAR
		Curso curso = new Curso();
		curso.setCodigo("A2");
		curso.setDescricao("CURSO FROTEND");
		curso.setNome("JAVASCRIPT");
		
		// CADASTRAR
		curso = dao.cadastrar(curso);
		assertNotNull(curso);
		
		// CONSULTAR
		Curso cursoBD = dao.consultar(curso.getId());
		assertEquals(curso.getCodigo(), cursoBD.getCodigo());
		
		// ATUALIZAR
		cursoBD.setNome("TYPESCRIPT");
		Curso cursoAtualizado = dao.atualizar(cursoBD);
		assertNotNull(cursoAtualizado);
		
		// BUSCAR
		List<Curso> cursos = dao.buscarTodas();
		assertTrue(cursos.size() >= 1);
		
		// EXCLUIR
		Integer excluir = dao.excluir(cursoBD.getId());
		assertTrue(excluir == 1);
	}
}
