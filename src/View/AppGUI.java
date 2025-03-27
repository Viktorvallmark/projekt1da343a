package View;

import Model.Element;
import se.mau.DA343A.VT25.assignment1.AirQualityApp;
import Controller.Controller;
import se.mau.DA343A.VT25.assignment1.MovedOutOfGridException;

import javax.swing.*;
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
        try{

            controller.createElement(row,col);
            repaint();
        }catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void setIcons(List<Element> icons){
        this.icons = icons;


    }

    @Override
    protected void buttonNextTimeStepClicked() {
        try {
            controller.updateGUI();
            repaint();
        }catch (MovedOutOfGridException e){
            System.err.println(e.getMessage());
        }

    }

    @Override
    protected List<Element> elementIconsToPaint() {
        return icons;
    }
}
