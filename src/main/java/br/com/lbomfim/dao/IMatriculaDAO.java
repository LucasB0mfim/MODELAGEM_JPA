package br.com.lbomfim.dao;

import java.util.List;

import br.com.lbomfim.domain.Curso;
import br.com.lbomfim.domain.Matricula;

/**
 * @author Lucas Bomfim 
 */

public interface IMatriculaDAO {
	
	Matricula cadastrar(Matricula mat);
	
	Matricula consultarPorCodigoCurso(String codigoCurso);
	
	Matricula consultarPorCurso(Curso curso);
	
	Matricula consultarPorCodigoCursoCriteria(String codigoCurso);
	
	Matricula consultarPorCursoCriteria(Curso curso);
	
	List<Matricula> buscarTodas();
}
