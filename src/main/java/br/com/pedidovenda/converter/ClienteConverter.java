package br.com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.pedidovenda.model.Cliente;
import br.com.pedidovenda.repository.ClienteRepository;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter{
	
	private ClienteRepository clienteRepository;
	
	
	public ClienteConverter() {
		this.clienteRepository = new ClienteRepository();
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Cliente cliente = null;
		
		if(value != null){
			Long id = new Long(value);
			cliente = clienteRepository.buscaPorId(id); 
		}
		return cliente;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Cliente cliente = (Cliente) value;
			return cliente.getId() == null ? null : cliente.getId().toString();
		}
		
		return "";
	}
	
	
}
