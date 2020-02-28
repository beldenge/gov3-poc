package com.ciphertool.poc;

import it.unimi.dsi.fastutil.longs.LongBigArrayBigList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

@Component
public class DataImporter {
    private static Logger log = LoggerFactory.getLogger(DataImporter.class);
    private static final String EXTENSION = ".txt";

    public FunctionData importData(String inputDirectory, int keyLength) throws IOException {
        Path inputDirectoryPath = Paths.get(inputDirectory);

        if (!Files.exists(inputDirectoryPath)) {
            throw new IllegalArgumentException("Input directory '" + inputDirectory + "' does not exist.");
        }

        if (!Files.isDirectory(inputDirectoryPath)) {
            throw new IllegalArgumentException("Input directory must be a directory.");
        }

        long matchingFileCount = Files.find(inputDirectoryPath, 1, (path, basicFileAttributes) -> {
            String filename = path.toString();
            String ext = filename.substring(filename.lastIndexOf('.'));

            if (basicFileAttributes.isRegularFile() && ext.equals(EXTENSION)) {
                return true;
            }

            return false;
        }).count();

        if (matchingFileCount == 0) {
            throw new IllegalStateException("No files of type " + EXTENSION + " found.");
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputDirectoryPath)) {
            List<byte[]> keys = new LinkedList<>();
            LongBigArrayBigList values = new LongBigArrayBigList();

            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    log.info("Skipping directory: {}", entry.toString());
                    continue;
                }

                String filename = entry.toString();
                String ext = filename.substring(filename.lastIndexOf('.'));

                if (!ext.equals(EXTENSION)) {
                    log.info("Skipping file with unexpected file extension: {}", filename);
                    continue;
                }

                try(BufferedReader bufferedReader = Files.newBufferedReader(entry)) {
                    bufferedReader.lines().forEach(line -> {
                        if (line.length() < (keyLength + 1)) {
                            throw new IllegalStateException("Expected line to have at least " + (keyLength + 1) + " characters, but only found " + line.length() + ".");
                        }

                        keys.add(line.substring(0, keyLength).trim().getBytes());
                        values.add(Long.parseLong(line.substring(keyLength).trim()));
                    });
                }
            }

            return new FunctionData(keys, values);
        }
    }
}
