package io.github.anantharajuc.sbtest.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/rbac")
public class UserControllerNew 
{
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@PostMapping("/user")
	public void createUser(@Valid @RequestBody User user)
	{ 		
		List<Role> roles = user.getRoles();
		
		log.info("------------------ User Name : "+user.getUsername());
		log.info("------------------ Roles Count : "+roles.size());
		
		for(int i = 0; i < roles.size(); i++)
		{
			log.info("------------------ Role Name : "+roles.get(i).getName()); 
			
			if(roles.get(i).getPermissions().isEmpty())
			{
				log.info("------------------ Role doesn't have permissions explicitly defined : ");

				String resource = roles.get(i).getName().replace("ROLE_", "");
				
				log.info("------------------ Resource : "+resource); 
			}
			else
			{
				log.info("------------------ Role has permissions explicitly defined : "); 
			}
		}

		log.info("------------------ user.getUsername() : "+user.getUsername());
	
		userServiceImpl.createUser(user);
	}
	
	@GetMapping(value="/person/{username}")
	public Optional<User> getPersonByUsername(@PathVariable(value = "username") String username)
	{		
		return userServiceImpl.getUserByUsername(username);
	}
}
