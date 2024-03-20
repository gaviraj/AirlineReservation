package com.synergisticit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.synergisticit.domain.Reservation;
import com.synergisticit.domain.Role;
import com.synergisticit.service.RoleService;
import com.synergisticit.validation.RoleValidator;

import jakarta.validation.Valid;

@Controller
@Secured({"DBA", "Admin"})
public class RoleController {
	
	@Autowired RoleService roleService;
	@Autowired RoleValidator roleValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(roleValidator);
	}

	@RequestMapping("roles")
	public ModelAndView roles(
			Role role,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "5") int pageSize,
			@RequestParam(defaultValue = "roleId") String sortedBy
	) {
		System.out.println("RoleController.roles()...");
		ModelAndView mav = new ModelAndView("roles");
		//mav.addObject("roles", roleService.findAll());
		mav.addObject("activeRoles", "active");
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Role> pagedRoles = roleService.findAll(pageable);
		List<Role> roles = pagedRoles.getContent();
		mav.addObject("roles", roles);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedRoles.getTotalPages());
		
		return mav;
	} 
	
	@RequestMapping("saveRole")
	public ModelAndView saveRole(@Valid @ModelAttribute Role role, BindingResult br) {
		System.out.println("RoleController.saveRole()...");
		ModelAndView mav = new ModelAndView("roles");
		
		if (!br.hasErrors()) {
			roleService.saveRole(role);
			mav.setViewName("redirect:roles");
		}
		mav.addObject("roles", roleService.findAll());
		mav.addObject("activeRoles", "active");
		
		return mav;
	}
	
	@RequestMapping("updateRole")
	public ModelAndView updateRole(
			Role role,
			@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "5") int pageSize,
			@RequestParam(defaultValue = "roleId") String sortedBy
	) {
		System.out.println("RoleController.updateRole()...");
		ModelAndView mav = new ModelAndView("roles");
		role = roleService.findById(role.getRoleId());
		mav.addObject("role", role);
		//mav.addObject("roles", roleService.findAll());
		mav.addObject("activeRoles", "active");
		mav.addObject("isUpdate", true);
		
		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortedBy));
		Page<Role> pagedRoles = roleService.findAll(pageable);
		List<Role> roles = pagedRoles.getContent();
		mav.addObject("roles", roles);
		mav.addObject("pageNo", pageNo);
		mav.addObject("pageSize", pageSize);
		mav.addObject("sortedBy", sortedBy);
		mav.addObject("totalPages", pagedRoles.getTotalPages());
		
		return mav;
	}
	
	@RequestMapping("deleteRole")
	public ModelAndView deleteRole(Role role) {
		System.out.println("RoleController.deleteRole()...");
		
		roleService.deleteById(role.getRoleId());
		
		ModelAndView mav = new ModelAndView("redirect:roles");
		mav.addObject("roles", roleService.findAll());
		
		return mav;
	}
}
