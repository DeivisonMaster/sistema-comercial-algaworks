package br.com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.com.pedidovenda.model.Categoria;
import br.com.pedidovenda.repository.CategoriaRepository;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {
	
	private CategoriaRepository repositoryCategoria; 
	
	public CategoriaConverter() {
		this.repositoryCategoria = new CategoriaRepository();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Categoria categoria = null;
		
		if(value != null) {
			Long id = new Long(value);
			categoria = repositoryCategoria.buscaPorId(id);
		}
		
		return categoria;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			return ((Categoria) value).getId().toString();
		}
		return "";
	}

}
