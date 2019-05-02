package com.celinamax.easyfood;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.celinamax.easyfood.domain.Categoria;
import com.celinamax.easyfood.domain.Cidade;
import com.celinamax.easyfood.domain.Cliente;
import com.celinamax.easyfood.domain.Endereco;
import com.celinamax.easyfood.domain.Estado;
import com.celinamax.easyfood.domain.ItemPedido;
import com.celinamax.easyfood.domain.Pagamento;
import com.celinamax.easyfood.domain.PagamentoComBoleto;
import com.celinamax.easyfood.domain.PagamentoComCartao;
import com.celinamax.easyfood.domain.Pedido;
import com.celinamax.easyfood.domain.Produto;
import com.celinamax.easyfood.domain.enums.EstadoPagamento;
import com.celinamax.easyfood.domain.enums.TipoCliente;
import com.celinamax.easyfood.repositories.CategoriaRepository;
import com.celinamax.easyfood.repositories.CidadeRepository;
import com.celinamax.easyfood.repositories.ClienteRepository;
import com.celinamax.easyfood.repositories.EnderecoRepository;
import com.celinamax.easyfood.repositories.EstadoRepository;
import com.celinamax.easyfood.repositories.ItemPedidoRepository;
import com.celinamax.easyfood.repositories.PagamentoRepository;
import com.celinamax.easyfood.repositories.PedidoRepository;
import com.celinamax.easyfood.repositories.ProdutoRepository;

@SpringBootApplication
public class EasyFoodApplication implements CommandLineRunner{	
	

	public static void main(String[] args) {
		SpringApplication.run(EasyFoodApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {	
		
		
	}

}
