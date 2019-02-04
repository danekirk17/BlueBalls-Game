

package blueClasses;//change based on program

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author fased1690
 */
public class Player extends Item {
    int r, green, b;
    public Player(int x, int y, int h, int w, int dx, int dy) {
        super(x, y, h, w, dx, dy); 
    }
    public void setColor(int r, int g, int b){
        this.r = r;
        this.green = g;
        this.b = b;
    }
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(r, green, b));
        g2d.fillRect(xLoc, yLoc, width, height);
    }
    public void move(){
        super.yLoc += dy;
        super.xLoc += dx;
        
    }
    
}
