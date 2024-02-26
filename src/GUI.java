import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
//Importing all applicable packages

public class GUI {
    //Initializes JTable and JTextField variables
    private UniversityDatabase database;
    private JFrame frame;
    private JTable studentsTable;
    private JTable facultiesTable;
    private JTable departmentsTable;
    private JTextField studentIdField;
    private JTextField studentNameField;
    private JTextField studentAddressField;
    private JTextField studentHoursField;
    private JTextField facultyIdField;
    private JTextField facultyNameField;
    private JTextField facultyAddressField;
    private JTextField facultyDepartmentIdField;
    private JTextField departmentIdField;
    private JTextField departmentNameField;
    private JTextField departmentLocationField;

    //Initializes Constructor
    public GUI(UniversityDatabase database) {
        this.database = database;
        initialize();
    }

    //Initializes the GUI components
    private void initialize() {
        frame = new JFrame("University Database Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1, 10, 10));
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //Creates the input panels for the different tables
        JPanel studentPanel = createInputPanel("Add Student", "ID:", "Name:", "Address:", "Hours Taken:");
        JPanel facultyPanel = createInputPanel("Add Faculty", "ID:", "Name:", "Address:", "Department ID:");
        JPanel departmentPanel = createInputPanel("Add Department", "ID:", "Name:", "Location:");

        inputPanel.add(studentPanel);
        inputPanel.add(facultyPanel);
        inputPanel.add(departmentPanel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        //Creates the buttons
        JButton saveButton = new JButton("Save Data");
        JButton loadButton = new JButton("Load Data");
        JButton addStudentButton = new JButton("Add Student");
        JButton addFacultyButton = new JButton("Add Faculty");
        JButton addDepartmentButton = new JButton("Add Department");
        JButton showAllDataButton = new JButton("Show All Data");

        //Handler for the student button. Uses id, name, address, and hoursTaken to add to table
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateStudentFields()) return;
                int id = Integer.parseInt(studentIdField.getText());
                String name = studentNameField.getText();
                String address = studentAddressField.getText();
                int hoursTaken = Integer.parseInt(studentHoursField.getText());
                database.addStudent(new Student(id, name, address, hoursTaken));
                updateTables();
            }
        });

        //Handler for the faculty button. Uses id, name, address, and departmentID to add to table
        addFacultyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateFacultyFields()) return;
                int id = Integer.parseInt(facultyIdField.getText());
                String name = facultyNameField.getText();
                String address = facultyAddressField.getText();
                int departmentId = Integer.parseInt(facultyDepartmentIdField.getText());
                database.addFaculty(new Faculty(id, name, address, departmentId));
                updateTables();
            }
        });

        //Handler for the department button. Uses id, name, and location to add to table
        addDepartmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!validateDepartmentFields()) return;
                int id = Integer.parseInt(departmentIdField.getText());
                String name = departmentNameField.getText();
                String location = departmentLocationField.getText();
                database.addDepartment(new Department(id, name, location));
                updateTables();
            }
        });

        //Handler for saving the tables, outputting to university_data.dat in the project folder
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                database.saveToFile("university_data.dat");
                System.out.println("Data saved to file.");
            }
        });

        //Handler for opening the data file to import table into management software
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open file chooser dialog. Does not path to anywhere specific as of now
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(frame);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    database.loadFromFile(selectedFile.getAbsolutePath());
                    updateTables();
                    System.out.println("Data loaded from file.");
                }
            }
        });

        //Creates a new window that displays all tables
        showAllDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllData();
            }
        });

        buttonPanel.add(addStudentButton);
        buttonPanel.add(addFacultyButton);
        buttonPanel.add(addDepartmentButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(showAllDataButton);

        studentsTable = new JTable();
        facultiesTable = new JTable();
        departmentsTable = new JTable();

        JScrollPane studentsScrollPane = new JScrollPane(studentsTable);
        JScrollPane facultiesScrollPane = new JScrollPane(facultiesTable);
        JScrollPane departmentsScrollPane = new JScrollPane(departmentsTable);

        JPanel textAreaPanel = new JPanel();
        textAreaPanel.setLayout(new GridLayout(1, 3, 10, 10));
        textAreaPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        textAreaPanel.add(studentsScrollPane);
        textAreaPanel.add(facultiesScrollPane);
        textAreaPanel.add(departmentsScrollPane);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(textAreaPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    //Creates the input panel for all applicable data
    private JPanel createInputPanel(String title, String... labels) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(labels.length + 1, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(title));
    
        for (String label : labels) {
            JLabel jLabel = new JLabel(label);
            panel.add(jLabel);
            JTextField jTextField = new JTextField();
            panel.add(jTextField);
            // Assign text fields to corresponding variables
            if (label.equals("ID:")) {
                if (title.equals("Add Student")) {
                    studentIdField = jTextField;
                } else if (title.equals("Add Faculty")) {
                    facultyIdField = jTextField;
                } else if (title.equals("Add Department")) {
                    departmentIdField = jTextField;
                }
            } else if (label.equals("Name:")) {
                if (title.equals("Add Student")) {
                    studentNameField = jTextField;
                } else if (title.equals("Add Faculty")) {
                    facultyNameField = jTextField;
                } else if (title.equals("Add Department")) {
                    departmentNameField = jTextField;
                }
            } else if (label.equals("Address:")) {
                if (title.equals("Add Student")) {
                    studentAddressField = jTextField;
                } else if (title.equals("Add Faculty")) {
                    facultyAddressField = jTextField;
                }
            } else if (label.equals("Hours Taken:")) {
                studentHoursField = jTextField;
            } else if (label.equals("Department ID:")) {
                facultyDepartmentIdField = jTextField;
            } else if (label.equals("Location:")) { // Assign department location field
                departmentLocationField = jTextField;
            }
        }
        return panel;
    }

    //Ensures that all fields are of valid data types when submitting Student data into the table
    private boolean validateStudentFields() {
        try {
            int id = Integer.parseInt(studentIdField.getText());
            if (id < 0) {
                throw new NumberFormatException();
            }
            @SuppressWarnings("unused")
            String name = studentNameField.getText();
            @SuppressWarnings("unused")
            String address = studentAddressField.getText();
            int hoursTaken = Integer.parseInt(studentHoursField.getText());
            if (hoursTaken < 0) {
                throw new NumberFormatException();
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid student data. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //Ensures that all fields are of valid data types when submitting Faculty data into the table
    private boolean validateFacultyFields() {
        try {
            int id = Integer.parseInt(facultyIdField.getText());
            if (id < 0) {
                throw new NumberFormatException();
            }
            @SuppressWarnings("unused")
            String name = facultyNameField.getText();
            @SuppressWarnings("unused")
            String address = facultyAddressField.getText();
            int departmentId = Integer.parseInt(facultyDepartmentIdField.getText());
            if (departmentId < 0) {
                throw new NumberFormatException();
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid faculty data. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //Ensures that all fields are of valid data types when submitting Department data into the table
    private boolean validateDepartmentFields() {
        try {
            int id = Integer.parseInt(departmentIdField.getText());
            if (id < 0) {
                throw new NumberFormatException();
            }
            @SuppressWarnings("unused")
            String name = departmentNameField.getText();
            @SuppressWarnings("unused")
            String location = departmentLocationField.getText();
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid department data. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //Updates the table to include all the data in the input panels
    private void updateTables() {
        List<Student> students = database.getStudents();
        DefaultTableModel studentsModel = new DefaultTableModel(new Object[]{"Student ID", "Name", "Address", "Hours Taken"}, 0);
        for (Student student : students) {
            studentsModel.addRow(new Object[]{student.getId(), student.getName(), student.getAddress(), student.getHoursTaken()});
        }
        studentsTable.setModel(studentsModel);

        List<Faculty> faculties = database.getFaculties();
        DefaultTableModel facultiesModel = new DefaultTableModel(new Object[]{"Faculty ID", "Name", "Address", "Department ID"}, 0);
        for (Faculty faculty : faculties) {
            facultiesModel.addRow(new Object[]{faculty.getId(), faculty.getName(), faculty.getAddress(), faculty.getDepartmentId()});
        }
        facultiesTable.setModel(facultiesModel);

        List<Department> departments = database.getDepartments();
        DefaultTableModel departmentsModel = new DefaultTableModel(new Object[]{"Department ID", "Name", "Location"}, 0);
        for (Department department : departments) {
            departmentsModel.addRow(new Object[]{department.getId(), department.getName(), department.getLocation()});
        }
        departmentsTable.setModel(departmentsModel);
    }

    //Creates a new window that shows all the data on the tables in a raw format.
    private void showAllData() {
        JFrame allDataframe = new JFrame("All Data");
        allDataframe.setSize(600, 400);

        JPanel allDataPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        allDataPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextArea studentsDataArea = new JTextArea();
        JTextArea facultiesDataArea = new JTextArea();
        JTextArea departmentsDataArea = new JTextArea();

        studentsDataArea.setEditable(false);
        facultiesDataArea.setEditable(false);
        departmentsDataArea.setEditable(false);

        JScrollPane studentsScrollPane = new JScrollPane(studentsDataArea);
        JScrollPane facultiesScrollPane = new JScrollPane(facultiesDataArea);
        JScrollPane departmentsScrollPane = new JScrollPane(departmentsDataArea);

        List<Student> students = database.getStudents();
        StringBuilder studentsText = new StringBuilder();
        for (Student student : students) {
            studentsText.append(student.getId()).append("\t").append(student.getName()).append("\t")
                    .append(student.getAddress()).append("\t").append(student.getHoursTaken()).append("\n");
        }
        studentsDataArea.setText(studentsText.toString());

        List<Faculty> faculties = database.getFaculties();
        StringBuilder facultiesText = new StringBuilder();
        for (Faculty faculty : faculties) {
            facultiesText.append(faculty.getId()).append("\t").append(faculty.getName()).append("\t")
                    .append(faculty.getAddress()).append("\t").append(faculty.getDepartmentId()).append("\t");
        }
        facultiesDataArea.setText(facultiesText.toString());

        List<Department> departments = database.getDepartments();
        StringBuilder departmentsText = new StringBuilder();
        for (Department department : departments) {
            departmentsText.append(department.getId()).append("\t").append(department.getName()).append("\t").append(department.getLocation()).append("\n");
        }
        departmentsDataArea.setText(departmentsText.toString());

        allDataPanel.add(studentsScrollPane);
        allDataPanel.add(facultiesScrollPane);
        allDataPanel.add(departmentsScrollPane);

        allDataframe.add(allDataPanel);
        allDataframe.setVisible(true);
    }

    public void show() {
        updateTables();
        frame.setVisible(true);
    }
}
