import Controller.Controller;
import View.AppGUI;
import se.mau.DA343A.VT25.assignment1.ImageResources;
import se.mau.DA343A.VT25.assignment1.IsLand;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        String[] elements = {"Bus", "Car", "Bike", "Airplane", "Pedestrian", "Woodland", "Factory"};

        AppGUI gui = new AppGUI(elements, new ImageResources().getMapImage());
        Controller controller = new Controller(new IsLand());
        gui.setIcons(new Vector<>(5));
        controller.setIcons(new Vector<>(5));

        controller.setGui(gui);
        gui.setController(controller);

        controller.getGui().startGUIOnNewThread();

    }
}