package com.celinamax.easyfood.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.celinamax.easyfood.domain.Cliente;
import com.celinamax.easyfood.dto.ClienteDTO;
import com.celinamax.easyfood.repositories.ClienteRepository;
import com.celinamax.easyfood.resources.exception.FieldMessage;

public class ClienteUpadateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
	 
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		
		List<FieldMessage> list = new ArrayList<>();
		
		Integer uriId = Integer.parseInt((map.get("id")));
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
			if(aux != null && !aux.getId().equals(uriId)) {
				list.add(new FieldMessage("email", "Email já cadastrado!"));
			
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}