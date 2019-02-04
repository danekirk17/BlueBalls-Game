package blueClasses;//change based on program
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author dakir3750
 */
public class Power1 extends Item {
    int startX;
    int startY;
    public Power1(int x, int y, int r) {
        super(x, y, r);
        startX  = x;
        startY = y;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }
    
    

    /**
     *
     * @param g
     */
    
    @Override
    public void draw(Graphics g) {
        Graphics2D g1 = (Graphics2D) g;
        // draw the refractal onto the JPanel
        drawFractal(xLoc, yLoc, radius, g1);
    }
    //need to fix this doesnt look like its working
    public void effect(Player p) {
        p.setHeight((int)p.getHeight() /(2));
        p.setWidth((int)p.getWidth() /(2));
    }
    
    public void drawFractal(int xLoc, int yLoc, int radius, Graphics2D g) {
        if (radius > 0) { // only if r > 0
            drawFractal(xLoc, yLoc - (radius/2), radius/2, g);//top middle
            drawFractal(xLoc, yLoc + (radius/2), radius/2, g);//bottom middle
            drawFractal(xLoc + (radius/2), yLoc, radius/2, g);//right middle
            drawFractal(xLoc- (radius/2), yLoc, radius/2, g);//left middle
            
            drawFractal(xLoc - radius, yLoc - radius, radius / 2, g); // draw to top left corner, half size
            drawFractal(xLoc - radius, yLoc + radius, radius / 2, g); // draw the bottom left corner, half size
            drawFractal(xLoc + radius, yLoc - radius, radius / 2, g); // draw the top right corner, half size
            drawFractal(xLoc + radius, yLoc + radius, radius / 2, g); // draw the bottom right corner, half size
            
            animate(xLoc, yLoc, radius, g); //draw the square
        }
    }
    
    float red = 1f, green = 0f, blue = 1f;
    float dr = 0.001f;
    float dg = 0.001f;
    float db = 0.001f;

    public void animate(int x, int y, int r, Graphics2D g1) {
        if (red >= 1f) {//checks to see if the red is at the max
            dr = -0.00001f;//sets red value to a negative number
        } else if (red <= 0f) {//checks to see if the red is at the min 
            dr = 0.00001f;//sets red value to a positive number
        }
        if (blue >= 1f) {//checks to see if the blue is at the max
            db = -0.00001f;//sets blue value to a negative number
        } else if (blue <= 0f) {//checks to see if the blue is at the minx
            db = 0.00001f;//sets blue value to a positive number
        }
        if (green >= 1f) {//checks to see if the green is at the max
            dg = -0.00001f;//sets green value to a negative number
        } else if (green <= 0f) {//checks to see if the green is at the min
            dg = 0.00001f;//sets green value to a positive number
        }

        //decreases/increases each colour
        red += dr;
        blue += db;
        green += dg;

        red = ((float) Math.round(red * 1000000)) / 1000000;//rounds the value
        green = ((float) Math.round(green * 1000000)) / 1000000;//rounds the value
        blue = ((float) Math.round(blue * 1000000)) / 1000000;//rounds the value

        g1.setColor(new Color(red, green, blue));//sets colour
        g1.drawRect(x-r, y-r, 2 * r, 2 * r);//draws the square
    }
}
