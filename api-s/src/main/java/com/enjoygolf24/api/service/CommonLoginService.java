package com.enjoygolf24.api.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service 
public interface CommonLoginService extends UserDetailsService{
	
	@Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException;
}
