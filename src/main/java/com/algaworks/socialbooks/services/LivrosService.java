package com.algaworks.socialbooks.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.socialbooks.domain.Comentario;
import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.ComentariosRepository;
import com.algaworks.socialbooks.repository.LivrosRepository;
import com.algaworks.socialbooks.services.exceptions.LivroNaoEncontradoException;

@Service
public class LivrosService {

	@Autowired
	private LivrosRepository livrosrepository;
	
	@Autowired
	private ComentariosRepository comentariosRepository;
	
	public List<Livro> listar(){
		return livrosrepository.findAll();
		
	}
	
	public Livro buscar(Long id){
		Livro livro  = livrosrepository.findOne(id);
		
		if (livro == null){
			throw new LivroNaoEncontradoException("O livro não foi encontrado");
		}
		
		return livro;
	}
	
	public Livro salvar(Livro livro){
		livro.setId(null);
		return livro = livrosrepository.save(livro);		
	}
	
	public void deletar(Long id){
		try {
			livrosrepository.delete(id);
		} catch (EmptyResultDataAccessException  e) {
			throw new LivroNaoEncontradoException("Livro não pode ser encontrado");
		}		
	}
	
	public void atualizar(Livro livro){
		verificarExistencia(livro);
		livrosrepository.save(livro);
	}
	
	private void verificarExistencia(Livro livro){
		this.buscar(livro.getId());
	}
	
	public Comentario salvarComentario(Long livroId, Comentario comentario){
		
		Livro livro = buscar(livroId);
		
		comentario.setLivro(livro);
		comentario.setData(new Date());
		return comentariosRepository.save(comentario);
	}
	
	public List<Comentario> listarComentarios(Long livroId){
		Livro livro = buscar(livroId);
		return livro.getComentarios();
	}
}
