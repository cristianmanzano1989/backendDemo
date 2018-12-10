package com.youtube.demo.controllers;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.demo.model.User;
import com.youtube.demo.service.UserService;
import com.youtube.demo.util.QueryResult;
import com.youtube.demo.util.RestResponse;

import antlr.StringUtils;

@RestController
public class UserController {
	
	@Autowired
	protected UserService userService;
	
	protected ObjectMapper mapper;
	
	@RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST )
	public RestResponse saveOrUpdate(@RequestBody String userJson) throws JsonParseException, JsonMappingException, IOException
	{
		this.mapper= new ObjectMapper();
		User user = this.mapper.readValue(userJson,User.class);
		
		if(!this.validate(user))
		{
			return new RestResponse(HttpStatus.NOT_ACCEPTABLE.value(),"los campos obligatorios no estan diligenciados");

		}
		
		this.userService.save(user);
		
		return new RestResponse(HttpStatus.OK.value(), "Operación Exitosa");
	}
	
	@RequestMapping(value = "/getUsers",method = RequestMethod.GET)
	public List<User> getUsers() {
		return this.userService.findAll();
	}
	
	@RequestMapping(value = "/deleteUser",method = RequestMethod.POST)
	public void deleteUser(@RequestBody String userJson) throws Exception {
		this.mapper= new ObjectMapper();
		User user = this.mapper.readValue(userJson,User.class);
		
		if(String.valueOf(user.getId()) == null)
		{
			throw new Exception("El id esta nulo");
		}
		
		this.userService.delete(user.getId());
	}
	
	private boolean validate(User user) {
		
		boolean isValid = true;
		
		if(org.apache.commons.lang3.StringUtils.trimToNull(user.getFirstname()) == null)
		{
			isValid = false;
		}
		
		if(org.apache.commons.lang3.StringUtils.trimToNull(user.getFirstsurname()) == null)
		{
			isValid = false;
		}
		
		if(org.apache.commons.lang3.StringUtils.trimToNull(user.getAddress()) == null)
		{
			isValid = false;
		}
		
		return isValid;
	}

}
