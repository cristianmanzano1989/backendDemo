package com.youtube.demo.service;

import java.util.List;

import com.youtube.demo.model.User;

public interface UserService {
	
	/***
	 * Guarda un usuario 
	 * @param user
	 * @return el usuario guardado
	 */
	User save(User user);
	
	/***
	 * Recupera la lista de Usuarios
	 * @return lista de Usuarios
	 */
	
	List<User> findAll();

}
