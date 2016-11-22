package com.github.bilak.poc.springdatarepository;

import com.github.bilak.poc.springdatarepository.web.rest.common.RestPageImpl;
import org.json.JSONObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by lvasek on 22/11/2016.
 */
public class Test {

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<RestPageImpl<Map>> response =
				restTemplate.exchange("http://localhost:8080/users/tuple-json?attributes=id,username,userEmail",
						HttpMethod.GET,
						null,
						new ParameterizedTypeReference<RestPageImpl<Map>>() {
						});
		JSONObject.testValidity(response.getBody());
		System.out.println(response.getBody());

		RestPageImpl<Map> responseBody = response.getBody();
		List<Map> content = responseBody.getContent();
		content.forEach(System.out::println);


	}
}
