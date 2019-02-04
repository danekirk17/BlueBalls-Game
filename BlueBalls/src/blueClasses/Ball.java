package blueClasses;//change based on program
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/*
 * Dane K
 * December 13
 * Contains information specific to balls (speed etc).
 */

/**
 *
 * @author dakir3750
 */
public class Ball extends Item{
    //Attributes
    protected static int NUM_BALLS;
    
    //Constructor
    public Ball(int x, int y, int h, int w, int dx, int dy) {
        super(x, y, h, w, dx, dy);
        NUM_BALLS++;
    }
    
    /**
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(0,0,255));
        g2d.fillOval(xLoc, yLoc, width, height);
    }
    public void move(){
        super.xLoc += dx;
        super.yLoc += dy;
    }
    
}
