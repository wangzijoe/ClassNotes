package com.example.stream;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

@Slf4j
public final class FileUtil {

    private FileUtil() {
    }

    // 复制文件
    private static void fileCopy(String source, String target) throws IOException {
        try (InputStream in = new FileInputStream(source)) {
            try (OutputStream out = new FileOutputStream(target)) {
                byte[] buffer = new byte[4096];
                int bytesToRead;
                while ((bytesToRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesToRead);
                }
            }
        }
    }

    //NIO复制文件
    private static void fileCopyNIO(String source, String target) throws IOException {
        try (FileInputStream in = new FileInputStream(source)) {
            try (FileOutputStream out = new FileOutputStream(target)) {
                FileChannel inChannel = in.getChannel();
                FileChannel outChannel = out.getChannel();
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                while (inChannel.read(buffer) != -1) {
                    buffer.flip();
                    outChannel.write(buffer);
                    buffer.clear();
                }
            }
        }
    }

    // 递归遍历目录文件
    private static void showDirectory(File file) {
        _walkDirectory(file, 0);
    }

    private static void _walkDirectory(File f, int level) {
        if (f.isDirectory()) {
            for (File temp : Objects.requireNonNull(f.listFiles())) {
                if (null != temp) _walkDirectory(temp, level + 1);
            }
        } else {
            log.info(f.getName());
        }
    }

    private static void visitFile(String directory) throws IOException {
        Path path = Paths.get(directory);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                log.info(file.getFileName().toString());
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void main(String[] args) {
        showDirectory(new File("D:\\download"));

        try {
            visitFile("D:\\Sexy");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
