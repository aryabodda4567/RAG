package org.project.ai.queryai.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class DataSplitter {
    public enum DataType {
        DATABASE_SCHEMA,
        DB_RELATED_TEXT,
        GENERAL_TEXT
    }

    private static final Pattern SCHEMA_KEYWORDS = Pattern.compile("(?i)CREATE TABLE|ALTER TABLE|INSERT INTO|PRIMARY KEY|FOREIGN KEY|INDEX");
    private static final Pattern DB_RELATED_KEYWORDS = Pattern.compile("(?i)database|table|column|schema|key|query");

    private static DataType detectDataType(String data) {
        if (SCHEMA_KEYWORDS.matcher(data).find()) {
            return DataType.DATABASE_SCHEMA;
        }
        if (DB_RELATED_KEYWORDS.matcher(data).find()) {
            return DataType.DB_RELATED_TEXT;
        }
        return DataType.GENERAL_TEXT;
    }

    private static List<String> splitSchema(String schema) {
        return splitByDelimiter(schema, ";");
    }

    private static List<String> splitByDelimiter(String text, String delimiter) {
        List<String> chunks = new ArrayList<>();
        String[] parts = text.split(Pattern.quote(delimiter));
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                chunks.add(trimmed + delimiter);
            }
        }
        return chunks;
    }

    private static List<String> splitText(String text, int maxTokens) {
        List<String> chunks = new ArrayList<>();
        int start = 0;
        while (start < text.length()) {
            int end = Math.min(start + maxTokens, text.length());
            // Ensure we split at word boundaries
            while (end < text.length() && !Character.isWhitespace(text.charAt(end))) {
                end--;
            }
            chunks.add(text.substring(start, end).trim());
            start = end;
        }
        return chunks;
    }

    private static List<String> splitDBRelatedText(String text) {
        List<String> chunks = new ArrayList<>();
        String[] lines = text.split("\\n+");

        for (String line : lines) {
            if (SCHEMA_KEYWORDS.matcher(line).find()) {
                chunks.add(line.trim());
            } else {
                chunks.addAll(splitText(line, 2000));
            }
        }
        return chunks;
    }

    public static List<String> splitData(String data) {
        return switch (detectDataType(data)) {
            case DATABASE_SCHEMA -> splitSchema(data);
            case DB_RELATED_TEXT -> splitDBRelatedText(data);
            default -> splitText(data, 20000);
        };
    }

    // Optional: Method to estimate token count
    public static int estimateTokenCount(String text) {
        // Simple token estimation (adjust as needed)
        return text.split("\\s+").length;
    }
}