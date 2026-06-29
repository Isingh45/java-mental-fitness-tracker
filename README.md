# Mental Fitness Tracker (Java)

## Overview
A Java-based command-line journaling application designed to help users log, manage, and review mental fitness entries. The application supports persistent storage, searching, sorting, CSV export, and basic emotional trend analysis. The codebase is engineered entirely with native Java tools, focusing heavily on zero-dependency execution, robust data persistence, and defensive programming paradigms rather than UI abstraction.

## Features
* Add, Modify, and Delete Journal Entries:** Supports a full interactive data lifecycle allowing real-time creation, in-place string modification, and memory-safe deletion of targeted entry records.
* **Persistent File-Based Storage with Autosave: Automatically saves journal entries to a local file whenever entries are added, modified, or removed, preserving data across application sessions.
* **Search Entries by Keyword:** Implements an algorithmic string-matching loop that scans both unique dictionary key markers and entry contents to filter data pools instantly.
* **Sort Entries by Key or Timestamp:** Offers comparative chronological and lexicographical sorting, mapping decoupled data streams into ordered visual arrays.
* **Export Entries to CSV Format:** Features a data export engine that formats runtime string pools with quote-escapes into compliant, comma-separated values for external data processing.
* **Basic Keyword-Based Emotional Trend Analysis:** Processes a rudimentary lexical search algorithm across memory addresses to count predefined positive and negative keywords to provide a basic indication of overall emotional trends.

## Technologies Used
* **Java Core Utilities:** Standard execution engine built without third-party frameworks to maintain a minimal runtime memory footprint.
* **HashMap and ArrayList Data Structuring:** Leverages native Java Collections, using a HashMap for high-performance $O(1)$ key lookups coupled with sequential ArrayLists for sorting routines.
* **File I/O Engine:** Utilizes Java `FileWriter`, `File`, and `Scanner` tools to coordinate cross-boundary file reading and serialization.
* **LocalDateTime & DateTimeFormatter APIs:** Manages consistent temporal state definitions using precise modern datetime abstractions for data logging and chronological parsing.

## How to Run
1. Compile the program using the native compiler:
   ```bash
   javac MentalFitnessTracker.java Entry.java
