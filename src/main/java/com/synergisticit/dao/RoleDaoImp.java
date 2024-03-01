package com.synergisticit.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.synergisticit.domain.Role;

@Repository
public class RoleDaoImp implements RoleDao {
	
	@Autowired NamedParameterJdbcTemplate npJdbcTemplate;

	@Override
	public Role saveRole(Role role) {
		String sql = "INSERT INTO ROLE (ROLEID, ROLENAME) VALUES (:roleId, :roleName);";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("roleId", role.getRoleId());
		mapSqlParameterSource.addValue("roleName", role.getRoleName());
		
		int rowsAffected = npJdbcTemplate.update(sql, mapSqlParameterSource);
		if (rowsAffected == 1) {
			System.out.println("role records saved: " + rowsAffected);
		} else {
			System.out.println("Error creating new role...");
		}
		
		return role;
	}

	@Override
	public List<Role> findAll() {
		String sql = "SELECT * FROM ROLE;";
		return npJdbcTemplate.query(sql, new BeanPropertyRowMapper<Role>(Role.class));
	}

	@Override
	public Role findById(Long roleId) {
		String sql = "SELECT * FROM ROLE WHERE ROLEID = :roleId;";
		Map<String, Object> parameterMap = new HashMap<String, Object>();
		parameterMap.put("roleId", roleId);
		
		try {
			return npJdbcTemplate.queryForObject(sql, parameterMap, new BeanPropertyRowMapper<Role>(Role.class));
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		} 
	}

	@Override
	public void deleteById(Long roleId) {
		String sql = "DELETE FROM ROW WHERE ROLEID = :roleId;";
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("roleId", roleId);
		
		int rowsAffected = npJdbcTemplate.update(sql, mapSqlParameterSource);
		if (rowsAffected == 1) {
			System.out.println("roles deleted: " + rowsAffected);
		} else {
			System.out.println("Error deleting role...");
		}
		
	}

	@Override
	public Role findByRoleName(String roleName) {
		String sql = "SELECT * FROM ROLE WHERE ROLENAME = :roleName;";
		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("roleName", roleName);
		
		try {
			return npJdbcTemplate.queryForObject(sql, parameterMap, new BeanPropertyRowMapper<Role>(Role.class));
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		} 
	}

}
