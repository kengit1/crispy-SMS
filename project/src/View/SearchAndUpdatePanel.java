package View;

import Model.Admin;
import Model.Student;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchAndUpdatePanel extends JPanel {

    private final JComboBox<String> searchByComboBox;
    private final JTextField searchField;
    private final JButton searchButton;

    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField ageField;
    private final JTextField genderField;
    private final JTextField departmentField;
    private final JTextField gpaField;
    private final JButton updateButton;

    private final Admin admin;

    private Student currentStudent;

    public SearchAndUpdatePanel(Admin admin) {
        this.admin = admin;
        setLayout(new BorderLayout(10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search By:"));
        searchByComboBox = new JComboBox<>(new String[]{"ID", "Name"});
        searchPanel.add(searchByComboBox);
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        searchButton = new JButton("Search");
        searchPanel.add(searchButton);

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        idField = new JTextField();
        idField.setEditable(false);
        formPanel.add(new JLabel("ID:"));
        formPanel.add(idField);

        nameField = new JTextField();
        nameField.setEditable(false);
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        ageField = new JTextField();
        formPanel.add(new JLabel("Age:"));
        formPanel.add(ageField);

        genderField = new JTextField();
        genderField.setEditable(false);
        formPanel.add(new JLabel("Gender:"));
        formPanel.add(genderField);

        departmentField = new JTextField();
        formPanel.add(new JLabel("Department:"));
        formPanel.add(departmentField);

        gpaField = new JTextField();
        formPanel.add(new JLabel("GPA:"));
        formPanel.add(gpaField);

        updateButton = new JButton("Update Student Details");
        updateButton.setEnabled(false);

        add(searchPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(updateButton, BorderLayout.SOUTH);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSearch();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdate();
            }
        });
    }

    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        String searchType = (String) searchByComboBox.getSelectedItem();

        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("ID".equals(searchType)) {
            currentStudent = admin.SearchStudentByID(searchTerm);
        } else {
            currentStudent = admin.SearchStudentByName(searchTerm);
        }

        if (currentStudent != null) {
            idField.setText(String.valueOf(currentStudent.getID()));
            nameField.setText(currentStudent.getName());
            ageField.setText(String.valueOf(currentStudent.getAge()));
            genderField.setText(currentStudent.getGender());
            departmentField.setText(currentStudent.getDepartment());
            gpaField.setText(String.valueOf(currentStudent.getGPA()));

            updateButton.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "Student not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            updateButton.setEnabled(false);
        }
    }

    private void handleUpdate() {
        if (ageField.getText().isEmpty() || departmentField.getText().isEmpty() || gpaField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all editable fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String id = idField.getText();
            int newAge = Integer.parseInt(ageField.getText());
            String newDepartment = departmentField.getText();
            float newGPA = Float.parseFloat(gpaField.getText());

            boolean success = admin.updateStudent(id, newAge, newDepartment, newGPA);

            if (success) {
                JOptionPane.showMessageDialog(this, "Student details updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update student details.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for Age and GPA.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        currentStudent = null;
        idField.setText("");
        nameField.setText("");
        ageField.setText("");
        genderField.setText("");
        departmentField.setText("");
        gpaField.setText("");
    }
    /*
    public static void main(String[] args)
    {
        new SearchAndUpdatePanel(new Admin());
    }
     */
}