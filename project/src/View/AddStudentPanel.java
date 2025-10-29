package View;

import Model.Admin;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddStudentPanel extends JPanel {
    private JPanel Container1;
    private JTextField FnameText;
    private JTextField IDText;
    private JTextField LnameText;
    private JComboBox Departement;
    private JTextField AgeText;
    private JComboBox Gender;
    private JTextField GPAtext;
    private JButton ENTERButton;


    private final Admin admin;


    public AddStudentPanel(Admin admin) {

        this.admin = admin;
        add(Container1);

        ENTERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddStudentOperations();
            }
        });
    }

    private void AddStudentOperations() {
        String ID = IDText.getText().trim();
        String FirstName = FnameText.getText().trim();
        String LastName = LnameText.getText().trim();
        String Age = AgeText.getText().trim();
        String Dep = (String) Departement.getSelectedItem();
        String Gen = (String) Gender.getSelectedItem();
        String GPA = GPAtext.getText().trim();



        if (FirstName.isEmpty() || LastName.isEmpty() || Age.isEmpty() || GPA.isEmpty())
            JOptionPane.showMessageDialog(this, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);

        else if (!ValidName(FirstName))
            JOptionPane.showMessageDialog(this, "Invalid First Name", "Input Error", JOptionPane.ERROR_MESSAGE);

        else if (!ValidName(LastName))
            JOptionPane.showMessageDialog(this, "Invalid Last Name", "Input Error", JOptionPane.ERROR_MESSAGE);

        else if (!ValidAge(Age))
            JOptionPane.showMessageDialog(this, "Invalid Age", "Input Error", JOptionPane.ERROR_MESSAGE);

        else if (!ValidGpa(GPA))
            JOptionPane.showMessageDialog(this, "Invalid GPA", "Input Error", JOptionPane.ERROR_MESSAGE);

        else {
            String FullName = FirstName + " " + LastName;
            boolean flag = false;
            if (ID.isEmpty()) {
                if (admin.addStudent(FullName, Integer.parseInt(Age), Gen, Dep, Float.parseFloat(GPA))) {
                    JOptionPane.showMessageDialog(this, "Student Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    flag = true;
                }
                else
                    JOptionPane.showMessageDialog(this, "Duplicate Record", "Input Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (ValidID(ID))
                    if (admin.addStudent(Integer.parseInt(ID), FullName, Integer.parseInt(Age), Gen, Dep, Float.parseFloat(GPA))){
                        JOptionPane.showMessageDialog(this, "Student Added Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        flag = true;
                    }
                    else
                        JOptionPane.showMessageDialog(this, "Duplicate Record", "Input Error", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(this, "Invalid ID", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
            admin.save();
            if(flag) {
                IDText.setText("");
                FnameText.setText("");
                LnameText.setText("");
                AgeText.setText("");
                GPAtext.setText("");
            }
        }
    }


    private boolean ValidName(String name){
        if(name.matches("[a-zA-Z]+") && name.length() <= 15) return true;
        else return false;
    }

    private boolean ValidID(String ID){
        if(ID.matches("\\d+") && ID.length() == 4 && (Integer.parseInt(ID) < 10000 && Integer.parseInt(ID) >= 9000)) return true;
        else return false;
    }

    private boolean ValidAge(String Age){
        if(Age.matches("\\d+") && (Integer.parseInt(Age) <100 && Integer.parseInt(Age) >16)) return true;
        else return false;
    }

    private boolean ValidGpa(String Gpa){
        String tokens[] = Gpa.split("\\.");
        boolean FirstCondition = false;
        boolean SecondCondition = false;
        boolean ThirdCondition = false;

        if(tokens.length == 2) {
            FirstCondition = tokens[0].matches("\\d+") && tokens[0].length() == 1 && Integer.parseInt(tokens[0]) <= 4;
            SecondCondition = tokens[1].matches("\\d+") && tokens[1].length() == 2;
            ThirdCondition = Gpa.length() == 4;
        }

        if(FirstCondition && SecondCondition && ThirdCondition) return true;
        return false;
    }

}