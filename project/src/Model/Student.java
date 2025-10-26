package Model;
public class Student implements Record {
    private final int ID;
    private int age;
    private float GPA;
    private final String name,gender;
    private String department;

    public Student(int ID, String name, int age, String gender, String department, double GPA){
        this.name = name;
        this.gender = gender;
        this.department = department;
        this.ID = ID;
        this.age = age;
        this.GPA = (float) GPA;
    }

    public int getID(){
        return ID;
    }
    public int getAge(){
        return age;
    }
    public float getGPA(){
        return GPA;
    }
    public String getName(){
        return name;
    }
    public String getGender(){
        return gender;
    }
    public String getDepartment(){
        return department;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setGPA(float GPA){
        this.GPA = GPA;
    }
    @Override
    public String toString(){
        return ID + "," + name + "," + age + "," + gender + "," + department + "," + GPA;
    }

    @Override
    public String lineRepresentation(){
        return this.toString();
    }

    @Override
    public String getSearchKey(){
        return Integer.toString(Integer.parseInt(String.valueOf(ID)));
    }
}
