package org.ssglobal.training.codes.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.ssglobal.training.codes.repository.AuthenticateRepository;

@Component
public class MyCommandLineRunner implements CommandLineRunner {
	
	@Autowired
	private AuthenticateRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        // Your custom code to run on startup
        System.out.println("Running code on Spring Boot startup using CommandLineRunner");
        
        repository.findAllUsers().orElse(null).forEach(user -> {
    		user.setPassword(encoder.encode("123456"));
    		repository.updatePassword(user);
        });
    }

}