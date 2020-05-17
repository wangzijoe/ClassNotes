package com.example.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.Test;

import com.example.utils.FileUtils;


public class FileTest {

	
	@Test
	public void testReadFile() {
		FileUtils.readFileByChannel("D:\\workSapces\\Japanese\\中级_上\\No_06.md");
	}
	
	@Test
	public void testReadFile1() throws Exception {
		Path path = Paths.get("D:\\workSapces\\Japanese\\中级_上\\No_06.md");
		String readString = Files.readString(path);
		System.err.println(readString);
	}
	
	@Test
	public void testUUID() {
		UUID randomUUID = UUID.randomUUID();
		System.err.println(randomUUID);
		System.err.println(randomUUID.toString().length());
		System.err.println(randomUUID.toString().substring(20, 36));
	}
}
