package com.movieticket;

/**
 * Booking.java
 * ------------
 * This class represents ONE booking/ticket.
 * It stores who booked, which movie, which show, seat type, etc.
 * It also has logic to calculate total price and generate receipt.
 */
public class Booking {

    // Static counter to give each booking a unique ID (like BKG1001, BKG1002...)
    private static int bookingCounter = 1001;

    // Unique booking ID for this booking
    private String bookingId;

    // Name of the customer who booked
    private String customerName;

    // The movie that was booked (Movie object)
    private Movie movie;

    // Show time selected (e.g., "10:00 AM")
    private String showTime;

    // Seat type selected (e.g., "Regular", "Premium", "VIP")
    private String seatType;

    // Number of tickets booked
    private int numberOfTickets;

    // Total price after calculation
    private double totalPrice;

    /**
     * Constructor - creates a new Booking
     * @param customerName    Name of customer
     * @param movie           Movie object selected
     * @param showTime        Show time string
     * @param seatType        Seat type string
     * @param numberOfTickets Number of tickets
     */
    public Booking(String customerName, Movie movie, String showTime, String seatType, int numberOfTickets) {
        // Generate booking ID like BKG1001 and increase counter for next booking
        this.bookingId = "BKG" + bookingCounter;
        bookingCounter++;

        this.customerName = customerName;
        this.movie = movie;
        this.showTime = showTime;
        this.seatType = seatType;
        this.numberOfTickets = numberOfTickets;

        // Calculate total price as soon as booking is created
        this.totalPrice = calculateTotalPrice();
    }

    /**
     * This method calculates total price
     * Logic: total = basePrice * seatMultiplier * numberOfTickets
     * Regular = 1.0x, Premium = 1.5x, VIP = 2.0x
     * @return total price (double)
     */
    public double calculateTotalPrice() {
        double multiplier = 1.0; // default for Regular

        // Check seat type and set multiplier
        if (seatType.equals("Premium")) {
            multiplier = 1.5; // 50% extra for Premium
        } else if (seatType.equals("VIP")) {
            multiplier = 2.0; // Double price for VIP
        }

        // Formula: (base price of movie * multiplier) * number of tickets
        return movie.getBasePrice() * multiplier * numberOfTickets;
    }

    /**
     * This method creates a formatted receipt string
     * This string will be shown in JTextArea after booking
     * @return receipt text
     */
    public String generateReceipt() {
        String receipt = "";
        receipt += "==================================\n";
        receipt += "      MOVIE TICKET RECEIPT         \n";
        receipt += "==================================\n";
        receipt += "Booking ID   : " + bookingId + "\n";
        receipt += "Customer     : " + customerName + "\n";
        receipt += "----------------------------------\n";
        receipt += "Movie        : " + movie.getTitle() + "\n";
        receipt += "Genre        : " + movie.getGenre() + "\n";
        receipt += "Duration     : " + movie.getDuration() + "\n";
        receipt += "Show Time    : " + showTime + "\n";
        receipt += "Seat Type    : " + seatType + "\n";
        receipt += "Tickets      : " + numberOfTickets + "\n";
        receipt += "Price/Ticket : $" + String.format("%.2f", movie.getBasePrice()) + "\n";
        receipt += "----------------------------------\n";
        receipt += "TOTAL AMOUNT : $" + String.format("%.2f", totalPrice) + "\n";
        receipt += "==================================\n";
        receipt += "  Thank you! Enjoy your movie! :) \n";
        receipt += "==================================\n";
        return receipt;
    }

    // Getters - to access private fields from other classes

    public String getBookingId() {
        return bookingId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getShowTime() {
        return showTime;
    }

    public String getSeatType() {
        return seatType;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public double getTotalPrice() {
        return totalPrice;
    }
}
