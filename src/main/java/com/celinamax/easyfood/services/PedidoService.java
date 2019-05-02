package com.celinamax.easyfood.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.celinamax.easyfood.domain.ItemPedido;
import com.celinamax.easyfood.domain.PagamentoComBoleto;
import com.celinamax.easyfood.domain.Pedido;
import com.celinamax.easyfood.domain.Produto;
import com.celinamax.easyfood.domain.enums.EstadoPagamento;
import com.celinamax.easyfood.repositories.ItemPedidoRepository;
import com.celinamax.easyfood.repositories.PagamentoRepository;
import com.celinamax.easyfood.repositories.PedidoRepository;
import com.celinamax.easyfood.repositories.ProdutoRepository;
import com.celinamax.easyfood.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
		
	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não Encontrado! Id: " + id 
					+ ", Tipo: " + Pedido.class.getName());
		}
		return obj;		
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}		
		
		Pedido obj2 = repo.save(obj);		
		pagamentoRepository.save(obj2.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			int cod = ip.getProduto().getId();				
			Produto x = produtoRepository.findOne(cod);					
			ip.setPreco(x.getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		return obj;
		
		
	}

}
