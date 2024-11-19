package org.project.ai.queryai.util;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

 public class FileService {

    public Iterator<Path> fileIterator;

    public void initializeFileIterator(String folderPath) throws IOException {
        // Ensure the folder exists
        Path folder = Paths.get(folderPath);
        if (!Files.exists(folder) || !Files.isDirectory(folder)) {
            throw new IllegalArgumentException("Invalid folder path: " + folderPath);
        }

        // Fetch all `.txt` files and set up the iterator
        List<Path> files = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(folder, "*.txt")) {
            stream.forEach(files::add);
        }
        this.fileIterator = files.iterator();
    }

    public String getNextFileContent() throws IOException {
        if (fileIterator == null || !fileIterator.hasNext()) {
            throw new IllegalStateException("No files available or iterator not initialized.");
        }

        // Read and return the content of the next file
        Path nextFile = fileIterator.next();
        return Files.readString(nextFile);

    }


}
