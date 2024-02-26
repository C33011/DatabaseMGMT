public class App {
    public static void main(String[] args) {
        //Acts as main for this project
        UniversityDatabase database = new UniversityDatabase();
        GUI gui = new GUI(database);
        gui.show();
    }
}