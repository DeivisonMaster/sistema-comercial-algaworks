package br.com.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pedidovenda.model.Categoria;

@Dependent
public class RepositoryCategoria implements Serializable{
	private static final long serialVersionUID = 1L;	
	
	@Inject
	private EntityManager entityManager;
	
	public List<Categoria> buscarPorCategorias(){
		return entityManager.createQuery("from Categoria",  Categoria.class).getResultList();
	}
}
