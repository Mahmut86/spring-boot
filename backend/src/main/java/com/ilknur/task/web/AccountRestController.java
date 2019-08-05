package com.ilknur.task.web;

import com.ilknur.task.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ilknur.task.entities.User;

@RestController
public class AccountRestController {
	@Autowired
	private AccountService accountService;

	
	@PostMapping("/register")
	private User register(@RequestBody User userDTO) {
		

		User user= accountService.findUserByUserName(userDTO.getUsername());
		if (user!=null) {
			throw new RuntimeException("This user already exists");
		}
		User appUser= new User();
		appUser.setUsername(userDTO.getUsername());
		appUser.setPassword(userDTO.getPassword());
		// roles  "ADMIN" , "USER" added in the start main class (run function) 
		 accountService.saveUser(appUser);

		 return appUser;
		
	}
	
}










