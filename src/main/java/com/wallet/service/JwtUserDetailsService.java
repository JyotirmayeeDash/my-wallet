package com.wallet.service;

import com.wallet.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.wallet.entity.User user = userRepository.findByUserName(username);
		if(user!=null) {
			return new User(user.getUserName(), user.getEncodedPassword(),
					new ArrayList<>());		}

       else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}