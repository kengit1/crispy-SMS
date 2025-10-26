package Model;
import java.util.*;
public class Admin {
    private final StudentDatabase dataBase;

    public Admin() {
        dataBase = new StudentDatabase("StudentsDatabase.txt");
        dataBase.readFromFile();
    }
    //betdilo el ID.
    public void addStudent(int ID,String name,int age,String gender,String department,float GPA){
        Student student = new Student(ID,name,age,gender,department,GPA);
        dataBase.insertRecord(student);
    }
    //by3ml generate llID.
    public void addStudent(String name,int age,String gender,String department,float GPA){
        Random random = new Random();
        int randomInt = random.nextInt(1000) + 9000;
        StudentDatabase database1 = new StudentDatabase("StudentsDatabase.txt");
        database1.readFromFile();
        while(database1.contains(String.valueOf(randomInt))) randomInt = (randomInt + 1) % 10000;

        Student student = new Student(randomInt,name,age,gender,department,GPA);
        dataBase.insertRecord(student);
    }

    public Student[] getListOfStudents() {
        int x = dataBase.returnAllRecords().size();
        return dataBase.returnAllRecords().toArray(new Student[x]);
    }

    public void removeStudent(String key) {
        dataBase.deleteRecord(key);
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
