package com.example.RentalService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {


	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtFilter filter;
	
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
//		return http.csrf(csrf -> csrf.disable())
//				.authorizeHttpRequests(auth -> 
//				auth.requestMatchers("/login", "/register").permitAll()
//				 .requestMatchers("/admin/**").authenticated()
//				.anyRequest().authenticated())
//				.httpBasic().disable()
//				.build();
//		
//	}
//	
	 @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	        return http
	            .csrf(csrf -> csrf.disable())
//	            .cors().and()
	            .authorizeHttpRequests(auth -> auth
	                .requestMatchers("/login", "/register").permitAll()
	                .requestMatchers("/api/rental/**").hasAuthority("ROLE_rental") 
	                .anyRequest().authenticated()
	            )
//	            .httpBasic(Customizer.withDefaults())
	            .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
	            .build();
	    }
	@Bean
	public AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		return provider;
	}
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    	return config.getAuthenticationManager();
    }
}
