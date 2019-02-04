package blueClasses;
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
public class Power3 extends Item {
//best raduis 10
    int startX;
    int startY;
    public Power3(int x, int y, int r) {
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
        drawFractal(xLoc,yLoc,radius,g1);
    }
    
    public void effect(Ball b) {
        b.setWidth((int) ((int) b.getWidth() * 0.5));
        b.setHeight((int) ((int) b.getHeight() * 0.5));
    }
    
    public void drawFractal(int x, int y, int r, Graphics2D g1) {

        if (r > 0) { // only if r > 0
            drawFractal(x, y - r, r/2, g1);//top middle
            drawFractal(x, y + r, r/2, g1);//bottom middle
            drawFractal(x + r, y, r/2, g1);//right middle
            drawFractal(x - r, y, r/2, g1);//left middle
            animate(x,y,r,g1);
        }
    }
    
    float red = 1f, green = 0f, blue = 1f;
    float dr = 0.001f;
    float dg = 0.001f;
    float db = 0.001f;
    
    public void animate(int x, int y, int r, Graphics2D g1) {
        if (red >= 1f) {//checks to see if the red is at the max
            dr = -0.0001f;//sets red value to a negative number
        } else if (red <= 0f) {//checks to see if the red is at the min 
            dr = 0.0001f;//sets red value to a positive number
        }
        if (blue >= 1f) {//checks to see if the blue is at the max
            db = -0.0001f;//sets blue value to a negative number
        } else if (blue <= 0f) {//checks to see if the blue is at the minx
            db = 0.0001f;//sets blue value to a positive number
        }
        if (green >= 1f) {//checks to see if the green is at the max
            dg = -0.0001f;//sets green value to a negative number
        } else if (green <= 0f) {//checks to see if the green is at the min
            dg = 0.0001f;//sets green value to a positive number
        }

        //decreases/increases each colour
        red += dr;
        blue += db;
        green += dg;

        red = ((float) Math.round(red * 10000)) / 10000;//rounds the value
        green = ((float) Math.round(green * 10000)) / 10000;//rounds the value
        blue = ((float) Math.round(blue * 10000)) / 10000;//rounds the value

        g1.setColor(new Color(red, green, blue));//sets colour
        g1.drawRoundRect(x - r, y - r, 2 * r, 2 * r,r,r);//draws the square
    }
    }
