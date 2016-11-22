package com.github.bilak.poc.springdatarepository.web.rest.common;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvasek on 22/11/2016.
 */
public class RestPageImpl <T> extends SliceImpl<T> {

	public RestPageImpl() {
		this(new ArrayList<T>());
	}

	public RestPageImpl(List<T> content, Pageable pageable, boolean hasNext) {
		super(content, pageable, hasNext);
	}

	public RestPageImpl(List<T> content) {
		super(content);
	}
}