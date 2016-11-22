package com.github.bilak.poc.springdatarepository.web.rest;

import com.github.bilak.poc.springdatarepository.persistence.jpa.repository.UserEntityRepository;
import com.github.bilak.poc.springdatarepository.persistence.jpa.common.QueryHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by lvasek on 21/11/2016.
 */
@RestController
@RequestMapping("/users")
public class UserController {

	private UserEntityRepository userEntityRepository;

	@Autowired
	public UserController(UserEntityRepository userEntityRepository) {
		this.userEntityRepository = userEntityRepository;
	}

	@GetMapping("/tuple")
	public ResponseEntity<List<Object[]>> tupleQuery(@RequestParam String attributes) {
		return Optional.ofNullable(userEntityRepository.selectedAttributesQueryWithSpecifications(Arrays.asList(attributes.split(",")), null, null, null, null))
				.map(result -> result.getContent().size() > 0 ?
						new ResponseEntity<List<Object[]>>(
								result.getContent().stream()
										.map(row -> {
											Object[] response = new Object[row.getElements().size()];
											for (int i = 0; i < row.getElements().size(); i++) {
												response[i] = row.get(i);
											}
											return response;
										})
										.collect(Collectors.toList()),
								HttpStatus.OK
						) :
						new ResponseEntity<List<Object[]>>(HttpStatus.NOT_FOUND))
				.orElse(new ResponseEntity<List<Object[]>>(HttpStatus.NOT_FOUND));

	}

	@GetMapping("/tuple-json")
	public ResponseEntity<Slice<Object>> tupleJsonQuery(@RequestParam String attributes,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "sortProps", required = false) String sortProperties,
			@RequestParam(name = "sortDirs", required = false) String sortDirections) {
		String[] attributeNames = attributes.split(",");
		return Optional.ofNullable(
				userEntityRepository.selectedAttributesQueryWithSpecifications(
						Arrays.asList(attributeNames),
						null,
						page,
						size,
						QueryHelper.constructSortFromSortProperties(sortProperties, sortDirections)))
				.map(result -> result.getContent().size() > 0 ?
						new ResponseEntity<Slice<Object>>(
								new SliceImpl<Object>(
										tuplesToJson(result.getContent(), attributeNames),
										new PageRequest(result.getNumber(), result.getSize(), result.getSort()),
										result.hasNext()),
								HttpStatus.OK
						) :
						new ResponseEntity<Slice<Object>>(HttpStatus.NOT_FOUND))
				.orElse(new ResponseEntity<Slice<Object>>(HttpStatus.NOT_FOUND));
	}

	private List<Object> tuplesToJson(List<Tuple> tuples, String[] attributeNames) {
		JSONArray jsonArray = new JSONArray();
		if (tuples != null && tuples.size() > 0) {
			for (Tuple tuple : tuples) {
				JSONObject element = new JSONObject();
				for (int i = 0; i < tuple.getElements().size(); i++) {
					element.put(attributeNames[i], tuple.get(i));
				}
				jsonArray.put(element);
			}
		}

		//Map<String, Object> result = new LinkedHashMap<>();
		//result.put("result", toList(jsonArray));
		return toList(jsonArray);
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

}
