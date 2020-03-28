package com.example.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

	public static <T> List<List<T>> splitArray(List<T> allList, Integer size) {
		List<List<T>> list = new ArrayList<>();
		int fromIndex = 0;
		int remainder = allList.size() % size;
		while (fromIndex < allList.size()) {
  			if (allList.size() - fromIndex <= remainder) {
    			list.add(allList.subList(fromIndex, fromIndex + remainder));
    			fromIndex += remainder;
    			break;
  			}
  			list.add(allList.subList(fromIndex, fromIndex + size));
  			fromIndex += size;
		}
		return list;
	}
}
