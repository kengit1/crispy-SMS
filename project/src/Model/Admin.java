package Model;
import java.util.*;
public class Admin {
    private final StudentDatabase dataBase;

    public Admin() {
        dataBase = new StudentDatabase("StudentsDatabase.txt");
        dataBase.readFromFile();
    }
    //betdilo el ID.
    public boolean addStudent(int ID, String name, int age, String gender, String department, float GPA){
        Student student = new Student(ID,name,age,gender,department,GPA);
        if(dataBase.insertRecord(student)) return true;
        else return false;
    }
    //by3ml generate llID.
    public boolean addStudent(String name,int age,String gender,String department,float GPA){
        Random random = new Random();
        int randomInt = random.nextInt(1000) + 9000;
        StudentDatabase database1 = new StudentDatabase("StudentsDatabase.txt");
        database1.readFromFile();
        while(database1.contains(String.valueOf(randomInt))) randomInt = (randomInt + 1) % 10000;

        Student student = new Student(randomInt,name,age,gender,department,GPA);
        if(dataBase.insertRecord(student)) return true;
        else return false;
    }

    public Student[] getListOfStudents() {
        int x = dataBase.returnAllRecords().size();
        return dataBase.returnAllRecords().toArray(new Student[x]);
    }

    public boolean removeStudent(String key) {
        dataBase.deleteRecord(key);
        return true;
    }

    public boolean updateStudent(String ID, int newAge, String newDepartment, float newGPA) {
        // 1. ابحث عن الطالب باستخدام دالة getRecord الموجودة عندك
        Student studentToUpdate = dataBase.getRecord(ID);

        // 2. تحقق إذا كان الطالب موجوداً
        if (studentToUpdate != null) {
            // 3. قم بتحديث البيانات باستخدام الـ Setters الموجودة في كلاس Student
            studentToUpdate.setAge(newAge);
            studentToUpdate.setDepartment(newDepartment);
            studentToUpdate.setGPA(newGPA);

            // 4. احفظ التغييرات في الملف فوراً [cite: 42, 44]
            dataBase.saveToFile();
            return true; // تمت العملية بنجاح
        }

        // إذا لم يتم العثور على الطالب
        return false;
    }

    public Student SearchStudentByID(String ID){
        return dataBase.getRecord(ID);
    }

    public Student SearchStudentByName(String name){
        return dataBase.getRecordByName(name);
    }

    public void save() {
        dataBase.saveToFile();
    }
}
