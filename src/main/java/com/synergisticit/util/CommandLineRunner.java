package com.synergisticit.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.synergisticit.domain.Role;
import com.synergisticit.domain.User;
import com.synergisticit.service.RoleService;
import com.synergisticit.service.UserService;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
	
	@Autowired UserService userService;
	@Autowired RoleService roleService;

	@Override
	public void run(String... args) throws Exception {
		if (roleService.findByRoleName("Admin") == null) {
			Role role = new Role();
			role.setRoleId(1L);
			role.setRoleName("Admin");
			
			roleService.saveRole(role);
		}
		
		if (userService.findByUsername("admin") == null) {
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleService.findByRoleName("Admin"));
			
			User user = new User();
			user.setUserId(1L);
			user.setUsername("admin");
			user.setPassword("123123");
			user.setEmail("admin@admin.com");
			user.setPhoneNum("123-456-7890");
			user.setRoles(roles);
			
			userService.saveUser(user);
		}

	}

}
