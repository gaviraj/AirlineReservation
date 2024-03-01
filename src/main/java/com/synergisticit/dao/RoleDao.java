package com.synergisticit.dao;

import java.util.List;

import com.synergisticit.domain.Role;

public interface RoleDao {

	public Role saveRole(Role role); 
	
	public List<Role> findAll();
	
	public Role findById(Long roleId);
	
	public void deleteById(Long roleId);
	
	public Role findByRoleName(String roleName);
}
