import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class UniversityDatabase {
    private List<Student> students;
    private List<Faculty> faculties;
    private List<Department> departments;

    public UniversityDatabase() {
        students = new ArrayList<>();
        faculties = new ArrayList<>();
        departments = new ArrayList<>();
    }

    // Add a student to the database
    public void addStudent(Student student) {
        students.add(student);
    }

    // Add a faculty member to the database
    public void addFaculty(Faculty faculty) {
        faculties.add(faculty);
    }

    // Add a department to the database
    public void addDepartment(Department department) {
        departments.add(department);
    }

    // Get list for the students
    public List<Student> getStudents() {
        return students;
    }

    // Get list for the Faculty
    public List<Faculty> getFaculties() {
        return faculties;
    }

    // Get list for the Departments
    public List<Department> getDepartments() {
        return departments;
    }

    // Modify student data in the database
    public void modifyStudent(int id, Student newData) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId() == id) {
                students.set(i, newData);
                break;
            }
        }
    }

    // Modify faculty data in the database
    public void modifyFaculty(int id, Faculty newData) {
        for (int i = 0; i < faculties.size(); i++) {
            if (faculties.get(i).getId() == id) {
                faculties.set(i, newData);
                break;
            }
        }
    }

    // Save data to a file
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(students);
            oos.writeObject(faculties);
            oos.writeObject(departments);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load data from a file
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            students = (List<Student>) ois.readObject();
            faculties = (List<Faculty>) ois.readObject();
            departments = (List<Department>) ois.readObject();
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
