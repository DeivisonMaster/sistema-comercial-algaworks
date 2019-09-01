package br.com.pedidovenda.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.repository.filtro.PedidoFiltro;

public class PedidoRepository implements Serializable{
	private static final long serialVersionUID = 1L; 
	
	@Inject
	private EntityManager entityManager;
	
	public List<Pedido> pedidosFiltrados(PedidoFiltro filtro){
		Session session = this.entityManager.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(Pedido.class);
		criteria.createAlias("cliente", "c");
		criteria.createAlias("vendedor", "v");
		
		
		if(filtro.getNumeroDe() != null) {
			criteria.add(Restrictions.ge("id", filtro.getNumeroDe()));
		}
		if(filtro.getNumeroAte() != null) {
			criteria.add(Restrictions.le("id", filtro.getNumeroAte()));
		}
		if(filtro.getDataCriacaoDe() != null) {
			criteria.add(Restrictions.ge("dataCriacao", filtro.getDataCriacaoDe()));
		}
		if(filtro.getDataCriacaoAte() != null) {
			criteria.add(Restrictions.le("dataCriacao", filtro.getDataCriacaoAte()));
		}
		if(StringUtils.isNotBlank(filtro.getNomeCliente())) {
			criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
		}
		if(StringUtils.isNotBlank(filtro.getNomeVendedor())) {
			criteria.add(Restrictions.ilike("v.nome", filtro.getNomeVendedor(), MatchMode.ANYWHERE));
		}
		if(filtro.getStatus() != null && filtro.getStatus().length > 0 ) {
			criteria.add(Restrictions.in("status", filtro.getStatus()));
		}
		
		return criteria.addOrder(Order.asc("id")).list();
		
	}
}
