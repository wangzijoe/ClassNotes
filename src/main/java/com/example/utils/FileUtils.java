package com.example.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

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

}
