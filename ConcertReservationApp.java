import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConcertReservationApp extends JFrame {

    private JPanel frontPanel, mainPanel, createAccountPanel;
    private JLabel titleLabel, usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton, startReservationButton;
    private JTextArea createAccountResultArea;

    private Map<String, String> accounts; // Map to store username-password pairs

    private JLabel firstNameLabel, lastNameLabel, numberLabel, emailLabel, paymentLabel, seatLabel, priceLabel, totalLabel, quantityLabel;
    private JTextField firstNameField, lastNameField, numberField, emailField, quantityField;
    private JComboBox<String> paymentComboBox, seatComboBox;
    private JButton buyButton, cancelButton;
    private JTextArea receiptTextArea;

    private String[] paymentOptions = {"Gcash", "Paypal", "Maya"};
    private String[] seatOptions = {"VIP (+P8,000.00)", "Lower Block (+P6,000.00)", "Upper Block (+P4,000.00)", "GenAd (+P2,000.00)"};

    private double totalCost = 0.0;

    private List<Ticket> purchasedTickets; // List to store purchased tickets

    private JPanel seatPanel;
    private List<JButton> seatButtons;
    private int selectedSeatCount = 0;

    public ConcertReservationApp() {
        setTitle("James Arthur's Bitter Sweet Love World Tour Seat Reservation");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        accounts = new HashMap<>();
        purchasedTickets = new ArrayList<>();

        createFrontPage();
        createMainPanel();

        add(frontPanel, BorderLayout.CENTER);
    }

    private void createFrontPage() {
        frontPanel = new JPanel(new BorderLayout());
        frontPanel.setBackground(new Color(255, 192, 203)); // Pink background

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(255, 192, 203)); // Pink background

        titleLabel = new JLabel("Welcome to James Arthur's Concert Reservation System", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(50, 0, 30, 0));
        titleLabel.setForeground(Color.BLACK); // Black text color


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        loginPanel.add(titleLabel, gbc);

        JTextArea messageTextArea = new JTextArea(
                "Bring your tissues and your hearts as we prepare for a journey filled with nostalgia, melody, and passion! " +
                        "This event promises to bring you to the best trip in the music world with the greatest of all time. " +
                        "We will cry and sing along with the man who can make you cry with just his song."
        );
        messageTextArea.setEditable(false);
        messageTextArea.setOpaque(false);
        messageTextArea.setFont(new Font("Serif", Font.PLAIN, 18));
        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
        messageTextArea.setBorder(new EmptyBorder(0, 20, 30, 30));
        messageTextArea.setForeground(Color.BLACK); // Black text color
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(messageTextArea, gbc);

        // Username label outside the box
        usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLACK); // Black text color
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 30, 0, 0);
        loginPanel.add(usernameLabel, gbc);

        // Username field inside the box
        usernameField = new JTextField(20);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 30);
        loginPanel.add(usernameField, gbc);

        // Password label outside the box
        passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.BLACK); // Black text color
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 30, 30, 0);
        loginPanel.add(passwordLabel, gbc);

        // Password field inside the box
        passwordField = new JPasswordField(20);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 30, 30);
        loginPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 192, 203)); // Pink background

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Serif", Font.PLAIN, 16));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
                    setSize(1200,800);
                    remove(frontPanel);
                    add(mainPanel, BorderLayout.CENTER);
                    revalidate();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(ConcertReservationApp.this,
                            "Invalid username or password. Please try again.",
                            "Login Failed",
                            JOptionPane.ERROR_MESSAGE);
                    // Clear fields on incorrect login attempt
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });
        buttonPanel.add(loginButton);

        createAccountButton = new JButton("Create Account");
        createAccountButton.setFont(new Font("Serif", Font.PLAIN, 16));
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccountPanel.setVisible(true);
            }
        });
        buttonPanel.add(createAccountButton);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        loginPanel.add(buttonPanel, gbc);

        frontPanel.add(loginPanel, BorderLayout.CENTER);

        // Create Account Panel
        createAccountPanel = new JPanel(new BorderLayout());
        createAccountPanel.setVisible(false);
        createAccountPanel.setBackground(new Color(255, 192, 203)); // Pink background

        JPanel createAccountFormPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        createAccountFormPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        createAccountFormPanel.setBackground(new Color(255, 192, 203)); // Pink background

        JLabel createAccountTitleLabel = new JLabel("Create Account", JLabel.CENTER);
        createAccountTitleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        createAccountTitleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        createAccountTitleLabel.setForeground(Color.BLACK); // Black text color
        createAccountPanel.add(createAccountTitleLabel, BorderLayout.NORTH);

        JLabel newUsernameLabel = new JLabel("New Username:");
        newUsernameLabel.setForeground(Color.BLACK); // Black text color
        createAccountFormPanel.add(newUsernameLabel);

        JTextField newUsernameField = new JTextField();
        createAccountFormPanel.add(newUsernameField);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setForeground(Color.BLACK); // Black text color
        createAccountFormPanel.add(newPasswordLabel);

        JPasswordField newPasswordField = new JPasswordField();
        createAccountFormPanel.add(newPasswordField);

        JButton createAccountSubmitButton = new JButton("Create Account");
        createAccountSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());

                if (newUsername.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(ConcertReservationApp.this,
                            "Please fill out all fields.",
                            "Create Account",
                            JOptionPane.ERROR_MESSAGE);
                } else if (accounts.containsKey(newUsername)) {
                    JOptionPane.showMessageDialog(ConcertReservationApp.this,
                            "Username already exists. Please choose a different username.",
                            "Create Account",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    accounts.put(newUsername, newPassword);
                    JOptionPane.showMessageDialog(ConcertReservationApp.this,
                            "Account created successfully!",
                            "Create Account",
                            JOptionPane.INFORMATION_MESSAGE);
                    createAccountPanel.setVisible(false);
                    newUsernameField.setText("");
                    newPasswordField.setText("");
                }
            }
        });
        createAccountFormPanel.add(createAccountSubmitButton);

        JButton cancelCreateAccountButton = new JButton("Cancel");
        cancelCreateAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccountPanel.setVisible(false);
                newUsernameField.setText("");
                newPasswordField.setText("");
            }
        });
        createAccountFormPanel.add(cancelCreateAccountButton);

        createAccountPanel.add(createAccountFormPanel, BorderLayout.CENTER);

        frontPanel.add(createAccountPanel, BorderLayout.SOUTH);
    }

    private void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 192, 203)); // Pink background

        // Top panel for user input
        JPanel inputPanel = new JPanel(new GridLayout(10, 2));
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        inputPanel.setBackground(new Color(255, 192, 203)); // Pink background

        firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setForeground(Color.BLACK); // Black text color
        firstNameField = new JTextField();
        firstNameField.setFont(new Font("Serif", Font.PLAIN, 14));

        lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setForeground(Color.BLACK); // Black text color
        lastNameField = new JTextField();
        lastNameField.setFont(new Font("Serif", Font.PLAIN, 14));

        numberLabel = new JLabel("Phone Number:");
        numberLabel.setForeground(Color.BLACK); // Black text color
        numberField = new JTextField();
        numberField.setFont(new Font("Serif", Font.PLAIN, 14));

        emailLabel = new JLabel("Email Address:");
        emailLabel.setForeground(Color.BLACK); // Black text color
        emailField = new JTextField();
        emailField.setFont(new Font("Serif", Font.PLAIN, 14));

        paymentLabel = new JLabel("Payment Method:");
        paymentLabel.setForeground(Color.BLACK); // Black text color
        paymentComboBox = new JComboBox<>(paymentOptions);
        paymentComboBox.setFont(new Font("Serif", Font.PLAIN, 14));

        seatLabel = new JLabel("Seat Selection:");
        seatLabel.setForeground(Color.BLACK); // Black text color
        seatComboBox = new JComboBox<>(seatOptions);
        seatComboBox.setFont(new Font("Serif", Font.PLAIN, 14));

        priceLabel = new JLabel("Ticket Price:");
        priceLabel.setForeground(Color.BLACK); // Black text color
        totalLabel = new JLabel("Total Cost: P0.00");
        totalLabel.setForeground(Color.BLACK); // Black text color

        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.BLACK); // Black text color
        quantityField = new JTextField();
        quantityField.setFont(new Font("Serif", Font.PLAIN, 14));

        buyButton = new JButton("Buy Tickets");
        buyButton.setFont(new Font("Serif", Font.PLAIN, 16));
        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Serif", Font.PLAIN, 16));

        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        receiptPanel.setBackground(new Color(255, 192, 203)); // Pink background

        receiptTextArea = new JTextArea();
        receiptTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(receiptTextArea);
        scrollPane.setPreferredSize(new Dimension(350, 300));

        receiptPanel.add(scrollPane, BorderLayout.CENTER);

        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);
        inputPanel.add(numberLabel);
        inputPanel.add(numberField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);
        inputPanel.add(paymentLabel);
        inputPanel.add(paymentComboBox);
        inputPanel.add(seatLabel);
        inputPanel.add(seatComboBox);
        inputPanel.add(priceLabel);
        inputPanel.add(totalLabel);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(buyButton);
        inputPanel.add(cancelButton);

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBuyTickets();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCancelReservation();
            }
        });

        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(receiptPanel, BorderLayout.CENTER);

        // Create seat selection panel
        seatPanel = new JPanel(new GridLayout(5, 6, 10, 10));
        seatPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        seatPanel.setBackground(new Color(255, 192, 203)); // Pink background
        createSeatButtons();
        mainPanel.add(seatPanel, BorderLayout.EAST);
    }

    private void createSeatButtons() {
        seatButtons = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            JButton seatButton = new JButton("Seat " + i);
            seatButton.setFont(new Font("Serif", Font.PLAIN, 12));
            seatButton.setPreferredSize(new Dimension(80, 40));
            seatButton.setBackground(Color.WHITE); // Default color for available seats
            seatButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    if (selectedSeatCount < Integer.parseInt(quantityField.getText()) && clickedButton.getBackground() == Color.WHITE) {
                        clickedButton.setBackground(Color.PINK); // Highlight selected seat
                        selectedSeatCount++;
                    }
                }
            });
            seatButtons.add(seatButton);
            seatPanel.add(seatButton);
        }
    }

    private void handleBuyTickets() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = numberField.getText();
        String email = emailField.getText();
        String paymentMethod = (String) paymentComboBox.getSelectedItem();
        String seatSelection = (String) seatComboBox.getSelectedItem();
        int quantity = Integer.parseInt(quantityField.getText());
        double ticketPrice = getPrice(seatSelection);

        if (isValidInput(firstName, lastName, phoneNumber, email) && quantity > 0) {
            if (selectedSeatCount == quantity) {
                for (int i = 0; i < quantity; i++) {
                    totalCost += ticketPrice;
                    Ticket ticket = new Ticket(firstName, lastName, phoneNumber, email, paymentMethod, seatSelection, ticketPrice);
                    purchasedTickets.add(ticket);
                }
                updateTotalCost();
                updateReceipt();
                clearFields();

                // Display thank you message
                JOptionPane.showMessageDialog(ConcertReservationApp.this,
                        "Thank you for purchasing " + quantity + " ticket(s)! Your total cost is P" + totalCost + ".",
                        "Thank You!",
                        JOptionPane.INFORMATION_MESSAGE);

                // Ask user if they want to buy more tickets or exit
                int choice = JOptionPane.showOptionDialog(ConcertReservationApp.this,
                        "What would you like to do next?",
                        "Buy More Tickets or Exit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new String[]{"Buy More Tickets", "Exit"},
                        "Buy More Tickets");

                if (choice == JOptionPane.YES_OPTION) {
                    // User chose to buy more tickets
                    // Optionally clear purchased tickets list and update display
                    purchasedTickets.clear();
                    totalCost = 0.0;
                    updateReceipt();
                    updateTotalCost();
                    // Reset seat selection UI
                    for (JButton button : seatButtons) {
                        button.setBackground(Color.WHITE);
                    }
                    seatComboBox.setSelectedIndex(0);
                } else {
                    // User chose to exit the system
                    JOptionPane.showMessageDialog(ConcertReservationApp.this,
                            "Thank you for using the Concert Reservation System. Goodbye!",
                            "Exit",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0); // Exit the application
                }
            } else {
                JOptionPane.showMessageDialog(ConcertReservationApp.this,
                        "Please select exactly " + quantity + " seats.",
                        "Invalid Seat Selection",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(ConcertReservationApp.this,
                    "Please fill out all fields correctly and enter a valid quantity.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleCancelReservation() {
        int choice = JOptionPane.showConfirmDialog(ConcertReservationApp.this,
                "Are you sure you want to cancel?",
                "Cancel Reservation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(ConcertReservationApp.this,
            "Reservation canceled. Thank you for visiting!",
            "Reservation Canceled",
            JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }
}

private double getPrice(String seatSelection) {
    switch (seatSelection) {
        case "VIP (+P8,000.00)":
            return 8000.00;
        case "Lower Block (+P6,000.00)":
            return 6000.00;
        case "Upper Block (+P4,000.00)":
            return 4000.00;
        case "GenAd (+P2,000.00)":
            return 2000.00;
        default:
            return 0.0;
    }
}

private void updateTotalCost() {
    totalLabel.setText(String.format("Total Cost: P%.2f", totalCost));
}

private void updateReceipt() {
    for (Ticket ticket : purchasedTickets) {
        receiptTextArea.append("-----------------------------\n");
        receiptTextArea.append("Name: " + ticket.getFirstName() + " " + ticket.getLastName() + "\n");
        receiptTextArea.append("Phone Number: " + ticket.getPhoneNumber() + "\n");
        receiptTextArea.append("Email Address: " + ticket.getEmail() + "\n");
        receiptTextArea.append("Payment Method: " + ticket.getPaymentMethod() + "\n");
        receiptTextArea.append("Seat: " + ticket.getSeatSelection() + "\n");
        receiptTextArea.append("Ticket Price: P" + ticket.getTicketPrice() + "\n");
        receiptTextArea.append("-----------------------------\n");
    }
}

private void clearFields() {
    firstNameField.setText("");
    lastNameField.setText("");
    numberField.setText("");
    emailField.setText("");
    paymentComboBox.setSelectedIndex(0);
    seatComboBox.setSelectedIndex(0);
    quantityField.setText("");
    selectedSeatCount = 0; // Reset selected seat count
}

private boolean isValidInput(String firstName, String lastName, String phoneNumber, String email) {
    return !firstName.isEmpty() && !lastName.isEmpty() && !phoneNumber.isEmpty() && !email.isEmpty();
}

private class Ticket {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String paymentMethod;
    private String seatSelection;
    private double ticketPrice;

    public Ticket(String firstName, String lastName, String phoneNumber, String email, String paymentMethod, String seatSelection, double ticketPrice) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.paymentMethod = paymentMethod;
        this.seatSelection = seatSelection;
        this.ticketPrice = ticketPrice;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getSeatSelection() {
        return seatSelection;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
}

public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new ConcertReservationApp().setVisible(true);
        }
    });
}
}

