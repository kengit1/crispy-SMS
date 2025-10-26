package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteStudentPanel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField searchField;
    private JComboBox<String> searchType;
    private final File studentFile = new File("StudentsDatabase.txt");

    public DeleteStudentPanel() {
        setLayout(new BorderLayout());
        model = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Gender", "Dept", "GPA"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        searchType = new JComboBox<>(new String[]{"Student ID", "Name"});
        searchField = new JTextField(10);
        JButton search = new JButton("Search");
        JButton refresh = new JButton("Refresh");
        JButton delete = new JButton("Delete");

        search.addActionListener(e -> searchStudent());
        refresh.addActionListener(e -> loadStudents());
        delete.addActionListener(e -> deleteStudent());

        bottom.add(new JLabel("Search by:"));
        bottom.add(searchType);
        bottom.add(searchField);
        bottom.add(search);
        bottom.add(refresh);
        bottom.add(delete);

        add(bottom, BorderLayout.SOUTH);
        loadStudents();
    }

    private List<String[]> readAllStudents() {
        List<String[]> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(studentFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) list.add(line.split(","));
            }
        } catch (IOException ignored) {}
        return list;
    }

    private void loadStudents() {
        model.setRowCount(0);
        for (String[] s : readAllStudents())
            if (s.length >= 6)
                model.addRow(new Object[]{s[0], s[1], s[2], s[3], s[4], s[5]});
    }

    private void searchStudent() {
        String text = searchField.getText().trim();
        if (text.isEmpty()) return;
        model.setRowCount(0);
        for (String[] s : readAllStudents()) {
            if (searchType.getSelectedItem().equals("Student ID") && s[0].equals(text)
                    || searchType.getSelectedItem().equals("Name") && s[1].equalsIgnoreCase(text)) {
                model.addRow(new Object[]{s[0], s[1], s[2], s[3], s[4], s[5]});
            }
        }
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row == -1) return;
        String idToDelete = String.valueOf(model.getValueAt(row, 0));
        List<String[]> students = readAllStudents();
        students.removeIf(s -> s[0].equals(idToDelete));
        try (PrintWriter pw = new PrintWriter(new FileWriter(studentFile))) {
            for (String[] s : students) pw.println(String.join(",", s));
        } catch (IOException ignored) {}
        loadStudents();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Delete Student Panel");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setSize(700, 400);
            f.add(new DeleteStudentPanel());
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
