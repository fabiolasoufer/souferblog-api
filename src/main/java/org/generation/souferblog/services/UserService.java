package org.generation.souferblog.services;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.generation.souferblog.models.User;
import org.generation.souferblog.models.UserLogin;
import org.generation.souferblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public User RegisterUser(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String passwordEncoder = encoder.encode(user.getPassword());
		user.setPassword(passwordEncoder);
		
		return repository.save(user);
	}
	
	public Optional<UserLogin> Login(Optional<UserLogin> userLogin) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<User> user = repository.findByUser(userLogin.get().getUser());
		
		if(user.isPresent()) {
			if(encoder.matches(userLogin.get().getPassword(), user.get().getPassword())) {
				
				String auth = userLogin.get().getUser() + ":" + userLogin.get().getPassword();
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				
				userLogin.get().setToken(authHeader);
				userLogin.get().setName(user.get().getName());
				
				return userLogin;
				
			}
		}
		
		return null;
	}

}
