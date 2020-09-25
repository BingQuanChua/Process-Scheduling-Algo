// this is the driver class

public class ProcessScheduling {
    public static void main(String[] args) {
        View view = new View();
        view.setVisible(true);
        new Controller(view);
    }
}