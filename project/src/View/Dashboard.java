package View;

import Model.Admin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame{
    private JTextArea WelcomeText;
    private JPanel panel1;
    private JButton Add_Student;
    private JButton Delete_Student;
    private JButton Search_Studnets;
    private JButton Show_Studnets;

    public Dashboard() {
        setVisible(true);
        setSize(800,600);
        setTitle("Add Student");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel1);
        Add_Student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openAddStudent();
                dispose();
            }
        });
        Delete_Student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                openDeleteStudent();
                dispose();
            }
        });
        Search_Studnets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });
        Show_Studnets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void openAddStudent(){
        AddStudentPanel panel1 = new AddStudentPanel();
    }

    private void openDeleteStudent(){
        Admin admin = new Admin();
        JFrame f = new JFrame("Delete Student Panel");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(700, 400);
        f.add(new DeleteStudentPanel(admin));
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
