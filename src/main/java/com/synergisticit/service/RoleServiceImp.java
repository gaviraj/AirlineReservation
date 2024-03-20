package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.synergisticit.dao.RoleDao;
import com.synergisticit.domain.Role;
import com.synergisticit.repository.RoleRepository;

@Service
public class RoleServiceImp implements RoleService {
	
	@Autowired RoleRepository roleRepository;
	@Autowired RoleDao roleDao;

	@Override
	public Role saveRole(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
		//return roleDao.findAll();
	}

	@Override
	public Role findById(Long roleId) {
		//return roleRepository.findById(roleId).orElse(null);
		return roleDao.findById(roleId);
	}

	@Override
	public void deleteById(Long roleId) {
		//roleRepository.deleteById(roleId);
		roleDao.deleteById(roleId);
	}

	@Override
	public Role findByRoleName(String roleName) {
		//return roleRepository.findByRoleName(roleName);
		return roleDao.findByRoleName(roleName);
	}

	@Override
	public Page<Role> findAll(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}

}
