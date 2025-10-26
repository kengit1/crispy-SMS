package View;

import Model.Login;
import Model.Admin;
import Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

class SMSMainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private final Admin adminController;

    public SMSMainFrame(Admin adminController) {
        this.adminController = adminController;

        setTitle("Student Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel dashboardPanel = createDashboardPanel();

        AddStudentPanel addPanel = new AddStudentPanel(this.adminController);
        DeleteStudentPanel deletePanel = new DeleteStudentPanel(this.adminController);

        JPanel viewUpdatePanel = new JPanel();
        viewUpdatePanel.add(new JLabel("View/Update Student Panel - To be implemented"));


        mainPanel.add(dashboardPanel, "Dashboard");
        mainPanel.add(addPanel, "Add");
        mainPanel.add(viewUpdatePanel, "ViewUpdate");
        mainPanel.add(deletePanel, "Delete");

        add(mainPanel);

        cardLayout.show(mainPanel, "Dashboard");
    }

    private JPanel createDashboardPanel() {
        JPanel dashboard = new JPanel(new BorderLayout(10, 10));

        JLabel welcomeLabel = new JLabel("Welcome to the Student Management System Dashboard!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        dashboard.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        JButton addButton = createDashboardButton("Add Student", new Color(66, 133, 244), Color.WHITE, "Add");
        JButton viewButton = createDashboardButton("View Students", new Color(52, 168, 83), Color.WHITE, "ViewUpdate");
        JButton updateButton = createDashboardButton("Search & Update Student", new Color(251, 188, 5), Color.BLACK, "ViewUpdate");
        JButton deleteButton = createDashboardButton("Delete Student", new Color(234, 67, 53), Color.WHITE, "Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        dashboard.add(buttonPanel, BorderLayout.CENTER);

        return dashboard;
    }

    private JButton createDashboardButton(String text, Color background, Color foreground, String panelName) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(250, 80));
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFocusPainted(false);

        button.addActionListener(e -> cardLayout.show(mainPanel, panelName));
        return button;
    }
}

class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private final Login user;
    private final Admin admin;

    public LoginScreen() {
        this.user = new Login();
        this.admin = new Admin();

        setTitle("Student Management System Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel headerLabel = new JLabel("Student Management System Login", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(headerLabel, BorderLayout.NORTH);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; usernameField = new JTextField(15); formPanel.add(usernameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; passwordField = new JPasswordField(15); formPanel.add(passwordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.setBackground(new Color(66, 133, 244));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        formPanel.add(loginButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        loginButton.addActionListener(e -> attemptLogin());

        JRootPane rootPane = SwingUtilities.getRootPane(this);
        rootPane.setDefaultButton(loginButton);
        setVisible(true);
    }

    private void attemptLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        int validationResult = user.validateLogin(username, password);

        switch (validationResult) {
            case 0:
                JOptionPane.showMessageDialog(this,
                        "Login Successful! Welcome, Admin.",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                openDashboard();
                dispose();
                break;
            case 1:
                JOptionPane.showMessageDialog(this, "Username field must not be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(this, "Password field must not be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(this, "Invalid Username or Password. Please try again.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                break;
            default:
                JOptionPane.showMessageDialog(this, "An unexpected system error occurred during login.", "System Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    private void openDashboard() {
        SMSMainFrame mainFrame = new SMSMainFrame(admin);
        mainFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}

class DeleteStudentPanel extends JPanel {
    private final Admin admin;
    private final JTable table;
    private final DefaultTableModel model;
    private final JTextField searchField;
    private final JButton deleteButton;

    public DeleteStudentPanel(Admin admin) {
        this.admin = admin;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Search:"));
        searchField = new JTextField(20);
        topPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        topPanel.add(searchButton);
        JButton showAllButton = new JButton("Show All");
        topPanel.add(showAllButton);
        add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Student ID", "Name", "Department", "GPA"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        deleteButton = new JButton("Delete Selected");
        deleteButton.setBackground(new Color(234, 67, 53));
        deleteButton.setForeground(Color.WHITE);
        bottomPanel.add(deleteButton);
        add(bottomPanel, BorderLayout.SOUTH);

        loadStudents(admin.getListOfStudents());

        searchButton.addActionListener(e -> searchStudents());
        showAllButton.addActionListener(e -> loadStudents(admin.getListOfStudents()));
        deleteButton.addActionListener(e -> deleteSelectedStudent());
    }

    private void loadStudents(Student[] students) {
        model.setRowCount(0);
        if (students == null) return;
        for (Student s : students) {
            model.addRow(new Object[]{
                    s.getID(),
                    s.getName(),
                    s.getDepartment(),
                    String.format("%.2f", s.getGPA())
            });
        }
    }

    private void searchStudents() {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a student name or ID to search.");
            return;
        }

        Student byID = admin.SearchStudentByID(keyword);
        if (byID != null) {
            loadStudents(new Student[]{byID});
            return;
        }

        Student byName = admin.SearchStudentByName(keyword);
        if (byName != null) {
            loadStudents(new Student[]{byName});
            return;
        }

        JOptionPane.showMessageDialog(this, "No student found matching the keyword: " + keyword + ".");
        loadStudents(admin.getListOfStudents());
    }

    private void deleteSelectedStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        String id = model.getValueAt(row, 0).toString();
        String name = model.getValueAt(row, 1).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete student ID " + id + " (" + name + ")?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = admin.removeStudent(id);

            if (success) {
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting student. Record may not exist.");
            }
            loadStudents(admin.getListOfStudents());
        }
    }
}

class AddStudentPanel extends JPanel {
    private final Admin admin;
    private final JTextField idField, nameField, ageField, gpaField;
    private final JComboBox<String> genderComboBox, deptComboBox;
    private final JButton addButton;

    public AddStudentPanel(Admin admin) {
        this.admin = admin;
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Add New Student Record", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] departments = {"Computer Engineering", "Communications", "Electronics", "Architecture", "Civil"};
        String[] genders = {"Male", "Female"};

        idField = new JTextField(20);
        nameField = new JTextField(20);
        ageField = new JTextField(20);
        gpaField = new JTextField(20);
        deptComboBox = new JComboBox<>(departments);
        genderComboBox = new JComboBox<>(genders);

        int row = 0;
        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Student ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(ageField, gbc);

        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(genderComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(deptComboBox, gbc);

        gbc.gridx = 0; gbc.gridy = row; formPanel.add(new JLabel("GPA / Grade:"), gbc);
        gbc.gridx = 1; gbc.gridy = row++; formPanel.add(gpaField, gbc);

        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2;
        addButton = new JButton("Add Student");
        addButton.setPreferredSize(new Dimension(150, 35));
        addButton.setBackground(new Color(52, 168, 83));
        addButton.setForeground(Color.WHITE);
        formPanel.add(addButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> addNewStudent());
    }

    private void addNewStudent() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText().trim();
        String ageStr = ageField.getText().trim();
        String gpaStr = gpaField.getText().trim();
        String gender = Objects.requireNonNull(genderComboBox.getSelectedItem()).toString();
        String department = Objects.requireNonNull(deptComboBox.getSelectedItem()).toString();

        if (  name.isEmpty() || ageStr.isEmpty() || gpaStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int age;
        double gpa;
        try {
            age = Integer.parseInt(ageStr);
            if (age < 16 || age > 99) {
                JOptionPane.showMessageDialog(this, "Age must be a valid number between 16 and 99.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a valid integer.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            gpa = Double.parseDouble(gpaStr);
            if (gpa < 0.0 || gpa > 4.0) {
                JOptionPane.showMessageDialog(this, "GPA must be a valid number between 0.0 and 4.0.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "GPA must be a valid numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


           boolean   success=admin.addStudent(name,age,gender,department, (float) gpa);
            if (success) {
                JOptionPane.showMessageDialog(this,
                        "Student Added Successfully!\nID: " + id + "\nName: " + name,
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                idField.setText("");
                nameField.setText("");
                ageField.setText("");
                gpaField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error adding student. ID may already exist.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
