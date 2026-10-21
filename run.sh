#!/bin/bash
echo "Compiling Movie Ticket Booking System..."
# Try Maven structure first
mkdir -p out
javac -d out src/main/java/com/movieticket/*.java
if [ $? -ne 0 ]; then
    echo "Trying simple src folder..."
    javac -d out src/*.java
fi
echo "Running app..."
java -cp out com.movieticket.MovieTicketBookingApp

# Alternative simple run (if above fails, run without package):
# cd src && javac *.java && java MovieTicketBookingApp
