@echo off
echo Compiling Movie Ticket Booking System...
javac -d out src/main/java/com/movieticket/*.java
if %errorlevel% neq 0 (
    echo Trying simple src folder...
    javac src/*.java -d out
)
echo Running app...
java -cp out com.movieticket.MovieTicketBookingApp
pause
