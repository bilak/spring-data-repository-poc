package com.github.bilak.poc.springdatarepository.springdatarepository.persistence.jpa.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.List;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * Created by lvasek on 21/11/2016.
 */
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

	private EntityManager em;
	private JpaEntityInformation<T, ?> entityInformation;

	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager em) {
		super(entityInformation, em);
		this.em = em;
		this.entityInformation = entityInformation;
	}

	@Override
	public Slice<Tuple> selectedAttributesQueryWithSpecifications(List<String> queryAttributes, Specification<T> spec, Integer page, Integer size, Sort sort) {

		if (page != null && page < 0) {
			throw new IllegalArgumentException("Page must not be less than zero!");
		}
		if (size != null && size < 1) {
			throw new IllegalArgumentException("Size must not be less than one!");
		}

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = builder.createTupleQuery();

		Root<T> root = applySpecificationToCriteria(spec, getDomainClass(), query);
		query.select(builder.tuple(getSelectionAttributes(queryAttributes, root)));

		if (sort != null) {
			query.orderBy(toOrders(sort, root, builder));
		}

		TypedQuery<Tuple> typedQuery = em.createQuery(query);

		if (size != null) {
			typedQuery.setFirstResult((page != null ? page : 0) * size);
			typedQuery.setMaxResults(size + 1);
		}

		List<Tuple> result = typedQuery.getResultList();
		boolean hasNext = false;
		if (size != null) {
			if (hasNext = (result.size() == size + 1)) {
				result.remove(size.intValue());
			}
		}

		return new SliceImpl<>(result, constructResultPageable(page, size, result.size(), sort), hasNext);
	}

	private Selection<?>[] getSelectionAttributes(List<String> queryAttributes, Root<T> root) {
		// todo check if attribute name is ok?
		// todo create attribute mappings?
		Selection<?>[] attrs = new Selection[queryAttributes.size()];
		for (int i = 0; i < queryAttributes.size(); i++) {
			attrs[i] = root.get(queryAttributes.get(i));
		}

		return attrs;
	}

	private <S, U extends T> Root<U> applySpecificationToCriteria(Specification<U> spec, Class<U> domainClass, CriteriaQuery<S> query) {

		Assert.notNull(query);
		Assert.notNull(domainClass);
		Root<U> root = query.from(domainClass);

		if (spec == null) {
			return root;
		}

		CriteriaBuilder builder = em.getCriteriaBuilder();
		Predicate predicate = spec.toPredicate(root, query, builder);

		if (predicate != null) {
			query.where(predicate);
		}

		return root;
	}

	private Pageable constructResultPageable(Integer pageNumber, Integer pageSize, Integer resultSize, Sort sort) {
		if (resultSize == null || resultSize == 0)
			return new PageRequest((pageNumber != null ? pageNumber : 0), (pageSize != null ? pageSize : 1), sort);

		if (pageNumber == null && pageSize == null) {
			pageSize = (resultSize == null || resultSize == 0) ? 1 : resultSize;
			return constructResultPageable(0, pageSize, resultSize, sort);
		}

		if (pageSize == null || pageSize == 0) {
			pageSize = (resultSize == null || resultSize == 0) ? 1 : resultSize;
			pageNumber = 0;
		}

		if (pageNumber == null)
			pageNumber = 0;
		return new PageRequest(pageNumber, pageSize, sort);
	}
}
