import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {

    private final String note;              // journal text
    private final LocalDateTime timestamp;  // structured time

    // Formatter used consistently across the app
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Constructor
    public Entry(String note, LocalDateTime timestamp) {
        this.note = note;
        this.timestamp = timestamp;
    }

    // Getter methods (controlled access)
    public String getNote() {
        return note;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Used when saving to file
    public String getFormattedTimestamp() {
        return timestamp.format(FORMATTER);
    }

    // Controls console display
    @Override
    public String toString() {
        return note + " [Logged at: " + getFormattedTimestamp() + "]";
    }
}
