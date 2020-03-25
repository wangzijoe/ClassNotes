package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestList {

	private static final int DATA_LENGTH = 5;

	@Test
	public void subListTest() {
		List<Integer> indexArr = new ArrayList<>();
		for (int i = 1; i <= 22; i++) {
			indexArr.add(i);
		}
		System.err.println(indexArr);
		System.err.println(indexArr.subList(0, 5));
		System.err.println(indexArr.subList(5, 10));
		System.err.println(indexArr.subList(10, 15));
		System.err.println(indexArr.subList(15, 20));
		System.err.println(indexArr.subList(20, 22));
		System.err.println("=============================data_sub=============================");
		int fromIndex = 0;
		int remainder = indexArr.size() % DATA_LENGTH;
		while (fromIndex < indexArr.size()) {
			if (indexArr.size() - fromIndex <= remainder) {
				System.err.println(indexArr.subList(fromIndex, fromIndex += remainder));
				break;
			}
			System.err.println(indexArr.subList(fromIndex, fromIndex += DATA_LENGTH));
		}
	}
	
	private <T> List<List<T>> splitArray(List<T> allList, Integer size) {
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
