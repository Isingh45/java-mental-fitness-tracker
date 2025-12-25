import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MentalFitnessTracker {

    // Shared scanner and dictionary used by all menu functions
    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, Entry> entries = new HashMap<>();

    // Keep ONE formatter here for parsing timestamps from file
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static final String SAVE_FILE = "moodlog.txt"; // keep one file name constant

    public static void main(String[] args) {
        loadEntriesFromFile(); // Load saved entries at startup

        while (true) {
            // Display main menu
            System.out.println("\n=== Mental Fitness Tracker ===");
            System.out.println("1. Add Entry");
            System.out.println("2. View All Entries");
            System.out.println("3. Remove Entry");
            System.out.println("4. Modify Entry");
            System.out.println("5. Exit");
            System.out.println("6. Search Entries");
            System.out.println("7. Sort Entries (by Key)");
            System.out.println("8. Save Entries to File");
            System.out.println("9. Export Entries to CSV");
            System.out.println("10. Sort Entries by Timestamp");
            System.out.println("11. Analyze Emotional Trends");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 11.");
                continue;
            }

            switch (choice) {
                case 1 -> addEntry();
                case 2 -> printEntries();
                case 3 -> removeEntry();
                case 4 -> modifyEntry();
                case 5 -> {
                    saveEntriesToFile(false); // save, but don't spam output
                    System.out.println("Goodbye!");
                    return;
                }
                case 6 -> searchEntry();
                case 7 -> sortEntriesByKey();
                case 8 -> saveEntriesToFile(true);   // manual save -> print message
                case 9 -> exportEntriesToCsv();
                case 10 -> sortEntriesByTimestamp();
                case 11 -> analyzeTrends();
                default -> System.out.println("Invalid option. Please choose 1-11.");
            }
        }
    }

    // -------------------------
    // Core Features
    // -------------------------

    public static void addEntry() {
        System.out.print("Enter a key (e.g., Mood or 2025-06-29): ");
        String key = scanner.nextLine().trim().toLowerCase();

        if (key.isEmpty()) {
            System.out.println("Key cannot be blank.");
            return;
        }

        if (entries.containsKey(key)) {
            Entry existing = entries.get(key);
            System.out.println("An entry already exists for this key:");
            System.out.println("→ " + key + " → " + existing);

            System.out.print("Do you want to overwrite it? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("yes")) {
                System.out.println("Entry was not changed.");
                return;
            }
        }

        System.out.print("Enter your mental fitness note: ");
        String value = scanner.nextLine().trim();
        if (value.isEmpty()) {
            System.out.println("Note cannot be blank.");
            return;
        }

        Entry newEntry = new Entry(value, LocalDateTime.now());
        entries.put(key, newEntry);

        System.out.println("Entry saved! [" + key + " : " + newEntry + "]");
        saveEntriesToFile(false); // autosave quietly
    }

    public static void printEntries() {
        if (entries.isEmpty()) {
            System.out.println("\n=============================");
            System.out.println("No entries found.");
            System.out.println("=============================");
            return;
        }

        System.out.println("\n=============================");
        System.out.println("--- All Entries ---");
        System.out.println("=============================");

        for (String key : entries.keySet()) {
            Entry entry = entries.get(key);
            System.out.println(key + " → " + entry);
        }

        System.out.println("\nTotal entries: " + entries.size());
        System.out.println("=============================");
    }

    public static void removeEntry() {
        System.out.print("Enter the key of the entry to remove: ");
        String key = scanner.nextLine().trim().toLowerCase();

        if (entries.containsKey(key)) {
            entries.remove(key);
            System.out.println("Entry removed successfully.");
            saveEntriesToFile(false);
        } else {
            System.out.println("No entry found with that key.");
        }
    }

    public static void modifyEntry() {
        System.out.print("Enter key of the entry to modify: ");
        String key = scanner.nextLine().trim().toLowerCase();

        if (!entries.containsKey(key)) {
            System.out.println("No entry found with that key.");
            return;
        }

        Entry currentEntry = entries.get(key);
        System.out.println("Current Entry: " + currentEntry);

        System.out.print("Enter the new value: ");
        String newNote = scanner.nextLine().trim();
        if (newNote.isEmpty()) {
            System.out.println("Note cannot be blank.");
            return;
        }

        Entry updatedEntry = new Entry(newNote, LocalDateTime.now());
        entries.put(key, updatedEntry);

        System.out.println("Entry updated!");
        saveEntriesToFile(false);
    }

    public static void searchEntry() {
        System.out.print("Enter keyword to search for: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        boolean found = false;

        for (String key : entries.keySet()) {
            Entry value = entries.get(key);

            if (key.contains(keyword) || value.getNote().toLowerCase().contains(keyword)) {
                System.out.println(key + " → " + value);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching entries found.");
        }
    }

    public static void sortEntriesByKey() {
        if (entries.isEmpty()) {
            System.out.println("No entries to sort.");
            return;
        }

        ArrayList<String> sortedKeys = new ArrayList<>(entries.keySet());
        Collections.sort(sortedKeys);

        System.out.println("\n--- Entries Sorted by Key ---");
        for (String key : sortedKeys) {
            System.out.println(key + " → " + entries.get(key));
        }
        System.out.println("\nTotal entries: " + entries.size());
    }

    // -------------------------
    // Persistence (Save/Load)
    // -------------------------

    // verbose=false -> don't spam output (autosave)
    // verbose=true  -> show success message (manual save)
    public static void saveEntriesToFile(boolean verbose) {
        try (FileWriter writer = new FileWriter(SAVE_FILE)) {
            for (String key : entries.keySet()) {
                Entry entry = entries.get(key);
                // key : note||timestamp
                writer.write(key + " : " + entry.getNote() + "||" + entry.getFormattedTimestamp() + "\n");
            }

            if (verbose) {
                System.out.println("Entries saved to " + SAVE_FILE);
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public static void loadEntriesFromFile() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                // Expect: key : note||timestamp
                String[] parts = line.split(":", 2);
                if (parts.length != 2) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                String key = parts[0].trim().toLowerCase();
                String rightSide = parts[1].trim();

                // Preferred format: note||timestamp
                if (rightSide.contains("||")) {
                    String[] noteParts = rightSide.split("\\|\\|", 2);
                    if (noteParts.length == 2) {
                        String note = noteParts[0].trim();
                        String ts = noteParts[1].trim();

                        LocalDateTime timestamp = parseTimestamp(ts);
                        if (timestamp != null) {
                            entries.put(key, new Entry(note, timestamp));
                        } else {
                            System.out.println("Skipping line with bad timestamp: " + line);
                        }
                        continue;
                    }
                }

                // Backward compatibility if older saved format had [Logged at: ...]
                if (rightSide.contains("[ Logged at: ") && rightSide.endsWith("]")) {
                    int index = rightSide.indexOf("[ Logged at: ");
                    String note = rightSide.substring(0, index).trim();
                    String ts = rightSide.substring(index + 13, rightSide.length() - 1).trim(); // drop trailing
                    LocalDateTime timestamp = parseTimestamp(ts);
                    if (timestamp != null) entries.put(key, new Entry(note, timestamp));
                    continue;
                }

                if (rightSide.contains("[Logged at: ") && rightSide.endsWith("]")) {
                    int index = rightSide.indexOf("[Logged at: ");
                    String note = rightSide.substring(0, index).trim();
                    String ts = rightSide.substring(index + 12, rightSide.length() - 1).trim(); // drop trailing
                    LocalDateTime timestamp = parseTimestamp(ts);
                    if (timestamp != null) entries.put(key, new Entry(note, timestamp));
                    continue;
                }

                System.out.println("Skipping malformed line: " + line);
            }

            System.out.println("Entries loaded from file.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static LocalDateTime parseTimestamp(String ts) {
        try {
            return LocalDateTime.parse(ts, FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    // -------------------------
    // Export + Analysis
    // -------------------------

    public static void exportEntriesToCsv() {
        try (FileWriter csvWriter = new FileWriter("entries.csv")) {
            csvWriter.write("Key,Note,Timestamp\n");

            for (String key : entries.keySet()) {
                Entry entry = entries.get(key);

                String safeNote = "\"" + entry.getNote().replace("\"", "\"\"") + "\"";
                csvWriter.write(key + "," + safeNote + "," + entry.getFormattedTimestamp() + "\n");
            }

            System.out.println("Entries exported to entries.csv");
        } catch (IOException e) {
            System.out.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    public static void sortEntriesByTimestamp() {
        if (entries.isEmpty()) {
            System.out.println("No entries to sort.");
            return;
        }

        List<Map.Entry<String, Entry>> entryList = new ArrayList<>(entries.entrySet());

        // Cleaner comparator (replaces the lambda warning in IntelliJ)
        entryList.sort(Comparator.comparing(e -> e.getValue().getTimestamp()));

        System.out.println("\n--- Entries Sorted by Timestamp ---");
        for (Map.Entry<String, Entry> entry : entryList) {
            System.out.println(entry.getKey() + " → " + entry.getValue());
        }
    }

    public static void analyzeTrends() {
        String[] positiveWords = {"happy", "grateful", "calm", "relaxed", "productive", "excited"};
        String[] negativeWords = {"sad", "angry", "stressed", "anxious", "tired", "lonely"};

        int positiveCount = 0;
        int negativeCount = 0;

        for (Entry entry : entries.values()) {
            String note = entry.getNote().toLowerCase();

            for (String word : positiveWords) {
                if (note.contains(word)) positiveCount++;
            }

            for (String word : negativeWords) {
                if (note.contains(word)) negativeCount++;
            }
        }

        System.out.println("\n--- Entries Trend Analysis ---");
        System.out.println("Total entries scanned: " + entries.size());
        System.out.println("Positive emotion words found: " + positiveCount);
        System.out.println("Negative emotion words found: " + negativeCount);

        if (positiveCount > negativeCount) {
            System.out.println("Overall mood trend: Positive 😊");
        } else if (negativeCount > positiveCount) {
            System.out.println("Overall mood trend: Negative 😟");
        } else {
            System.out.println("Overall mood trend: Balanced 😐");
        }
    }
}
