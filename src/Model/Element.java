package Model;

import se.mau.DA343A.VT25.assignment1.Direction;
import se.mau.DA343A.VT25.assignment1.IElementIcon;
import se.mau.DA343A.VT25.assignment1.ImageResources;

import java.awt.*;
import java.awt.image.BufferedImage;

/***
 * Class that represents a pollution emitter or absorber.
 */
public class Element implements IElement, IElementIcon {


    private BufferedImage image;
    private Direction direction;
    private final int speed;
    private final String name;
    private int x;
    private int y;
    private final int pollution;
    private ImageResources imageResources = new ImageResources();

    /***
     * This constructs a new Element, checking for wrong data in the name and speed parameters
     * @param speed speed of the element
     * @param name name of the element
     * @param x x-coordinate
     * @param y y-coordinate
     * @param pollution amount of pollution emitted or absorbed
     * @author Viktor Vallmark
     *
     */
    public Element (int speed, String name, int x, int y, int pollution) {
        this.x = x;
        this.y = y;
        setDirection();
        this.pollution = pollution;
        if(!(speed < 0)){
            this.speed = speed;
        }else {
            throw new IllegalArgumentException("Speed can't be negative");
        }
        if(!name.isEmpty() || !name.matches("^[0-9]")){
            this.name = name;
        }else {
            throw new IllegalArgumentException("The element needs a proper name. Can't be empty or start with a digit");
        }

        switch (name) {
            case "Bus" -> setIcon(imageResources.getBusImage());
            case "Bike" -> setIcon(imageResources.getBikeImage());
            case "Airplane" -> setIcon(imageResources.getAirPlaneImage());
            case "Car" -> setIcon(imageResources.getCarImage());
            case "Pedestrian" -> setIcon(imageResources.getPedestrianImage());
            case "Woodland" -> setIcon(imageResources.getTreesImage());
            default -> setIcon(imageResources.getFactoryImage());
        }
    }

    /***
     * getter for name attribute
     * @return name of the Element
     */
    public String getName(){
        return this.name;
    }


    private void setDirection(){
        double num = Math.random();

        if(num < 0.24){
            this.direction = Direction.EAST;
        }else if (num >= 0.25 && num < 0.49){
            this.direction = Direction.NORTH;
        }else if (num >= 0.50 && num < 0.74){
            this.direction = Direction.WEST;
        }else {
            this.direction = Direction.SOUTH;
        }
    }

    /***
     * generates a new direction for the element after every time step
     */
    public void newDirection(){
        setDirection();
    }

    /***
     * take the icon and rotates it PI/2, PI, (3/4) PI or 2PI
     * @return a rotated image
     */
    public BufferedImage rotateImage(){

        Direction direction = getDirection();
        BufferedImage image = getIcon();

        Graphics2D graphics = image.createGraphics();

        switch (direction){
            case EAST -> graphics.rotate(2*Math.PI);
            case NORTH -> graphics.rotate(Math.PI/2);
            case WEST -> graphics.rotate(Math.PI);
            case SOUTH -> graphics.rotate((3*Math.PI)/4);
        }

        if(direction == Direction.EAST || direction == Direction.WEST){
            graphics.scale(-1, -1);
        }

        graphics.dispose();

        return image;
    }

    /***
     * shifts the icon left or right depending on direction
     * @param direction Compass direction
     * @return new x-position after time step.
     */
    public int newXPosition(Direction direction) {

        switch(direction) {
            case EAST -> setRow(getRow()+speed);
            case WEST -> setRow(getRow()-speed);
        }

        return getRow();
    }

    /***
     * shifts the icon north or south depending on direction
     * @param direction Compass direction
     * @return new y-position after time step.
     */
    public int newYPosition(Direction direction){
        switch (direction){
            case NORTH -> setCol(getColumn()+speed);
            case SOUTH -> setCol(getColumn()-speed);
        }
        return getColumn();
    }

    /***
     * setter for icon
     * @param image BufferedImage tied to the element
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /***
     * getter for direction
     * @return direction
     */
    public Direction getDirection(){
        return this.direction;
    }

    /***
     * getter for image
     * @return image of the element
     */
    @Override
    public BufferedImage getIcon() {
        return this.image;
    }

    /***
     * setter for image
     * @param newIcon new BufferedImage to replace the old BufferedImage
     */
    public void setIcon(BufferedImage newIcon){
       this.image = newIcon;
    }

    /***
     * translates the icon to the new x-coordinate
     * @param x x-coordinate for the element after time-step
     */
    public void setRow(int x){
       this.x = x;
    }

    /***
     * translates the icon to the new y-coordinate
     * @param y y-coordinate for the element after time-step
     */
    public void setCol(int y){
        this.y = y;
    }

    /***
     * getter for x-coordinate
     * @return x-coordinate
     */
    @Override
    public int getRow() {
        return this.x;
    }

    /***
     * getter for y-coordinate
     * @return y-coordinate
     */
    @Override
    public int getColumn() {
        return this.y;
    }
}
