# Time Tracker & Life Analytics ⏳

A minimalist, pure-Java CLI application designed to track your daily productive vs. leisure time and provide deep psychological insights into your life's progress and habits.

## Features
*   **Simple Manual Entry:** No complex start/stop timers. Just log what you did and for how long.
*   **Deep Insights:** Analyzes your habits to detect burnout risks, long leisure drains, most frequent tasks, and your overall productivity ratio.
*   **Dot-Matrix Life Progress:** Uses your expected 80-year lifespan and current age to draw a progress bar of your life, mapping how many literal *years* you will spend in Leisure vs. Productivity based on your tracked behavior.
*   **Persistent Storage:** Auto-saves your history in a simple flat text file (`data.txt`). Zero database setup required.

## How to Get It
This project requires **zero** external libraries. As long as you have Java installed on your computer, you can run this immediately.

1. Clone or download this repository so that all `.java` files are in a single folder.
2. Ensure you have the Java Development Kit (JDK) installed. (You can check by opening your terminal and typing `javac -version`).

## How to Run It
Because we've intentionally kept the architecture flat and clean, you won't encounter any messy `package` or `classpath` errors!

**Via Terminal / Command Prompt:**
1. Open your terminal and navigate to the directory containing the files:
   ```bash
   cd path/to/timetracker
   ```
2. Compile all the Java files at once:
   ```bash
   javac *.java
   ```
3. Run the compiled application:
   ```bash
   java Main
   ```

*(If you are using an IDE like VS Code or IntelliJ, you can simply open `Main.java` and click the "Run" or "Play" button!)*

## Commands
Once the app is running, you'll see a `>` prompt. You can type any of the following commands:

- **`add <MODE> <MINUTES> <TASK>`**
  *Logs a new session.*
  *Example:* `add PRODUCTIVE 120 learned sorting algorithms`
  *Modes allowed:* `PRODUCTIVE`, `LEISURE`, `OTHER`
- **`summary`** (or `today` / `week`)
  *Shows a breakdown of your total tracked time separated by Mode.*
- **`insights`**
  *Scans your history and generates warnings if you are logging too much leisure, risking burnout, or have low productivity ratios.*
- **`life`**
  *Shows your literal life progress matrix. (It will ask for your age the very first time you run this).*
- **`life now`**
  *The ultimate dashboard. Combines `summary`, `insights`, and `life` all into one terminal readout.*
- **`exit`**
  *Saves everything and closes the application gracefully.*

## Example Session
```text
Enter command: add LEISURE 100 watched movie
Successfully logged 100 minutes for LEISURE.

[!] New Insights Available:
⚠ Long leisure session detected: watched movie (100 mins)

Enter command: life now

--- LOGGED TOTALS ---
Productive: 0h 0m
Leisure: 1h 40m

--- INSIGHTS ---
⚠ Long leisure session detected: watched movie (100 mins)

--- LIFE PROGRESS ---
Life Progress: 25%
█████░░░░░░░░░░░░░░░

Age: 20
Weeks lived: 1040 / 4160

At current pace:
- 60.0 years in leisure
- 0.0 years in productive work
```
