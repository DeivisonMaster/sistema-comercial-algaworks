package br.com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.pedidovenda.model.Usuario;
import br.com.pedidovenda.repository.UsuarioRepository;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter{
	
	private UsuarioRepository usuarioRepository;
	
	
	public UsuarioConverter() {
		this.usuarioRepository = new UsuarioRepository();
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario usuario = null;
		
		if(value != null){
			Long id = new Long(value);
			usuario = usuarioRepository.buscaPorId(id); 
		}
		return usuario;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Usuario usuario = (Usuario) value;
			return usuario.getId() == null ? null : usuario.getId().toString();
		}
		
		return "";
	}
}
