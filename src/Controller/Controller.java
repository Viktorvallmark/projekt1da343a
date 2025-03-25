package Controller;

import Model.Element;
import View.AppGUI;
import se.mau.DA343A.VT25.assignment1.IElementIcon;
import se.mau.DA343A.VT25.assignment1.IsLand;

import java.util.List;
import java.util.Vector;

public class Controller {

    private AppGUI gui;
    private final IsLand isLand;
    private List<Element> icons;
    private double[][] pollutionGrid;


    public Controller(IsLand isLand) {
        this.isLand = isLand;
        pollutionGrid = new double[100][100];
    }

    public void setIcons(List<Element> icons){
        this.icons = icons;
    }

    public AppGUI getGui() {
        return gui;
    }

    public void setGui(AppGUI gui){
        this.gui = gui;
    }



    public void updateGUI(){

        for(Element element : icons){
            element.newXPosition(element.getDirection());
            element.newYPosition(element.getDirection());
            element.newDirection();
        }
    }

    public void createElement(int row, int col){
        String element = gui.getSelectedElementType();
        List<Element> temp = new Vector<>(10);

        if((isLand.isLand(row, col))){
            switch (element) {
                case "Bus" -> icons.add(new Element(1, "Bus", row, col, 7));
                case "Car" -> icons.add(new Element(1, "Car", row, col, 5));
                case "Airplane" -> icons.add(new Element(5, "Airplane", row, col, 10));
                case "Pedestrian" -> icons.add(new Element(1, "Pedestrian", row, col, 0));
                case "Bike" -> icons.add(new Element(1, "Bike", row, col, 0));
                case "Woodland" -> {
                    for (IElementIcon icon : icons) {
                        if (icon.getColumn() == col && icon.getRow() == row) {
                            throw new IllegalArgumentException("Can't have two unmovable objects on same position");
                        } else {
                            temp.add(new Element(0, "Woodland", row, col, -5));
                        }
                    }
                    icons.addAll(temp);
                }
                default -> {
                    for (IElementIcon icon : icons) {
                        if (icon.getColumn() == col && icon.getRow() == row) {
                            throw new IllegalArgumentException("Can't have two unmovable objects on same position");
                        } else {
                            temp.add(new Element(0, "Factory", row, col, 10));
                        }
                    }
                    icons.addAll(temp);
                }
            }
        }else {
            switch (element){
                case "Bus", "Car", "Pedestrian","Bike","Woodland","Factory" -> throw new IllegalArgumentException("Can't be placed on water");
                default -> icons.add(new Element(5, "Airplane", row, col, 10));
            }
        }
        gui.setIcons(icons);
    }

}
