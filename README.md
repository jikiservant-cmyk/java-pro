# 🎬 Movie Ticket Booking System - Java Swing

> Simple Java Swing project for **class assignment** - made with basic Java only, no complicated stuff. Every line has clear comments!

### ✨ Features
- Select Movie (5 sample movies with price)
- Select Show Time (5 slots)
- Choose Seat Type: **Regular (1.0x) / Premium (1.5x) / VIP (2.0x)**
- Choose Number of Tickets (1 to 10)
- Live Price Update
- Book Ticket → Generate Receipt with Booking ID
- Clear Form button
- Input validation (name required)

### 📂 Project Structure

```
java-pro/
├── src/main/java/com/movieticket/
│   ├── Movie.java                  # Class 1: Movie data (POJO)
│   ├── Booking.java                # Class 2: Booking logic + receipt
│   └── MovieTicketBookingApp.java  # Class 3: Main GUI (Swing) + main()
├── src/                            # Simple flat version (no package) - easiest for assignment
│   ├── Movie.java
│   ├── Booking.java
│   └── MovieTicketBookingApp.java
├── pom.xml                         # Maven build file (Java 8)
├── run.bat / run.sh                # One-click run scripts
└── README.md
```

### 🧱 Classes Used (OOP)

| Class | Purpose | Concepts |
|-------|---------|----------|
| `Movie` | Stores movie details | Encapsulation, Constructor, Getters, toString() |
| `Booking` | Handles booking + price calculation | Static variable, Methods, Logic |
| `MovieTicketBookingApp` | Builds GUI + handles clicks | Inheritance (extends JFrame), Event Handling |

> **All features use classes** as required - no logic in main() without objects!

### 🚀 How to Run

#### Option 1: Easiest (No Maven, just javac) - For Assignment
```bash
# Go to simple folder
cd src

# Compile all 3 files
javac Movie.java Booking.java MovieTicketBookingApp.java

# Run the app (window will open)
java MovieTicketBookingApp
```

#### Option 2: With Maven structure
```bash
javac -d out src/main/java/com/movieticket/*.java
java -cp out com.movieticket.MovieTicketBookingApp
```

#### Option 3: With Maven (if you have Maven)
```bash
mvn compile exec:java -Dexec.mainClass="com.movieticket.MovieTicketBookingApp"
# or build jar
mvn package
java -jar target/movie-ticket-booking-1.0.0.jar
```

#### Option 4: Double-click scripts
- Windows: double-click `run.bat`
- Mac/Linux: `chmod +x run.sh && ./run.sh`

### 🖥️ How to Use App
1. Enter your name
2. Pick a movie from dropdown
3. Pick show time
4. Choose seat type
5. Select ticket count
6. See live price update
7. Click **BOOK TICKET** → receipt shows on right + success popup
8. Click **CLEAR FORM** to reset

### 💡 Price Formula
```
Total = Movie Base Price × Seat Multiplier × Tickets
Regular = 1.0x  | Premium = 1.5x | VIP = 2.0x
Example: Avengers ($10) × VIP (2.0) × 2 tickets = $40
```

### 📝 For Teacher

- **Language:** Pure Java (Swing is built-in, no external libs)
- **Java Version:** 8+ compatible
- **Comments:** Every class, method and block is commented in simple English
- **Simplicity:** No database, no threads, no lambda complications - only basic `if-else`, arrays, anonymous `ActionListener`

---
Made for assignment - simple, clean, commented! 🎓
