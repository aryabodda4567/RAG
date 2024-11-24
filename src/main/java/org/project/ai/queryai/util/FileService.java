package org.project.ai.queryai.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class FileService {

    private final List<Path> files = new ArrayList<>();
    public Iterator<Path> fileIterator;
    private List<Charset> charsetsToTry = Arrays.asList(
            StandardCharsets.UTF_8,
            StandardCharsets.ISO_8859_1,
            StandardCharsets.US_ASCII,
            Charset.forName("Windows-1252")
    );

    // Initialize file iterator for the given folder
    public void initializeFileIterator(String folderPath) throws IOException {
        Path folder = Paths.get(folderPath);
        if (!Files.exists(folder) || !Files.isDirectory(folder)) {
            throw new IllegalArgumentException("Invalid folder path: " + folderPath);
        }

        // Fetch all `.txt` files recursively using Files.walk()
        Files.walk(folder)
                .filter(Files::isRegularFile)
                .forEach(files::add);

        // Check if any files are found
        if (files.isEmpty()) {
            throw new IllegalStateException("No files found in the specified directory.");
        }

        // Set the iterator for the files
        this.fileIterator = files.iterator();
    }

    // Get the next file's content
    public String getNextFileContent() throws IOException {
        if (fileIterator == null || !fileIterator.hasNext()) {
            throw new IllegalStateException("No files available or iterator not initialized.");
        }

        // Read the content of the next file
        Path nextFile = fileIterator.next();

        // Attempt to read the file using various charsets
        for (Charset charset : charsetsToTry) {
            try {
                System.out.println("Attempting to read file: " + nextFile.toAbsolutePath() + " with charset: " + charset.name());
                return Files.readString(nextFile, charset);
            } catch (MalformedInputException e) {
                System.err.println("Failed to read file with charset " + charset.name() + ": " + nextFile);
            }
        }

        // If none of the charsets worked, throw an exception
        throw new IOException("Unable to read file with any of the provided charsets: " + nextFile.toAbsolutePath());
    }
}
