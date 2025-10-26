package Model;
import java.util.*;
public class Admin {
    private final StudentDatabase dataBase;

    public Admin() {
        dataBase = new StudentDatabase("StudentsDatabase.txt");
        dataBase.readFromFile();
    }

    public boolean addStudent(String name, int age, String gender, String department, float GPA){
        Random random = new Random();
        int randomInt = random.nextInt(1000) + 9000;
        StudentDatabase database1 = new StudentDatabase("StudentsDatabase.txt");
        database1.readFromFile();
        while(database1.contains(String.valueOf(randomInt))) randomInt = (randomInt + 1) % 10000;
        Student student = new Student(randomInt,name,age,gender,department,GPA);
        dataBase.insertRecord(student);
        return true;
    }
    public Student[] getListOfStudents() {
        int x = dataBase.returnAllRecords().size();
        return dataBase.returnAllRecords().toArray(new Student[x]);
    }

    public boolean removeStudent(String key) {
        dataBase.deleteRecord(key);
        return true;
    }
    public Student SearchStudentByID(String ID){
        return dataBase.getRecord(ID);
    }
    public Student SearchStudentByName(String name){
        return dataBase.getRecordByName(name);
    }
    public void logout() {
        dataBase.saveToFile();
    }
}
