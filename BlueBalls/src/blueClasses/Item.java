package blueClasses;//change based on program
import java.awt.Color;
import java.awt.Graphics;

/*
 * Dane K
 * December 13
 * Handle attributes and behaviours common to all visual elements in Blue Balls.
 */

/**
 *
 * @author dakir3750
 */

abstract public class Item {
    //Attributes
    protected int xLoc;
    protected int yLoc;

    protected int radius;
    protected int height;
    protected int width;
    protected int dx;
    protected int dy;
    
    //Constructor
    public Item(){
        xLoc = 0;
        yLoc = 0;
        height = 0;
        width = 0;
        dx = 0;
        dy = 0;
    }
    
    public Item(int x, int y, int r) {
        xLoc = x;
        yLoc = y;
        radius = r;
    }
    
    public Item(int x, int y, int h, int w, int xSpeed, int ySpeed) {
        this();
        xLoc = x;
        yLoc = y;
        height = h;
        width = w;
        dx = xSpeed;
        dy = ySpeed;
    }
    //Accessors
    public int getXLoc() {
        return xLoc;
    }
    public int getYLoc() {
        return yLoc;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
    
    
    //Mutators
    public void setXLoc(int x) {
        xLoc = x;
    }
    public void setYLoc(int y) {
        yLoc = y;
    }
    public void setHeight(int h) {
        height = h;
    }
    public void setWidth(int w) {
        width = w;
    }
    

    public void setDx(int dx) {
        this.dx = dx;
    }
    public void setDy(int dy){
        this.dy = dy;
    }
    
    
    //draw
    abstract public void draw(Graphics g);
}
