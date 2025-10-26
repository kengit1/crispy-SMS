package View;

import Model.Login;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// CHANGE: Made class non-public (best practice if it's only called from main)
class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private Login user;


    public LoginScreen() {
        this.user = new Login();

        setTitle("Student Managment System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel headerLabel = new JLabel("Student Management System Login", SwingConstants.CENTER); // [cite: 95]
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(headerLabel, BorderLayout.NORTH);
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        usernameField = new JTextField(15);
        formPanel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField(15);
        formPanel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.setBackground(new Color(66, 133, 244));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        formPanel.add(loginButton, gbc);
        add(formPanel, BorderLayout.CENTER);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attemptLogin();
            }
        });
        JRootPane rootPane = SwingUtilities.getRootPane(this);
        rootPane.setDefaultButton(loginButton);
        // setVisible(true); // Moved setVisible to the main method
    }

    private void attemptLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        int validationResult = user.validateLogin(username, password);
        switch (validationResult) {
            case 0:
                // Don't show a popup, just open the dashboard
                openDashboard();
                dispose(); // Close the login window
                break;
            case 1:
                JOptionPane.showMessageDialog(this,
                        "Username field must not be empty.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(this,
                        "Password field must not be empty.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(this,
                        "Invalid Username or Password. Please try again.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                break;
            default:
                JOptionPane.showMessageDialog(this,
                        "An unexpected system error occurred during login.",
                        "System Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void openDashboard() {

        SwingUtilities.invokeLater(() -> {
            MainFrame dashboard = new MainFrame();
            dashboard.setVisible(true);
        });
    }

    /**
     * This is now the MAIN ENTRY POINT for your entire application.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen login = new LoginScreen();
            login.setVisible(true);
        });
    }
}