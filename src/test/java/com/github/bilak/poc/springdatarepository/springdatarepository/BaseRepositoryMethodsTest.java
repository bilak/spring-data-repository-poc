package com.github.bilak.poc.springdatarepository.springdatarepository;

import com.github.bilak.poc.springdatarepository.springdatarepository.persistence.jpa.model.TestEntity;
import com.github.bilak.poc.springdatarepository.springdatarepository.persistence.jpa.repository.BaseRepositoryImpl;
import com.github.bilak.poc.springdatarepository.springdatarepository.persistence.jpa.repository.TestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Tuple;
import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lvasek on 21/11/2016.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class, basePackages = "com.github.bilak.poc.**.persistence.jpa.repository")
public class BaseRepositoryMethodsTest {

	private String uuid1 = UUID.randomUUID().toString();
	private String uuid2 = UUID.randomUUID().toString();

	private TestEntity johnDoe = new TestEntity(uuid1, "john.doe@gmail.com", "John", "Doe");
	private TestEntity jackSparrow = new TestEntity(uuid2, "jack.sparrow@gmail.com", "Jack", "Sparrow");

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private TestRepository testRepository;

	@Test
	public void testGetOnlyIdAttribute() {
		testRepository.save(johnDoe);
		Slice<Tuple> result = testRepository.selectedAttributesQueryWithSpecifications(Arrays.asList("id"), null, null, null, null);
		result.getContent().forEach(tuple -> {
			assertTrue("Size of returned element should be 1", tuple.getElements().size() == 1);
			assertEquals("Founded id does not match selected id", uuid1, tuple.get(0));
		});
	}

	@Test
	public void testGetThreeAttributes() {
		testRepository.save(jackSparrow);
		Slice<Tuple> result = testRepository.selectedAttributesQueryWithSpecifications(Arrays.asList("id", "email", "firstName"), null, null, null, null);
		result.getContent().forEach(tuple -> {
			assertTrue("Size of returned element should be 3", tuple.getElements().size() == 3);
			assertEquals("Founded id does not match selected id", uuid2, tuple.get(0));
			assertEquals("Founded email does not match selected email", "jack.sparrow@gmail.com", tuple.get(1));
			assertEquals("Founded firstName does not match selected firstName", "Jack", tuple.get(2));
		});
	}

	@Test
	public void testNumberOfReturnedRows() {
		testRepository.save(johnDoe);
		testRepository.save(jackSparrow);
		Slice<Tuple> result = testRepository.selectedAttributesQueryWithSpecifications(Arrays.asList("id"), null, null, null, null);
		assertEquals("Size of found row should be 2", 2, result.getContent().size());
	}

	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testInvalidAttributeName() {
		Slice<Tuple> result = testRepository.selectedAttributesQueryWithSpecifications(Arrays.asList("id2345"), null, null, null, null);
	}

	@Test
	public void testPaging() {
		testRepository.save(johnDoe);
		testRepository.save(jackSparrow);
		Slice<Tuple> result = testRepository.selectedAttributesQueryWithSpecifications(Arrays.asList("id"), null, 0, 1, null);
		assertEquals("Size of result must be 1", result.getContent().size(), 1);
	}
}
