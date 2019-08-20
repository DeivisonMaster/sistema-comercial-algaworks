package br.com.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.pedidovenda.model.Produto;
import br.com.pedidovenda.repository.filtro.ProdutoFiltro;
import br.com.pedidovenda.service.NegocioException;
import br.com.pedidovenda.util.JPAUtil;
import br.com.pedidovenda.util.Transacional;

@Dependent
public class ProdutoRepository implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//@Inject
	private EntityManager entityManager;
	
	public ProdutoRepository() {
		this.entityManager = new JPAUtil().getEntityManager();
	}

	
	public void salvar(Produto produto) {
		this.entityManager.getTransaction().begin();
		this.entityManager.merge(produto);
		this.entityManager.getTransaction().commit();
	}

	public Produto buscaPorSKU(Produto produto) {
		try {
			return this.entityManager.createQuery("FROM Produto WHERE UPPER(sku) = :sku", Produto.class)
					.setParameter("sku", produto.getSku().toUpperCase())
					.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<Produto> buscaPorProdutos(ProdutoFiltro filtro){
		Session session = entityManager.unwrap(Session.class);  // erro quando se utiliza o escopo @RequestScoped no método produtor de EntityManager
		Criteria criteria = session.createCriteria(Produto.class);
		
		if(StringUtils.isNotBlank(filtro.getSku())) {
			criteria.add(Restrictions.eq("sku", filtro.getSku()));
		}
		if(StringUtils.isNotBlank(filtro.getNome())) {
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		return criteria.addOrder(Order.asc("nome")).list();
	}

	public Produto buscaPorId(Long id) {
		return this.entityManager.find(Produto.class, id);
	}
	
	@Transacional
	public void excluiProduto(Produto produto) {
		try {
			/* proposito de uso do flush():
			 * 
			 * caso haja alguma outra tabela no bd fazendo uso do produto (tb_pedido), 
			 * uma exceção é lançada dentro do método, sem o flush(), a exceção seria 
			 * lançada dentro do interceptador
			 * */
			
			produto = buscaPorId(produto.getId());
			this.entityManager.remove(produto); // setado para exclusão, aguardando: flush, commit
			
			this.entityManager.flush(); // pendencias no banco serão comitadas
			
		} catch (PersistenceException e) {
			throw new NegocioException("Produto não pode ser excluído");
		}
	}

}















