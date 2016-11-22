package com.github.bilak.poc.springdatarepository.persistence.jpa.repository;

import com.github.bilak.poc.springdatarepository.persistence.jpa.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lvasek on 21/11/2016.
 */
@Repository
public interface TestRepository extends JpaRepository<TestEntity, String>, BaseRepository<TestEntity, String> {
}
