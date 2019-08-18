package br.com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.pedidovenda.model.Produto;
import br.com.pedidovenda.repository.ProdutoRepository;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {
	
	private ProdutoRepository repositoryProduto; 
	
	public ProdutoConverter() {
		this.repositoryProduto = new ProdutoRepository();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Produto produto = null;
		
		if(value != null) {
			Long id = new Long(value);
			produto = repositoryProduto.buscaPorId(id);
		}
		
		return produto;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Produto produto = (Produto) value;
			return produto.getId() == null ? null : produto.getId().toString();
			//return ((Produto) value).getId().toString();
		}
		return "";
	}

}

