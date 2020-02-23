package com.ciphertool.poc;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataImporter {
    private static Logger log = LoggerFactory.getLogger(DataImporter.class);
    private static final String EXTENSION = ".txt";
    private static final String DELIMITER =  " ";

    public FunctionData importData(String inputDirectory) throws IOException {
        Path inputDirectoryPath = Paths.get(inputDirectory);

        if (!Files.exists(inputDirectoryPath)) {
            throw new IllegalArgumentException("Input directory '" + inputDirectory + "' does not exist.");
        }

        if (!Files.isDirectory(inputDirectoryPath)) {
            throw new IllegalArgumentException("Input directory must be a directory.");
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(inputDirectoryPath)) {
            List<byte[]> keys = new ArrayList<>();
            LongArrayList values = new LongArrayList();

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
                        String[] parts = line.split(DELIMITER);

                        if (parts.length != 2) {
                            throw new IllegalStateException("Expected line to have two parts, but file " + entry.toString() + " contains a line with " + parts.length + " parts.");
                        }

                        keys.add(parts[0].getBytes());
                        values.add(Long.parseLong(parts[1]));
                    });
                }
            }

            return new FunctionData(keys, values);
        }
    }
}
