public class Student extends Person{
    private int hoursTaken;

    public Student(int id, String name, String address, int hoursTaken) {
        super(id, name, address);
        this.hoursTaken = hoursTaken;
    }

    public int getHoursTaken() {
        return hoursTaken;
    }

    public void setHoursTaken(int hoursTaken) {
        this.hoursTaken = hoursTaken;
    }
}
