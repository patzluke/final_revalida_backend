package org.ssglobal.training.codes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.JavaVersion;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class LastMajorrevalidaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(LastMajorrevalidaBackendApplication.class, args);
		System.out.println(SpringVersion.getVersion());
		System.out.println(JavaVersion.getJavaVersion());
	}

}
