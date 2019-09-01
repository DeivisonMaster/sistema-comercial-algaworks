package br.com.pedidovenda.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.pedidovenda.model.Pedido;
import br.com.pedidovenda.repository.PedidoRepository;

@FacesConverter(forClass = Pedido.class)
public class PedidoConverter implements Converter{

	private PedidoRepository pedidoRepository; 
	
	public PedidoConverter() {
		this.pedidoRepository = new PedidoRepository();
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Pedido pedido = null;
		
		if(value != null) {
			Long id = new Long(value);
			pedido = pedidoRepository.buscaPorId(id);
		}
		
		return pedido;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null) {
			Pedido pedido = (Pedido) value;
			return pedido.getId() == null ? null : pedido.getId().toString();
			//return ((Produto) value).getId().toString();
		}
		return "";
	}
	
}
