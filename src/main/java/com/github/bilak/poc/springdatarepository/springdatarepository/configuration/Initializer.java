package com.github.bilak.poc.springdatarepository.springdatarepository.configuration;

import com.github.bilak.poc.springdatarepository.springdatarepository.persistence.jpa.model.UserEntity;
import com.github.bilak.poc.springdatarepository.springdatarepository.persistence.jpa.repository.UserEntityRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Created by lvasek on 21/11/2016.
 */
public class Initializer implements InitializingBean {

	private UserEntityRepository userEntityRepository;

	@Autowired
	public Initializer(UserEntityRepository userEntityRepository) {
		this.userEntityRepository = userEntityRepository;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (userEntityRepository.count() < 200) {
			UserEntity userEntity = null;
			String id = null;
			for (int i = 0; i < 10; i++) {
				id = UUID.randomUUID().toString();
				userEntity = new UserEntity(id, "user_" + id, "email_" + id);
				userEntityRepository.save(userEntity);
			}
		}
	}
}