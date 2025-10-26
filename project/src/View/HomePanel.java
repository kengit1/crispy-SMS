package View;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {

    public HomePanel() {
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome to the Student Management System!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel instructionLabel = new JLabel("Please select an operation from the menu on the left.", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        add(welcomeLabel, BorderLayout.CENTER);
        add(instructionLabel, BorderLayout.SOUTH);
    }
}