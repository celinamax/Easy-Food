package com.celinamax.easyfood.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.celinamax.easyfood.domain.Cliente;
import com.celinamax.easyfood.domain.ItemPedido;
import com.celinamax.easyfood.domain.PagamentoComBoleto;
import com.celinamax.easyfood.domain.Pedido;
import com.celinamax.easyfood.domain.enums.EstadoPagamento;
import com.celinamax.easyfood.repositories.ClienteRepository;
import com.celinamax.easyfood.repositories.ItemPedidoRepository;
import com.celinamax.easyfood.repositories.PagamentoRepository;
import com.celinamax.easyfood.repositories.PedidoRepository;
import com.celinamax.easyfood.repositories.ProdutoRepository;
import com.celinamax.easyfood.security.UserSS;
import com.celinamax.easyfood.services.exceptions.AuthorizationException;
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
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	/*@Autowired
	private ClienteService clienteService;	*/
	
	@Autowired
	private EmailService emailService;
	
		
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
		obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}		
		
	    obj = repo.save(obj);		
		pagamentoRepository.save(obj.getPagamento());
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));							
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;		
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso Negado!");
		}
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteRepository.findOne(user.getId());
		return repo.findByCliente(cliente, pageRequest);		
	}
}
