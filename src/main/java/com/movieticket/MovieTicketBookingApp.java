package com.movieticket;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * MovieTicketBookingApp.java - THEATER DARK EDITION
 * ------------------------------------------------
 * Real cinema vibe: dark charcoal walls, gold lights, red velvet.
 * Like you're inside a theater lobby booking at the counter.
 * Still 100% basic Java Swing - no external libs.
 */
public class MovieTicketBookingApp extends JFrame {

    // === GUI Components ===
    private JComboBox<Movie> movieComboBox;
    private JComboBox<String> showTimeComboBox;
    private JRadioButton regularRadio;
    private JRadioButton premiumRadio;
    private JRadioButton vipRadio;
    private ButtonGroup seatGroup;
    private JSpinner ticketSpinner;
    private JTextField nameField;
    private JLabel priceLabel;
    private JButton bookButton;
    private JButton clearButton;
    private JButton priceButton;
    private JTextArea receiptArea;
    private Movie[] movies;

    // === THEATER COLORS (cinema palette) ===
    private final Color BG_DARK     = new Color(18, 18, 24);     // main window bg - near black
    private final Color PANEL_DARK  = new Color(30, 30, 38);     // form panels - charcoal
    private final Color HEADER_BG   = new Color(12, 12, 16);     // header - deeper black
    private final Color GOLD        = new Color(212, 175, 55);   // cinema gold
    private final Color GOLD_DIM    = new Color(160, 130, 35);   // dim gold for borders
    private final Color RED_VELVET  = new Color(139, 0, 0);     // velvet red
    private final Color RED_LIGHT   = new Color(192, 30, 30);   // lighter red
    private final Color TEXT_WHITE  = new Color(230, 230, 230); // off-white
    private final Color INPUT_BG    = new Color(42, 42, 52);    // input fields
    private final Color RECEIPT_BG  = new Color(255, 248, 220); // ticket stub beige

    public MovieTicketBookingApp() {
        super("CINEMA PREMIERE - Movie Ticket Booking");
        createSampleMovies();

        // Window properties - bigger, cinematic
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(BG_DARK); // dark theater bg

        // Build theater layout
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createFormPanel(), BorderLayout.CENTER);
        add(createReceiptPanel(), BorderLayout.EAST);
        add(createButtonPanel(), BorderLayout.SOUTH);

        addEventListeners();
        updatePriceDisplay();
    }

    private void createSampleMovies() {
        movies = new Movie[5];
        movies[0] = new Movie(1, "Avengers: Endgame", "Action", "3h 01m", 10.00);
        movies[1] = new Movie(2, "The Lion King", "Animation", "1h 58m", 8.00);
        movies[2] = new Movie(3, "Inception", "Sci-Fi", "2h 28m", 9.50);
        movies[3] = new Movie(4, "Titanic", "Romance", "3h 15m", 7.50);
        movies[4] = new Movie(5, "Joker", "Drama", "2h 02m", 9.00);
    }

    // === HEADER - cinema marquee ===
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(HEADER_BG);
        header.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 3, 0, GOLD), // gold bottom line
            BorderFactory.createEmptyBorder(18, 20, 18, 20)
        ));

        // Left: small logo text
        JLabel logo = new JLabel("★ PREMIERE CINEMA ★");
        logo.setFont(new Font("SansSerif", Font.PLAIN, 10));
        logo.setForeground(GOLD_DIM);
        logo.setHorizontalAlignment(SwingConstants.LEFT);

        // Center: main title gold like theater sign
        JLabel title = new JLabel("MOVIE TICKET BOOKING", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 26));
        title.setForeground(GOLD);
        // Add slight shadow effect via border

        // Right: decorative
        JLabel deco = new JLabel("EST. 2024  •  Kampala", SwingConstants.RIGHT);
        deco.setFont(new Font("SansSerif", Font.ITALIC, 10));
        deco.setForeground(new Color(120, 120, 120));

        // Top row with subtitle
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        titlePanel.add(logo, BorderLayout.NORTH);
        titlePanel.add(title, BorderLayout.CENTER);
        
        JLabel sub = new JLabel("Select your show  •  Choose your seats  •  Enjoy the magic", SwingConstants.CENTER);
        sub.setFont(new Font("SansSerif", Font.PLAIN, 11));
        sub.setForeground(new Color(150, 150, 150));
        sub.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));
        titlePanel.add(sub, BorderLayout.SOUTH);

        header.add(titlePanel, BorderLayout.CENTER);
        header.add(deco, BorderLayout.SOUTH);
        return header;
    }

    // === FORM - dark theater counter ===
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 14, 16));
        formPanel.setBackground(PANEL_DARK);
        // Gold titled border like cinema frame
        TitledBorder tb = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(GOLD_DIM, 1), "  ◉  BOOKING COUNTER  "
        );
        tb.setTitleColor(GOLD);
        tb.setTitleFont(new Font("SansSerif", Font.BOLD, 12));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 20, 20, 20), tb
        ));

        // Helper to style labels - off-white on dark
        // --- 1. Customer Name ---
        JLabel l1 = makeLabel("Customer Name:");
        formPanel.add(l1);
        nameField = new JTextField();
        styleInput(nameField);
        nameField.setToolTipText("Your name as on ID");
        formPanel.add(nameField);

        // --- 2. Movie ---
        formPanel.add(makeLabel("Select Movie:"));
        movieComboBox = new JComboBox<>(movies);
        styleCombo(movieComboBox);
        formPanel.add(movieComboBox);

        // --- 3. Show Time ---
        formPanel.add(makeLabel("Show Time:"));
        String[] times = {"10:00 AM  ☀ Matinee", "01:00 PM  ☀ Afternoon", "04:30 PM  🌇 Evening", "08:00 PM  🌙 Prime Time", "11:00 PM  🌃 Late Night"};
        showTimeComboBox = new JComboBox<>(times);
        styleCombo(showTimeComboBox);
        formPanel.add(showTimeComboBox);

        // --- 4. Seat Type ---
        formPanel.add(makeLabel("Seat Type:"));
        JPanel seatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 2));
        seatPanel.setBackground(PANEL_DARK);
        regularRadio = new JRadioButton("Regular", true);
        premiumRadio = new JRadioButton("Premium +50%");
        vipRadio = new JRadioButton("VIP ×2");
        styleRadio(regularRadio); styleRadio(premiumRadio); styleRadio(vipRadio);
        seatGroup = new ButtonGroup();
        seatGroup.add(regularRadio); seatGroup.add(premiumRadio); seatGroup.add(vipRadio);
        seatPanel.add(regularRadio); seatPanel.add(premiumRadio); seatPanel.add(vipRadio);
        // Add hint
        JLabel hint = new JLabel("  (VIP = recliner + popcorn)");
        hint.setFont(new Font("SansSerif", Font.ITALIC, 10));
        hint.setForeground(new Color(140, 140, 140));
        seatPanel.add(hint);
        formPanel.add(seatPanel);

        // --- 5. Tickets ---
        formPanel.add(makeLabel("No. of Tickets:"));
        SpinnerNumberModel sm = new SpinnerNumberModel(1, 1, 10, 1);
        ticketSpinner = new JSpinner(sm);
        styleSpinner(ticketSpinner);
        formPanel.add(ticketSpinner);

        // --- 6. Price ---
        formPanel.add(makeLabel("Total Price:"));
        priceLabel = new JLabel("$0.00");
        priceLabel.setFont(new Font("Serif", Font.BOLD, 22));
        priceLabel.setForeground(GOLD); // gold price like theater
        priceLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD_DIM, 1),
            BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        priceLabel.setOpaque(true);
        priceLabel.setBackground(new Color(42, 36, 18)); // dark gold bg
        formPanel.add(priceLabel);

        // --- 7. Calculate ---
        formPanel.add(new JLabel()); // empty
        priceButton = new JButton("⟳  RECALCULATE PRICE");
        priceButton.setFont(new Font("SansSerif", Font.PLAIN, 11));
        priceButton.setBackground(new Color(50, 50, 60));
        priceButton.setForeground(TEXT_WHITE);
        priceButton.setBorder(BorderFactory.createLineBorder(GOLD_DIM, 1));
        priceButton.setFocusPainted(false);
        formPanel.add(priceButton);

        return formPanel;
    }

    // Helper: make gold-tinted label
    private JLabel makeLabel(String txt) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("SansSerif", Font.BOLD, 13));
        l.setForeground(TEXT_WHITE);
        return l;
    }
    // Helper: style text fields dark
    private void styleInput(JTextField f) {
        f.setBackground(INPUT_BG);
        f.setForeground(TEXT_WHITE);
        f.setCaretColor(GOLD);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(GOLD_DIM, 1),
            BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));
    }
    private void styleCombo(JComboBox<?> cb) {
        cb.setBackground(INPUT_BG);
        cb.setForeground(TEXT_WHITE);
        ((JComponent)cb.getRenderer()).setOpaque(true);
        cb.setBorder(BorderFactory.createLineBorder(GOLD_DIM, 1));
        // Make dropdown items readable
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object val, int idx, boolean sel, boolean foc) {
                super.getListCellRendererComponent(list, val, idx, sel, foc);
                setBackground(sel ? GOLD : INPUT_BG);
                setForeground(sel ? Color.BLACK : TEXT_WHITE);
                setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
                return this;
            }
        });
    }
    private void styleRadio(JRadioButton rb) {
        rb.setBackground(PANEL_DARK);
        rb.setForeground(TEXT_WHITE);
        rb.setFont(new Font("SansSerif", Font.PLAIN, 12));
        rb.setFocusPainted(false);
    }
    private void styleSpinner(JSpinner sp) {
        sp.setBorder(BorderFactory.createLineBorder(GOLD_DIM, 1));
        JComponent editor = sp.getEditor();
        editor.setBackground(INPUT_BG);
        // spinner text field
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField tf = ((JSpinner.DefaultEditor)editor).getTextField();
            tf.setBackground(INPUT_BG);
            tf.setForeground(TEXT_WHITE);
            tf.setCaretColor(GOLD);
        }
    }

    // === RECEIPT - ticket stub ===
    private JPanel createReceiptPanel() {
        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setPreferredSize(new Dimension(380, 0));
        receiptPanel.setBackground(BG_DARK);
        TitledBorder tb = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(GOLD_DIM, 1), "  ✂  TICKET STUB  "
        );
        tb.setTitleColor(GOLD);
        tb.setTitleFont(new Font("Monospaced", Font.BOLD, 11));
        receiptPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 15), tb
        ));

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setBackground(RECEIPT_BG);
        receiptArea.setForeground(new Color(40, 20, 10)); // dark brown text on beige
        receiptArea.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        receiptArea.setText(
            "   ┌──────────────────────┐\n" +
            "   │  NO BOOKING YET      │\n" +
            "   └──────────────────────┘\n" +
            "   Fill details & BOOK!\n" +
            "   ──────────────────────\n" +
            "   Regular  x1.0\n" +
            "   Premium  x1.5  ★ recom.\n" +
            "   VIP      x2.0  ★★ luxe\n" +
            "   ──────────────────────\n" +
            "   Tip: Try Joker VIP x2!"
        );

        JScrollPane scroll = new JScrollPane(receiptArea);
        scroll.setBorder(BorderFactory.createLineBorder(GOLD_DIM, 1));
        scroll.getViewport().setBackground(RECEIPT_BG);

        // Footer note
        JLabel footer = new JLabel("Keep this stub for entry  •  Non-transferable", SwingConstants.CENTER);
        footer.setFont(new Font("SansSerif", Font.ITALIC, 10));
        footer.setForeground(new Color(120, 100, 60));
        footer.setBorder(BorderFactory.createEmptyBorder(8, 0, 2, 0));
        footer.setOpaque(false);

        receiptPanel.add(scroll, BorderLayout.CENTER);
        receiptPanel.add(footer, BorderLayout.SOUTH);
        return receiptPanel;
    }

    // === BUTTONS - theater lobby ===
    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 14));
        btnPanel.setBackground(HEADER_BG);
        btnPanel.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, GOLD));

        // BOOK - gold premiere button
        bookButton = new JButton("  ★  BOOK TICKET  ★  ");
        bookButton.setFont(new Font("Serif", Font.BOLD, 14));
        bookButton.setBackground(GOLD);
        bookButton.setForeground(Color.BLACK);
        bookButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(140, 110, 20), 2),
            BorderFactory.createEmptyBorder(10, 24, 10, 24)
        ));
        bookButton.setFocusPainted(false);
        bookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // CLEAR - velvet red
        clearButton = new JButton("  CLEAR  ");
        clearButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
        clearButton.setBackground(RED_VELVET);
        clearButton.setForeground(TEXT_WHITE);
        clearButton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(RED_LIGHT, 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        clearButton.setFocusPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnPanel.add(bookButton);
        btnPanel.add(clearButton);
        return btnPanel;
    }

    private void addEventListeners() {
        bookButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { handleBooking(); }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { clearForm(); }
        });
        priceButton.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { updatePriceDisplay(); }
        });
        movieComboBox.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) { updatePriceDisplay(); }
        });
        ticketSpinner.addChangeListener(e -> updatePriceDisplay());
        regularRadio.addActionListener(e -> updatePriceDisplay());
        premiumRadio.addActionListener(e -> updatePriceDisplay());
        vipRadio.addActionListener(e -> updatePriceDisplay());
    }

    private String getSelectedSeatType() {
        if (premiumRadio.isSelected()) return "Premium";
        else if (vipRadio.isSelected()) return "VIP";
        else return "Regular";
    }

    private void updatePriceDisplay() {
        Movie m = (Movie) movieComboBox.getSelectedItem();
        if (m == null) return;
        int tickets = (Integer) ticketSpinner.getValue();
        String seat = getSelectedSeatType();
        double mult = 1.0;
        if (seat.equals("Premium")) mult = 1.5;
        else if (seat.equals("VIP")) mult = 2.0;
        double total = m.getBasePrice() * mult * tickets;
        priceLabel.setText("$" + String.format("%.2f", total) + "  •  " + tickets + " × " + seat);
    }

    private void handleBooking() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name!", "Missing Name", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Movie selMovie = (Movie) movieComboBox.getSelectedItem();
        String show = (String) showTimeComboBox.getSelectedItem();
        String seat = getSelectedSeatType();
        int tickets = (Integer) ticketSpinner.getValue();
        Booking booking = new Booking(name, selMovie, show, seat, tickets);
        receiptArea.setText(booking.generateReceipt());
        // Gold success popup feel
        JOptionPane.showMessageDialog(this,
            "BOOKING CONFIRMED! ★\n\nBooking ID: " + booking.getBookingId() +
            "\nMovie: " + selMovie.getTitle() +
            "\nTotal: $" + String.format("%.2f", booking.getTotalPrice()) +
            "\n\nEnjoy the show!",
            "Premiere Cinema - Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearForm() {
        nameField.setText("");
        movieComboBox.setSelectedIndex(0);
        showTimeComboBox.setSelectedIndex(0);
        regularRadio.setSelected(true);
        ticketSpinner.setValue(1);
        receiptArea.setText(
            "   ┌──────────────────────┐\n" +
            "   │  FORM CLEARED        │\n" +
            "   └──────────────────────┘\n" +
            "   Ready for next guest!\n" +
            "   ──────────────────────\n" +
            "   Regular  x1.0\n" +
            "   Premium  x1.5\n" +
            "   VIP      x2.0\n"
        );
        updatePriceDisplay();
    }

    public static void main(String[] args) {
        // Try to set system look for better dark rendering (basic, not complicated)
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}
        SwingUtilities.invokeLater(new Runnable() {
            @Override public void run() {
                MovieTicketBookingApp app = new MovieTicketBookingApp();
                app.setVisible(true);
            }
        });
    }
}
