package com.synergisticit.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.synergisticit.domain.Role;

public interface RoleService {

	public Role saveRole(Role role); 
	
	public List<Role> findAll();
	
	public Role findById(Long roleId);
	
	public void deleteById(Long roleId);
	
	public Role findByRoleName(String roleName);
	
	public Page<Role> findAll(Pageable pageable);
}
