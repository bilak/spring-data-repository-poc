package com.github.bilak.poc.springdatarepository.springdatarepository.persistence.jpa.common;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvasek on 26/07/16.
 */
public class QueryHelper {

	/**
	 * Constructs sort for query. If there are no directions provided the default ones will be used.
	 * Parameters must be comma separated to be compatible with rest arrays in GET method
	 *
	 * @param sortProperties sort properties (attributes names)
	 * @param sortDirections sort directions (<code>asc</code>, <code>desc</code>)
	 * @return the sort for query or <code>null</code>
	 */
	public static Sort constructSortFromSortProperties(String sortProperties, String sortDirections) {

		if (StringUtils.isEmpty(sortProperties))
			return null;

		String[] sortProps = sortProperties.split(",");

		String[] sortDirs = new String[0];
		if (!StringUtils.isEmpty(sortDirections))
			sortDirs = sortDirections.split(",");

		if (sortProps.length != sortDirs.length)
			if (sortProps.length > sortDirs.length)
				return constructSortFromSortProperties(sortProperties, appendSortDirection(sortDirections));
			else
				throw new IllegalArgumentException("Number of sort properties and sort directions parameters must be equal");

		List<Sort.Order> sortOrders = new ArrayList<>();
		for (int i = 0; i < sortProps.length; i++) {
			sortOrders.add(new Sort.Order(Sort.Direction.fromStringOrNull(sortDirs.length > 0 ? sortDirs[i] : null), sortProps[i], Sort.NullHandling.NATIVE));
		}
		return new Sort(sortOrders);
	}

	private static String appendSortDirection(String sortDirections) {
		if (org.springframework.util.StringUtils.isEmpty(sortDirections))
			return Sort.Direction.ASC.toString();
		else
			return sortDirections.concat(",").concat(Sort.DEFAULT_DIRECTION.toString());

	}
}
