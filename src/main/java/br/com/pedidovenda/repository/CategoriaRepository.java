package br.com.pedidovenda.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.pedidovenda.model.Categoria;
import br.com.pedidovenda.util.JPAUtil;

@Dependent
public class CategoriaRepository implements Serializable{
	private static final long serialVersionUID = 1L;	
	
	//@Inject
	private EntityManager entityManager;
	
	public CategoriaRepository() {
		this.entityManager = new JPAUtil().getEntityManager();
	}
	
	public Collection<Categoria> buscarPorCategorias(){
		return entityManager.createQuery("FROM Categoria WHERE categoriaPai IS NULL",  Categoria.class).getResultList();
	}

	public Categoria buscaPorId(Long id) {
		return entityManager.find(Categoria.class, id);
	}

	public Collection<Categoria> buscaPorSubCategoriaDeCategoria(Categoria categoriaSelecionada) {
		return entityManager.createQuery("FROM Categoria WHERE categoriaPai = :idCategoria", 
				Categoria.class)
				.setParameter("idCategoria", categoriaSelecionada)
				.getResultList();
	}
}
