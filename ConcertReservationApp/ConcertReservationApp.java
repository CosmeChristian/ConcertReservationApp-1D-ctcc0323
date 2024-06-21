import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class ConcertReservationApp extends JFrame {

    private JPanel frontPanel, mainPanel, createAccountPanel;
    private JLabel titleLabel, usernameLabel, passwordLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;
    private final JLabel imageLabel;

    private Map<String, String> accounts; // username-password pair

    private JLabel firstNameLabel, numberLabel, emailLabel, paymentLabel, seatLabel, priceLabel, totalLabel, quantityLabel;
    private JTextField firstNameField, numberField, emailField, quantityField;
    private JComboBox<String> paymentComboBox, seatComboBox;
    private JButton buyButton, clearButton, logoutButton;
    private JTextArea receiptTextArea;

    private String[] paymentOptions = {"Gcash", "Paypal", "Maya"};
    private String[] seatOptions = {"VIP (+P8,000.00)", "Lower Block (+P6,000.00)", "Upper Block (+P4,000.00)", "GenAd (+P2,000.00)"};

    private double totalCost = 0.0;

    private List<Ticket> purchasedTickets; // List of purchased tickets

    private JPanel seatPanel;
    private List<JButton> seatButtons;
    private int selectedSeatCount = 0;

    public ConcertReservationApp() {
        setTitle("James Arthur's Bitter Sweet Love World Tour Seat Reservation");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        accounts = new HashMap<>();
        purchasedTickets = new ArrayList<>();

        BufferedImage loginImage = null;
        try {
            loginImage = ImageIO.read(new File("C:\\Users\\Kiiyanoribs\\Desktop\\ConcertReservationApp\\Images\\login.jpg")); // path image
        } catch (IOException e) {
        }

        imageLabel = new JLabel(new ImageIcon(loginImage));
        imageLabel.setOpaque(true);
        imageLabel.setBackground(new Color(86, 91, 110));
        imageLabel.setBorder(new EmptyBorder(40, 0, 0, 0));

        createFrontPage();
        createMainPanel();

        add(imageLabel, BorderLayout.NORTH);
        add(frontPanel, BorderLayout.CENTER);
    }

    // Front panel attributes
    private void createFrontPage() {
        frontPanel = new JPanel(new BorderLayout());
        frontPanel.setBackground(new Color(86, 91, 110)); 

        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(new Color(86, 91, 110)); 

        titleLabel = new JLabel("Welcome to James Arthur's Concert Reservation System", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(10, 0, 30, 0));
        titleLabel.setForeground(Color.WHITE);

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
        messageTextArea.setFont(new Font("Serif", Font.BOLD, 18));
        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
        messageTextArea.setBorder(new EmptyBorder(0, 20, 30, 30));
        messageTextArea.setForeground(Color.WHITE); 
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(messageTextArea, gbc);

        // Username outside box
        usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 30, 0, 0);
        loginPanel.add(usernameLabel, gbc);

        // Username inside box
        usernameField = new JTextField(20);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 30);
        loginPanel.add(usernameField, gbc);

        // Password outside box
        passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); 
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 30, 30, 0);
        loginPanel.add(passwordLabel, gbc);

        // Password inside box
        passwordField = new JPasswordField(20);
        gbc.gridx++;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 0, 30, 30);
        loginPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(86, 91, 110)); 

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
                    setSize(1400,800);
                    remove(frontPanel);
                    remove(imageLabel);
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
        createAccountButton.setFont(new Font("Arial", Font.PLAIN, 16));
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
        createAccountPanel.setBackground(new Color(86, 91, 110)); 

        JPanel createAccountFormPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        createAccountFormPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        createAccountFormPanel.setBackground(new Color(86, 91, 110));

        JLabel createAccountTitleLabel = new JLabel("Create Account", JLabel.CENTER);
        createAccountTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        createAccountTitleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        createAccountTitleLabel.setForeground(Color.WHITE); 
        createAccountPanel.add(createAccountTitleLabel, BorderLayout.NORTH);

        JLabel newUsernameLabel = new JLabel("New Username:");
        newUsernameLabel.setForeground(Color.WHITE); 
        createAccountFormPanel.add(newUsernameLabel);

        JTextField newUsernameField = new JTextField();
        createAccountFormPanel.add(newUsernameField);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setForeground(Color.WHITE); 
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
                            "Username and password cannot be empty.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (accounts.containsKey(newUsername)) {
                    JOptionPane.showMessageDialog(ConcertReservationApp.this,
                            "Username already exists. Please choose a different username.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    accounts.put(newUsername, newPassword);
                    JOptionPane.showMessageDialog(ConcertReservationApp.this,
                            "Account created successfully. You can now log in.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    createAccountPanel.setVisible(false);
                }
            }
        });

        JPanel createAccountButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        createAccountButtonPanel.setBackground(new Color(86, 91, 110)); 
        createAccountButtonPanel.add(createAccountSubmitButton);

        createAccountPanel.add(createAccountFormPanel, BorderLayout.CENTER);
        createAccountPanel.add(createAccountButtonPanel, BorderLayout.SOUTH);

        frontPanel.add(createAccountPanel, BorderLayout.SOUTH);
    }

    // Main panel attributes
    private void createMainPanel() {
        mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(86, 91, 110)); 

        titleLabel = new JLabel("James Arthur's Bitter Sweet Love World Tour Seat Reservation", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize(1000, 1000);
                remove(mainPanel);
                add(imageLabel, BorderLayout.NORTH);
                add(frontPanel, BorderLayout.CENTER);
                revalidate();
                repaint();
            }
        });
        topPanel.add(logoutButton, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(86, 91, 110)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        firstNameLabel = new JLabel("Full Name:");
        firstNameLabel.setForeground(Color.WHITE);
        formPanel.add(firstNameLabel, gbc);

        firstNameField = new JTextField(15);
        gbc.gridx++;
        formPanel.add(firstNameField, gbc);

        numberLabel = new JLabel("Contact Number:");
        numberLabel.setForeground(Color.WHITE); 
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(numberLabel, gbc);

        numberField = new JTextField(15);
        gbc.gridx++;
        formPanel.add(numberField, gbc);

        emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE); 
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(15);
        gbc.gridx++;
        formPanel.add(emailField, gbc);

        quantityLabel = new JLabel("Quantity:");
        quantityLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(quantityLabel, gbc);

        quantityField = new JTextField(15);
        gbc.gridx++;
        formPanel.add(quantityField, gbc);

        paymentLabel = new JLabel("Payment Method:");
        paymentLabel.setForeground(Color.WHITE); 
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(paymentLabel, gbc);

        paymentComboBox = new JComboBox<>(paymentOptions);
        gbc.gridx++;
        formPanel.add(paymentComboBox, gbc);

        seatLabel = new JLabel("Select Seat:");
        seatLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(seatLabel, gbc);

        seatComboBox = new JComboBox<>(seatOptions);
        gbc.gridx++;
        formPanel.add(seatComboBox, gbc);

        mainPanel.add(formPanel, BorderLayout.WEST);

        seatPanel = new JPanel(new GridLayout(5, 5, 10, 10));
        seatPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        seatPanel.setBackground(new Color(86, 91, 110)); 
        seatButtons = new ArrayList<>();

        for (int i = 1; i <= 25; i++) {
            JButton seatButton = new JButton("Seat " + i);
            seatButton.setBackground(new Color(197, 222, 255)); 
            seatButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (seatButton.getBackground().equals(Color.GREEN)) {
                        seatButton.setBackground(new Color(197, 222, 255));
                        selectedSeatCount--;
                    } else if (seatButton.getBackground().equals(new Color(197, 222, 255))) {
                        if (selectedSeatCount < Integer.parseInt(quantityField.getText())) {
                            seatButton.setBackground(Color.GREEN);
                            selectedSeatCount++;
                        } else {
                            JOptionPane.showMessageDialog(ConcertReservationApp.this,
                                    "You cannot select more seats than the specified quantity.",
                                    "Selection Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });
            seatButtons.add(seatButton);
            seatPanel.add(seatButton);
        }

        mainPanel.add(seatPanel, BorderLayout.CENTER);

        receiptTextArea = new JTextArea(20, 30);
        receiptTextArea.setEditable(false);
        receiptTextArea.setBorder(BorderFactory.createTitledBorder("Receipt"));
        receiptTextArea.setBackground(new Color(197, 222, 255)); 
        mainPanel.add(new JScrollPane(receiptTextArea), BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(86, 91, 110)); 

        buyButton = new JButton("Buy");
        buyButton.setFont(new Font("Arial", Font.BOLD, 20));
        buyButton.setPreferredSize(new Dimension(120, 50));
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String number = numberField.getText();
                String email = emailField.getText();
                String paymentMethod = (String) paymentComboBox.getSelectedItem();
                String seat = (String) seatComboBox.getSelectedItem();
                int quantity = Integer.parseInt(quantityField.getText());

                if (firstName.isEmpty() || number.isEmpty() || email.isEmpty() || quantity <= 0 || selectedSeatCount != quantity) {
                    JOptionPane.showMessageDialog(ConcertReservationApp.this,
                            "Please fill in all the details correctly and select the correct number of seats.",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    double price = getPrice(seat);
                    double totalCost = price * quantity;
                    Ticket ticket = new Ticket(firstName, number, email, paymentMethod, seat, quantity, totalCost);
                    purchasedTickets.add(ticket);
                    receiptTextArea.append(ticket.toString() + "\n");

                    JOptionPane.showMessageDialog(ConcertReservationApp.this,
                            "Tickets purchased successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);

                    clearFields();
                }
            }
        });

        buttonPanel.add(buyButton);

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 16));
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        buttonPanel.add(clearButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private double getPrice(String seat) {
        switch (seat) {
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

    private void clearFields() {
        firstNameField.setText("");
        numberField.setText("");
        emailField.setText("");
        quantityField.setText("");
        paymentComboBox.setSelectedIndex(0);
        seatComboBox.setSelectedIndex(0);
        selectedSeatCount = 0;
        for (JButton seatButton : seatButtons) {
            seatButton.setBackground(new Color(197, 222, 255));
        }
    }

    private static class Ticket {
        private String firstName;
        private String number;
        private String email;
        private String paymentMethod;
        private String seat;
        private int quantity;
        private double totalCost;

        public Ticket(String firstName, String number, String email, String paymentMethod, String seat, int quantity, double totalCost) {
            this.firstName = firstName;
            this.number = number;
            this.email = email;
            this.paymentMethod = paymentMethod;
            this.seat = seat;
            this.quantity = quantity;
            this.totalCost = totalCost;
        }

        @Override
        public String toString() {
            return "Ticket for " + firstName +
                    "Contact Number: " + number + "\n" +
                    "Email: " + email + "\n" +
                    "Payment Method: " + paymentMethod + "\n" +
                    "Seat: " + seat + "\n" +
                    "Quantity: " + quantity + "\n" +
                    "Total Cost: P" + totalCost + "\n";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConcertReservationApp app = new ConcertReservationApp();
            app.setVisible(true);
        });
    }
}
