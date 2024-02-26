public class Faculty extends Person{
    private int departmentId;

    public Faculty(int id, String name, String address, int departmentId) {
        super(id, name, address);
        this.departmentId = departmentId;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

}
