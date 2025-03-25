package View;

import Model.Element;
import se.mau.DA343A.VT25.assignment1.AirQualityApp;
import se.mau.DA343A.VT25.assignment1.IElementIcon;
import Controller.Controller;
import java.awt.image.BufferedImage;
import java.util.List;

public class AppGUI extends AirQualityApp {

    private List<Element> icons ;
    private Controller controller;

    public AppGUI(String[] elementSelectorTypeNames, BufferedImage mapImage) {
        super(elementSelectorTypeNames, mapImage);
    }

    public void setController(Controller controller){
        this.controller = controller;
    }

    @Override
    protected void mouseClicked(int row, int col) {
        controller.createElement(row,col);
        repaint();
    }

    public void setIcons(List<Element> icons){
        this.icons = icons;
    }

    @Override
    protected void buttonNextTimeStepClicked() {


    }

    @Override
    protected List<Element> elementIconsToPaint() {
        return icons;
    }
}
