package com.github.bilak.poc.springdatarepository.springdatarepository.persistence.jpa.repository;

import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import javax.persistence.Tuple;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lvasek on 21/11/2016.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {

	Slice<Tuple> selectedAttributesQueryWithSpecifications(List<String> queryAttributes, Specification<T> spec, Integer page, Integer size, Sort sort);
}
