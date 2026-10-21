
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MovieTicketBookingApp.java
 * --------------------------
 * This is the MAIN class - it creates the GUI using Java Swing.
 * Swing is Java's built-in library for making windows, buttons, dropdowns etc.
 *
 * Features:
 * 1. Select Movie from dropdown
 * 2. Select Show Time
 * 3. Choose Seat Type (Regular/Premium/VIP)
 * 4. Choose number of tickets
 * 5. Enter customer name
 * 6. See live price update
 * 7. Book ticket and get receipt
 * 8. Clear/Reset form
 *
 * This class extends JFrame (which is a window)
 */
public class MovieTicketBookingApp extends JFrame {

    // === GUI Components (declare at top so we can use them in all methods) ===

    // Dropdown to select movie
    private JComboBox<Movie> movieComboBox;

    // Dropdown to select show time
    private JComboBox<String> showTimeComboBox;

    // Radio buttons for seat type (only one can be selected)
    private JRadioButton regularRadio;
    private JRadioButton premiumRadio;
    private JRadioButton vipRadio;

    // Group to make radio buttons mutually exclusive
    private ButtonGroup seatGroup;

    // Spinner to select number of tickets (1 to 10)
    private JSpinner ticketSpinner;

    // Text field for customer name
    private JTextField nameField;

    // Label to show calculated price (live update)
    private JLabel priceLabel;

    // Buttons
    private JButton bookButton;
    private JButton clearButton;
    private JButton priceButton; // extra button to calculate price

    // Text area to show receipt
    private JTextArea receiptArea;

    // Array of movies (sample data)
    private Movie[] movies;

    /**
     * Constructor - this runs when we create new MovieTicketBookingApp()
     * It sets up the whole window and its components
     */
    public MovieTicketBookingApp() {
        // Call parent JFrame constructor and set window title
        super("Movie Ticket Booking System");

        // Step 1: Prepare sample movie data
        createSampleMovies();

        // Step 2: Set up the window properties
        setSize(900, 650); // Window size: width 900, height 650
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app when X is clicked
        setLocationRelativeTo(null); // Center window on screen
        setLayout(new BorderLayout(10, 10)); // Main layout with 10px gaps

        // Step 3: Create all panels and add to window
        add(createHeaderPanel(), BorderLayout.NORTH);   // Top header
        add(createFormPanel(), BorderLayout.CENTER);    // Center form
        add(createReceiptPanel(), BorderLayout.EAST);   // Right side receipt
        add(createButtonPanel(), BorderLayout.SOUTH);   // Bottom buttons

        // Step 4: Add listeners (what happens when buttons are clicked)
        addEventListeners();

        // Step 5: Do one initial price calculation
        updatePriceDisplay();
    }

    /**
     * Creates sample movie data
     * In real app this would come from database, here we hardcode for simplicity
     */
    private void createSampleMovies() {
        // Create 5 sample movies with id, title, genre, duration, basePrice
        movies = new Movie[5];
        movies[0] = new Movie(1, "Avengers: Endgame", "Action", "3h 01m", 10.00);
        movies[1] = new Movie(2, "The Lion King", "Animation", "1h 58m", 8.00);
        movies[2] = new Movie(3, "Inception", "Sci-Fi", "2h 28m", 9.50);
        movies[3] = new Movie(4, "Titanic", "Romance", "3h 15m", 7.50);
        movies[4] = new Movie(5, "Joker", "Drama", "2h 02m", 9.00);
    }

    /**
     * Creates top header panel with title
     * @return JPanel with header
     */
    private JPanel createHeaderPanel() {
        // Create panel with background color
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(30, 30, 120)); // Dark blue background
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10)); // Padding

        // Create title label
        JLabel titleLabel = new JLabel("🎬  MOVIE TICKET BOOKING SYSTEM  🎬");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22)); // Font style
        titleLabel.setForeground(Color.WHITE); // White text

        headerPanel.add(titleLabel); // Add label to panel
        return headerPanel;
    }

    /**
     * Creates center form panel with all inputs
     * @return JPanel with form fields
     */
    private JPanel createFormPanel() {
        // Main form panel with GridLayout (6 rows, 2 columns) + gaps
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 15));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), " Booking Details "));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(15, 15, 15, 15), // Outer padding
                BorderFactory.createTitledBorder("Booking Details") // Inner titled border
        ));

        // --- 1. Customer Name ---
        formPanel.add(new JLabel("Customer Name:")); // Left label
        nameField = new JTextField(); // Right input field
        nameField.setToolTipText("Enter your full name"); // Hint on hover
        formPanel.add(nameField);

        // --- 2. Movie Selection ---
        formPanel.add(new JLabel("Select Movie:"));
        movieComboBox = new JComboBox<>(movies); // Dropdown filled with movies array
        formPanel.add(movieComboBox);

        // --- 3. Show Time Selection ---
        formPanel.add(new JLabel("Show Time:"));
        String[] showTimes = {"10:00 AM", "01:00 PM", "04:30 PM", "08:00 PM", "11:00 PM"};
        showTimeComboBox = new JComboBox<>(showTimes); // Dropdown for times
        formPanel.add(showTimeComboBox);

        // --- 4. Seat Type (Radio Buttons) ---
        formPanel.add(new JLabel("Seat Type:"));
        JPanel seatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Small panel for radios
        seatPanel.setBackground(Color.WHITE);
        regularRadio = new JRadioButton("Regular ($1.0x)", true); // Default selected
        premiumRadio = new JRadioButton("Premium ($1.5x)");
        vipRadio = new JRadioButton("VIP ($2.0x)");

        // Group them so only one can be selected
        seatGroup = new ButtonGroup();
        seatGroup.add(regularRadio);
        seatGroup.add(premiumRadio);
        seatGroup.add(vipRadio);

        seatPanel.add(regularRadio);
        seatPanel.add(premiumRadio);
        seatPanel.add(vipRadio);
        formPanel.add(seatPanel);

        // --- 5. Number of Tickets (Spinner: 1 to 10, step 1) ---
        formPanel.add(new JLabel("No. of Tickets:"));
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1); // value, min, max, step
        ticketSpinner = new JSpinner(spinnerModel);
        formPanel.add(ticketSpinner);

        // --- 6. Price Display ---
        formPanel.add(new JLabel("Total Price:"));
        priceLabel = new JLabel("$0.00"); // Will be updated dynamically
        priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        priceLabel.setForeground(new Color(0, 128, 0)); // Green color
        formPanel.add(priceLabel);

        // --- 7. Extra: Calculate Price Button ---
        formPanel.add(new JLabel("")); // Empty cell
        priceButton = new JButton("Calculate Price");
        priceButton.setBackground(new Color(255, 193, 7)); // Yellow button
        formPanel.add(priceButton);

        return formPanel;
    }

    /**
     * Creates right side receipt panel
     * @return JPanel with receipt area
     */
    private JPanel createReceiptPanel() {
        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setPreferredSize(new Dimension(380, 0)); // Fixed width 380px
        receiptPanel.setBorder(BorderFactory.createTitledBorder(" Your Receipt "));

        // Text area to show receipt (non-editable)
        receiptArea = new JTextArea();
        receiptArea.setEditable(false); // User cannot type here
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Monospaced for alignment
        receiptArea.setText("  No booking yet...\n  Fill details and click BOOK!\n\n  --------------------------------\n  Prices:\n  Regular = Base Price x 1.0\n  Premium = Base Price x 1.5\n  VIP     = Base Price x 2.0\n");

        // Scroll pane so if text is long, user can scroll
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        receiptPanel.add(scrollPane, BorderLayout.CENTER);

        return receiptPanel;
    }

    /**
     * Creates bottom button panel (Book and Clear)
     * @return JPanel with buttons
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Centered with gaps
        buttonPanel.setBackground(new Color(240, 240, 240));

        // Book button - green
        bookButton = new JButton("  BOOK TICKET  ");
        bookButton.setFont(new Font("Arial", Font.BOLD, 14));
        bookButton.setBackground(new Color(40, 167, 69)); // Green
        bookButton.setForeground(Color.WHITE);
        bookButton.setFocusPainted(false);

        // Clear button - red
        clearButton = new JButton("  CLEAR FORM  ");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setBackground(new Color(220, 53, 69)); // Red
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);

        buttonPanel.add(bookButton);
        buttonPanel.add(clearButton);

        return buttonPanel;
    }

    /**
     * Adds event listeners to buttons
     * This defines what happens when user clicks buttons
     */
    private void addEventListeners() {

        // --- Book Button Click ---
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBooking(); // Call booking method
            }
        });

        // --- Clear Button Click ---
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm(); // Call clear method
            }
        });

        // --- Calculate Price Button Click ---
        priceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePriceDisplay(); // Update price label
            }
        });

        // --- Live price update when movie changes ---
        movieComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePriceDisplay();
            }
        });

        // --- Live price update when ticket count changes ---
        ticketSpinner.addChangeListener(e -> updatePriceDisplay());

        // --- Live price update when seat type changes ---
        regularRadio.addActionListener(e -> updatePriceDisplay());
        premiumRadio.addActionListener(e -> updatePriceDisplay());
        vipRadio.addActionListener(e -> updatePriceDisplay());
    }

    /**
     * Gets currently selected seat type as String
     * @return "Regular" or "Premium" or "VIP"
     */
    private String getSelectedSeatType() {
        if (premiumRadio.isSelected()) {
            return "Premium";
        } else if (vipRadio.isSelected()) {
            return "VIP";
        } else {
            return "Regular"; // Default
        }
    }

    /**
     * Calculates and shows price in priceLabel
     * Called whenever user changes movie/seats/tickets
     */
    private void updatePriceDisplay() {
        // Get selected movie from dropdown
        Movie selectedMovie = (Movie) movieComboBox.getSelectedItem();
        if (selectedMovie == null) return; // Safety check

        // Get number of tickets from spinner
        int tickets = (Integer) ticketSpinner.getValue();

        // Get seat type
        String seatType = getSelectedSeatType();

        // Calculate multiplier
        double multiplier = 1.0;
        if (seatType.equals("Premium")) multiplier = 1.5;
        else if (seatType.equals("VIP")) multiplier = 2.0;

        // Calculate total: basePrice * multiplier * tickets
        double total = selectedMovie.getBasePrice() * multiplier * tickets;

        // Show in label with 2 decimal places
        priceLabel.setText("$" + String.format("%.2f", total));
    }

    /**
     * Handles booking logic when BOOK button is clicked
     */
    private void handleBooking() {
        // 1. Validate customer name (must not be empty)
        String customerName = nameField.getText().trim(); // trim removes extra spaces
        if (customerName.isEmpty()) {
            // Show error popup
            JOptionPane.showMessageDialog(this,
                    "Please enter your name!",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return; // Stop here, don't book
        }

        // 2. Get all selected values
        Movie selectedMovie = (Movie) movieComboBox.getSelectedItem();
        String showTime = (String) showTimeComboBox.getSelectedItem();
        String seatType = getSelectedSeatType();
        int tickets = (Integer) ticketSpinner.getValue();

        // 3. Create a Booking object (uses Booking class)
        Booking booking = new Booking(customerName, selectedMovie, showTime, seatType, tickets);

        // 4. Generate receipt and show in text area
        String receipt = booking.generateReceipt();
        receiptArea.setText(receipt);

        // 5. Show success popup
        JOptionPane.showMessageDialog(this,
                "Booking Successful!\nBooking ID: " + booking.getBookingId() +
                        "\nTotal: $" + String.format("%.2f", booking.getTotalPrice()),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Clears/resets the form to default values
     */
    private void clearForm() {
        nameField.setText(""); // Clear name
        movieComboBox.setSelectedIndex(0); // First movie
        showTimeComboBox.setSelectedIndex(0); // First show time
        regularRadio.setSelected(true); // Regular seat
        ticketSpinner.setValue(1); // 1 ticket
        receiptArea.setText("  Form cleared!\n  Ready for new booking...\n\n  --------------------------------\n  Prices:\n  Regular = Base Price x 1.0\n  Premium = Base Price x 1.5\n  VIP     = Base Price x 2.0\n");
        updatePriceDisplay(); // Reset price
    }

    /**
     * Main method - entry point of program
     * This is where Java starts execution
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // SwingUtilities.invokeLater ensures GUI is created on correct thread (Event Dispatch Thread)
        // This is best practice for Swing apps
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Create and show the app window
                MovieTicketBookingApp app = new MovieTicketBookingApp();
                app.setVisible(true); // Make window visible
            }
        });
    }
}
