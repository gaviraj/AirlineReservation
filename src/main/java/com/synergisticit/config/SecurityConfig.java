package com.synergisticit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.synergisticit.service.UserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
	
	@Autowired UserDetailService userDetailService;
	@Autowired AuthenticationSuccessHandler successHandler;
	@Autowired AccessDeniedHandler accessDeniedHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		String[] staticResources = {
				"/css/**",
				"/js/**",
				"/img/**"
		};
		
		//httpSecurity.csrf().disable().authorizeHttpRequests().anyRequest().permitAll();
		httpSecurity.csrf().disable()
		.authorizeHttpRequests().requestMatchers(
				AntPathRequestMatcher.antMatcher("/"),
				AntPathRequestMatcher.antMatcher("/flights"),
				AntPathRequestMatcher.antMatcher("/searchFlights"),
				AntPathRequestMatcher.antMatcher("/reservationForm"),
				AntPathRequestMatcher.antMatcher("/reservationForm2"),
				AntPathRequestMatcher.antMatcher("/passengerForm"),
				AntPathRequestMatcher.antMatcher("/savePassenger"),
				AntPathRequestMatcher.antMatcher("/savePassengers/**"),
				AntPathRequestMatcher.antMatcher("/saveReservation"),
				AntPathRequestMatcher.antMatcher("/saveReservations"),
				AntPathRequestMatcher.antMatcher("/reservationSuccess"),
				AntPathRequestMatcher.antMatcher("/underConstruction"),
				AntPathRequestMatcher.antMatcher("/userProfile"),
				AntPathRequestMatcher.antMatcher("/passengers/**")
		).permitAll()
		.requestMatchers(AntPathRequestMatcher.antMatcher("/login")).permitAll()
		.requestMatchers(AntPathRequestMatcher.antMatcher("/signUp")).permitAll()
		.requestMatchers(AntPathRequestMatcher.antMatcher("/register")).permitAll()
		.requestMatchers(AntPathRequestMatcher.antMatcher("/addPassengers")).permitAll()
		.requestMatchers(AntPathRequestMatcher.antMatcher("/addPassengers/**")).permitAll()
		.requestMatchers(AntPathRequestMatcher.antMatcher("/updateUser/**")).permitAll()
		//.requestMatchers(AntPathRequestMatcher.antMatcher("/branches/**")).permitAll()
		.requestMatchers(staticResources).permitAll() //permit static files without authentication
		.requestMatchers(AntPathRequestMatcher.antMatcher("/WEB-INF/jsp/**")).permitAll()
		.anyRequest().authenticated()
		.and().formLogin()
		.loginPage("/login")
		.loginProcessingUrl("/login")
		.successHandler(successHandler)
		.and().exceptionHandling().accessDeniedHandler(accessDeniedHandler);
		//.and().logout().logoutSuccessUrl("/home");
		
		httpSecurity.userDetailsService(userDetailService);
		
		return httpSecurity.build();
	}
}
