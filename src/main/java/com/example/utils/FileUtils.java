package com.example.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.springframework.util.StringUtils;

public class FileUtils {

	private static List<String> fileTree = new LinkedList<>();;

	public static List<String> getFileTree(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				String absolutePath = listFiles[i].getAbsolutePath();
				fileTree.add(absolutePath);
				if (new File(absolutePath).isDirectory())
					getFileTree(absolutePath);
			}
		}
		return fileTree;
	}

	public static void readFileByIO(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return;
		}
		FileInputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = new FileInputStream(filePath);
			reader = new BufferedReader(new InputStreamReader(inputStream));
			String str = null;
			while ((str = reader.readLine()) != null) {
				// 输出文件内容
				System.err.println(str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void readFileByChannel(String filePath) {
		if (StringUtils.isEmpty(filePath)) {
			return;
		}
		try {
			List<String> lists = Files.readAllLines(Paths.get(filePath));
			for (String str : lists) {
				// 输出文件内容
				System.err.println(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
