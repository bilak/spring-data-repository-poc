package com.github.bilak.poc.springdatarepository.persistence.jpa.repository;

import com.github.bilak.poc.springdatarepository.persistence.jpa.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lvasek on 21/11/2016.
 */
@Repository
public interface UserEntityRepository extends BaseRepository<UserEntity, String>, JpaRepository<UserEntity, String> {
}
