package View;

import Model.Admin;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private final Admin admin;

    // Components for swapping panels
    private final CardLayout cardLayout;
    private final JPanel mainContentPanel;

    public MainFrame() {
        // 1. Initialize Backend Logic
        admin = new Admin();

        // Setup the Main Frame
        setTitle("Crispy SMS Dashboard"); // [cite: 7, 96]
        setSize(800, 600); // Made it a bit bigger
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Use BorderLayout

        // Create the Dashboard (Button Panel on the West)
        JPanel dashboardPanel = new JPanel();
        dashboardPanel.setLayout(new GridLayout(6, 1, 10, 10)); // 6 rows, 1 column, with spacing
        dashboardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Create buttons
        JButton homeButton = new JButton("Home");
        JButton addButton = new JButton("Add Student");
        JButton searchButton = new JButton("Search / Update");
        JButton deleteButton = new JButton("Delete Student");
        JButton logoutButton = new JButton("Logout");

        // Add buttons to the dashboard
        dashboardPanel.add(homeButton);
        dashboardPanel.add(addButton);
        dashboardPanel.add(searchButton);
        dashboardPanel.add(deleteButton);
        dashboardPanel.add(new JSeparator()); // A small spacer
        dashboardPanel.add(logoutButton);

        //  Create the Main Content Panel (Center)
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);

        //  Create and Add Your Panels
        // These panels are now "cards" in the CardLayout

        HomePanel homePanel = new HomePanel();
        mainContentPanel.add(homePanel, "HOME");

        AddStudentPanel addPanel = new AddStudentPanel(admin); // [cite: 98]
        mainContentPanel.add(addPanel, "ADD");

        SearchAndUpdatePanel searchPanel = new SearchAndUpdatePanel(admin); // [cite: 102]
        mainContentPanel.add(searchPanel, "SEARCH");

        DeleteStudentPanel deletePanel = new DeleteStudentPanel(admin); // [cite: 104]
        mainContentPanel.add(deletePanel, "DELETE");

        //  Add Dashboard and Content Panel to the Frame
        add(dashboardPanel, BorderLayout.WEST);
        add(mainContentPanel, BorderLayout.CENTER);

        //  Add Action Listeners to Buttons to Swap Panels
        homeButton.addActionListener(e -> cardLayout.show(mainContentPanel, "HOME"));
        addButton.addActionListener(e -> cardLayout.show(mainContentPanel, "ADD"));
        searchButton.addActionListener(e -> cardLayout.show(mainContentPanel, "SEARCH"));
        deleteButton.addActionListener(e -> cardLayout.show(mainContentPanel, "DELETE"));

        logoutButton.addActionListener(e -> {
            // Close this window and open the login screen again
            dispose();
            SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
        });

        // Show the Home panel by default
        cardLayout.show(mainContentPanel, "HOME");
    }

/*
    // You can remove this main method if you want.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // This is just for testing this frame directly
            // The *real* app should be run from LoginScreen.java
            new MainFrame().setVisible(true);
        });
    }*/
}