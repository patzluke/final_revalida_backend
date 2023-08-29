package org.ssglobal.training.codes.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.ssglobal.training.codes.models.Users;
import org.ssglobal.training.codes.repository.AuthenticateRepository;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
	
	@Autowired
	private AuthenticateRepository repository;
	
    @Override
    public void run(String... args) throws Exception {
        // Your custom code to run on startup
        System.out.println("Running code on Spring Boot startup using CommandLineRunner");
        
        for (int i = 0; i < 7; i++) {
			Users user = repository.findAllUsers().orElse(null).get(i);
			user.setPassword(encoder().encode("123456"));
    		repository.updatePassword(user);
		}
    }

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}