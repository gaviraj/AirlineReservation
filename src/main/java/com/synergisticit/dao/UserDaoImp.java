package com.synergisticit.dao;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.synergisticit.domain.User;

@Repository
public class UserDaoImp implements UserDao {
	
	@Autowired JdbcTemplate jdbcTemplate;

	@Override
	public User saveUser(User user) {
		String sql = "INSERT INTO USER ()";
		return null;
	}

	@Override
	public List<User> findAll() {
		String sql = "SELECT * FROM USER;";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
	}

	@Override
	public User findById(Long userId) {
		String sql = "SELECT * FROM USER WHERE USERID = ?;";
		return jdbcTemplate.queryForObject(sql, new Object[] {userId}, new int[] {Types.INTEGER}, new BeanPropertyRowMapper<User>(User.class));
	}

	@Override
	public void deleteById(Long userId) {
		String sql = "DELETE FROM USER WHERE USERID = ?;";
		int rowsAffected = jdbcTemplate.update(sql, new Object[] {userId});
		if (rowsAffected == 1) {
			System.out.println("users deleted: " + rowsAffected);
		} else {
			System.out.println("Error deleting user...");
		}

	}

	@Override
	public User findByUsername(String username) {
		String sql = "SELECT * FROM USER WHERE USERNAME = ?;";
		return jdbcTemplate.queryForObject(sql, new Object[] {username}, new int[] {Types.VARCHAR}, new BeanPropertyRowMapper<User>(User.class));
	}

	@Override
	public User findByEmail(String email) {
		String sql = "SELECT * FROM USER WHERE EMAIL = ?;";
		return jdbcTemplate.queryForObject(sql, new Object[] {email}, new int[] {Types.VARCHAR}, new BeanPropertyRowMapper<User>(User.class));
	}

}
