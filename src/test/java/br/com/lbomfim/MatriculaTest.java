package br.com.lbomfim;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Instant;

import org.junit.Test;

import br.com.lbomfim.dao.AlunoDAO;
import br.com.lbomfim.dao.ComputadorDAO;
import br.com.lbomfim.dao.IMatriculaDAO;
import br.com.lbomfim.dao.MatriculaDAO;
import br.com.lbomfim.dao.CursoDAO;
import br.com.lbomfim.dao.generic.IGenericDAO;
import br.com.lbomfim.domain.Aluno;
import br.com.lbomfim.domain.Computador;
import br.com.lbomfim.domain.Curso;
import br.com.lbomfim.domain.Matricula;

/**
 * @author Lucas Bomfim 
 */

public class MatriculaTest {
	
	private IMatriculaDAO matriculaDAO;
	
	private IGenericDAO<Curso> cursoDAO;
	
	private IGenericDAO<Aluno> alunoDAO;
	
	public MatriculaTest() {
		matriculaDAO = new MatriculaDAO();
		cursoDAO = new CursoDAO();
		alunoDAO = new AlunoDAO();
		new ComputadorDAO();
	}

	@Test
	public void cadastrar() throws Exception {
		Curso curso = adicionarCurso("A1");
		Aluno aluno = adicionarAluno("A1");
		Matricula matricula = new Matricula();
		
		// CRIAR
		matricula.setCodigo("A1");
		matricula.setDataMatricula(Instant.now());
		matricula.setStatus("ATIVA");
		matricula.setValor(5000d);
		matricula.setCurso(curso);
		matricula.setAluno(aluno);
		
		// CADASTRAR
		aluno.setMatricula(matricula);
		matricula = matriculaDAO.cadastrar(matricula);
	}
	
	private Computador criarComputador(String codigo) {
		Computador computador_1 = new Computador();
		computador_1.setCodigo(codigo);
		computador_1.setDescricao("COMPUTADOR 1");
		return computador_1;
	}

	private Aluno adicionarAluno(String codigo) throws Exception {
		Computador computador_1 = criarComputador("A1");
		Computador computador_2 = criarComputador("A2");
		
		Aluno aluno = new Aluno();
		aluno.setCodigo(codigo);
		aluno.setNome("LUCAS");
		aluno.add(computador_1);
		aluno.add(computador_2);
		
		return alunoDAO.cadastrar(aluno);
	}

	private Curso adicionarCurso(String codigo) throws Exception {
		Curso curso = new Curso();
		curso.setCodigo(codigo);
		curso.setDescricao("BACKEND");
		curso.setNome("JAVA");
		
		return cursoDAO.cadastrar(curso);
	}
}