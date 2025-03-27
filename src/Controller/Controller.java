package Controller;

import Model.Element;
import View.AppGUI;
import se.mau.DA343A.VT25.assignment1.IElementIcon;
import se.mau.DA343A.VT25.assignment1.IsLand;
import se.mau.DA343A.VT25.assignment1.MovedOutOfGridException;

import javax.swing.*;
import java.util.List;
import java.util.Vector;

public class Controller {

    private AppGUI gui;
    private final IsLand isLand;
    private List<Element> icons;
    private double[][] pollutionGrid;
    private static final int GRID_SIZE = 100;


    public Controller(IsLand isLand) {
        this.isLand = isLand;
        pollutionGrid = new double[GRID_SIZE][GRID_SIZE];
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


    private void calculatePollution(){
        double[][] temp = new double[GRID_SIZE][GRID_SIZE];
        for(int x = 0; x < GRID_SIZE; x++){
            for(int y = 0; y < GRID_SIZE; y++) {

                double sum = 0;
                if(x > 0){
                    sum += pollutionGrid[x-1][y];
                }
                if(x < GRID_SIZE - 1){
                    sum += pollutionGrid[x+1][y];
                }
                if(y > 0) {
                    sum += pollutionGrid[x][y-1];
                }
                if (y < GRID_SIZE - 1){
                    sum += pollutionGrid[x][y+1];
                }
                sum += pollutionGrid[x][y];
                temp[x][y] = sum / 5.0;
            }
        }

        for(Element element : icons){
            if (element.getName().equals("Woodland")){
                int row = element.getRow();
                int col = element.getColumn();

                temp[row][col] += element.getPollution();

                if(row > 0) temp[row-1][col] -= 2.5;
                if(row < GRID_SIZE - 1) temp[row+1][col] -= 2.5;
                if(col > 0) temp[row][col-1] -= 2.5;
                if(col < GRID_SIZE - 1) temp[row][col+1] -= 2.5;

            }
        }

        for(int x = 0; x < GRID_SIZE; x++){
            for(int y = 0; y < GRID_SIZE; y++){
                temp[x][y] = Math.max(temp[x][y], 0);
            }
        }

        pollutionGrid = temp;
    }

    private void setPollution(){
        for(int x = 0; x < pollutionGrid.length; x++){
            for (int y = 0; y < pollutionGrid[x].length; y++){
                gui.setPollution(x,y,pollutionGrid[x][y]);
            }
        }
    }



    public void updateGUI() throws MovedOutOfGridException {
        Vector<Element> elementsToRemove = new Vector<>(5);
            for (Element element : icons) {
                element.newXPosition(element.getDirection());
                element.newYPosition(element.getDirection());
                element.newDirection();
                try {
                    int row = element.getRow();
                    int col = element.getColumn();

                    if (element.getColumn() >= GRID_SIZE || element.getColumn() < 0 || element.getRow() >= GRID_SIZE || element.getRow() < 0) {
                        throw new MovedOutOfGridException();
                    }

                    pollutionGrid[row][col] += element.getPollution();
                    gui.setPollution(row,col,pollutionGrid[row][col]);
                }catch (MovedOutOfGridException e){
                    elementsToRemove.add(element);
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }catch (IllegalArgumentException e){
                    pollutionGrid[element.getRow()][element.getColumn()] = 0.0f;
                    gui.setPollution(element.getRow(),element.getColumn(),0.0f);
                }

                if ((!isLand.isLand(element.getRow(), element.getColumn())) && (!element.getName().equals("Airplane"))) {
                    elementsToRemove.add(element);
                    JOptionPane.showMessageDialog(null, "Only airplanes can be over water!");
                }
            }
            calculatePollution();
            setPollution();
            icons.removeAll(elementsToRemove);
    }

    public void createElement(int row, int col) throws IllegalArgumentException {
        String element = gui.getSelectedElementType();
        try {
            if ((isLand.isLand(row, col))) {
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
                            }
                        }
                        icons.add(new Element(0, "Woodland", row, col, -5));
                    }
                    default -> {
                        for (IElementIcon icon : icons) {
                            if (icon.getColumn() == col && icon.getRow() == row) {
                                throw new IllegalArgumentException("Can't have two unmovable objects on same position");
                            }
                        }
                        icons.add(new Element(0, "Factory", row, col, 10));
                    }
                }
            } else {
                switch (element) {
                    case "Bus", "Car", "Pedestrian", "Bike", "Woodland", "Factory" ->
                            throw new IllegalArgumentException("Can't be placed on water");
                    default -> icons.add(new Element(5, "Airplane", row, col, 10));
                }
            }
            gui.setIcons(icons);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null,"Can't place element here");
        }
    }
}
