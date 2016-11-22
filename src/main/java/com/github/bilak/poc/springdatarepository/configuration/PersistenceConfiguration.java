package com.github.bilak.poc.springdatarepository.configuration;

import com.github.bilak.poc.springdatarepository.persistence.jpa.repository.BaseRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by lvasek on 21/11/2016.
 */
@Configuration
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class, basePackages = "com.github.bilak.poc.**.persistence.jpa.repository")
public class PersistenceConfiguration {
}
