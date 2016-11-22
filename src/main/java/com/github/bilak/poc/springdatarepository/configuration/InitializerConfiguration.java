package com.github.bilak.poc.springdatarepository.configuration;

import com.github.bilak.poc.springdatarepository.persistence.jpa.repository.UserEntityRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lvasek on 21/11/2016.
 */
@Configuration
public class InitializerConfiguration {

	@Bean
	Initializer initializer(UserEntityRepository userEntityRepository) {
		return new Initializer(userEntityRepository);
	}
}
