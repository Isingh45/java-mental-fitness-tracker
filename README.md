# Mental Fitness Tracker (Java)

A Java-based command-line journaling application designed to help users log, manage, and review mental fitness entries.
The application supports persistent storage, searching, sorting, CSV export, and basic emotional trend analysis.

## Features
- Add, modify, and delete journal entries
- Persistent file-based storage with autosave
- Search entries by keyword (key or note content)
- Sort entries by key or by timestamp
- Export entries to CSV format
- Basic keyword-based emotional trend analysis

## Technologies Used
- Java
- HashMap and ArrayList
- File I/O
- LocalDateTime and DateTimeFormatter

## Project Structure
- `MentalFitnessTracker.java` — Main application logic, menu handling, persistence, sorting, and analysis
- `Entry.java` — Data model representing individual journal entries with timestamps

## How to Run
1. Compile the program:
```bash
javac MentalFitnessTracker.java Entry.java
```

2. Run the application:
```bash
java MentalFitnessTracker
```

The application will create and manage a local text file to store journal entries.

## Design Notes

Entries are stored using a HashMap for fast lookup by key

Timestamps are handled using Java’s LocalDateTime and DateTimeFormatter

File-loading logic includes validation to safely handle malformed or legacy data formats

The project emphasizes clean structure, data integrity, and defensive programming rather than UI complexity

## Future Improvements

Enhanced sentiment analysis

Improved command-line interface formatting

Optional graphical user interface
