package View;

import Model.Admin;
import Model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DeleteStudentPanel extends JPanel {
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

        JOptionPane.showMessageDialog(this, "No student found.");
        loadStudents(admin.getListOfStudents());
    }

    private void deleteSelectedStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
            return;
        }

        String id = model.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete student ID " + id + "?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            admin.removeStudent(id);
            admin.logout();
            JOptionPane.showMessageDialog(this, "Student deleted successfully!");
            loadStudents(admin.getListOfStudents());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Admin admin = new Admin();
            JFrame f = new JFrame("Delete Student Panel");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(700, 400);
            f.add(new DeleteStudentPanel(admin));
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
