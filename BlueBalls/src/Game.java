
import blueClasses.Ball;
import blueClasses.Player;
import blueClasses.Power1;
import blueClasses.Power2;
import blueClasses.Power3;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.io.*;
import sun.audio.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author fased1690
 */
public class Game extends JFrame {

    //when a new game is created
    public Game() {
        //initial start up 
        initUI();

    }

    private void initUI() {
        add(new GameVeiw()); //creates a new jpanel
        setName("Blue Balls");//sets the name of the jpanel
        setSize(1250, 750);//sets the dimensions to 1250 X 750 pixel
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    public static void main(String[] args) {

    }

}

class GameVeiw extends JPanel implements ActionListener, KeyListener {

    private ScoreBoard scoreBoard; //creates a score board varible
    int numBalls = 5; //this is for the max number of balls in play
    int numBallsInPlay = 3; //this is for the current number of balls in play
    Ball balls[] = new Ball[numBalls];//creates an array of balls
    Timer t = new Timer(5, this); //creates a timer where every time it ticks action listener will trigger
    int ballSize = 50; //the ball has a length of 50
    int playerSpeedx; //controls player speed in x
    int playerSpeedy; //controls player speed in the y
    int ballSpeed; //controls default ball speed
    Player player; //player
    Power1 power1; //power 1 player size
    Power2 power2; //power 2 ball speed
    Power3 power3; //power 3 ball size
    int powerX; //controls where the power randomly generates in the x
    int powerY; //controls random generation in the y
    boolean addBall4 = true; //boolean that allows a 4th ball to be added
    boolean addBall5 = true; //controls a 5th ball if allowed to be added
    long gameTime = 0; //sets the game time to 0
    long startTime; //stores the time the game started
    long currentTime; //stores the current time to calculate game time
    long powerTimeStart; //checks when  a power is captured
    long powerCurrentTime; //gets the time so a power only lasts 5 seconds
    long powerTime; //this calculates how long the power has been active on the player
    boolean up = false, down = false, left = false, right = false;// booleans that controls the direction the player is moving
    boolean power1Active = false, power2Active = false, power3Active = false; //controls if a power is drawn or not
    boolean activePower = false; //controls if any powers are drawn or not
    boolean power1On = false, power2On = false, power3On = false; //controls if a power effect is active or not
    private JLabel lblScore; //a label that draws the score
    boolean play = true;

    public GameVeiw() {
        setBackground(Color.BLACK);// sets the game background to black
        t.start(); // start timer
        addKeyListener(this); //adds a key listener
        this.setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        lblScore = new JLabel("", JLabel.CENTER); //creats a label
        lblScore.setBackground(new java.awt.Color(0, 0, 0)); //set label color to black
        lblScore.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblScore.setForeground(new java.awt.Color(106, 185, 0)); //set the forground color

        this.add(lblScore); //add the label to jframe

        ballSpeed = 3; //set the default ball speed to 3

        balls[0] = new Ball(10, 10, ballSize, ballSize, ballSpeed, ballSpeed); //create the first 3 balls
        balls[1] = new Ball(1000, 300, ballSize, ballSize, -ballSpeed, ballSpeed);
        balls[2] = new Ball(500, 600, ballSize, ballSize, ballSpeed, -ballSpeed);

        player = new Player(625, 375, 75, 40, 0, 0); //create a player
        player.setColor(255, 255, 255); //set the player color
        playerSpeedx = 2; //set the default player speed to 2
        playerSpeedy = 2;
        startTime = System.currentTimeMillis(); //get the time of when the game started
        music(); //start playing music
    }
    AudioPlayer MGP = AudioPlayer.player; //create an audio player
    AudioStream BGM; //create an audio streamer
    AudioPlayer MGP2 = AudioPlayer.player; //create an audio player
    AudioStream BGM2; //create an audio streamer

    public void music() {

        try {
            InputStream test = new FileInputStream("src\\music.wav"); //create a filepath to the music file
            BGM = new AudioStream(test);  //create a new streamer
            AudioPlayer.player.start(BGM); //play the music

        } catch (FileNotFoundException e) { //error message printing
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        try {
            InputStream test = new FileInputStream("src\\ha.wav"); //create a filepath to the music file
            BGM2 = new AudioStream(test);  //create a new streamer
            

        } catch (FileNotFoundException e) { //error message printing
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }

    }
    private Graphics2D g2; //graphics 2d

    @Override
    public void actionPerformed(ActionEvent e) { //when the timer ticks this runs
        currentTime = System.currentTimeMillis(); //gets the current time
        gameTime = (currentTime - startTime) / 1000; //calcuates the total game time
        lblScore.setText("" + gameTime); //updates the timer on the screen
        //if the game time is 5, 20, 35, 50, 65, 80, or 95 seconds a power up will spawn
        if (gameTime == 5 || gameTime == 20 || gameTime == 35 || gameTime == 50 || gameTime == 65 || gameTime == 80 || gameTime == 95) {
            if (activePower == false) { //if a power is allowed to spawn
                powerGen(); //generate a power up
            }
        }
        if (gameTime == 10 && addBall4 == true) { //if the game time is 10 add a new ball
            balls[3] = new Ball(30, 30, ballSize, ballSize, ballSpeed, -ballSpeed); //add the ball in the top left hand corner
            numBallsInPlay++; //increase the number of balls in the game by 1
            addBall4 = false; //make it so ball 4 cannot spawn more then once
        }
        if (gameTime == 30 && addBall5 == true) { //if the game time is 30 seconds add another ball
            balls[4] = new Ball(600, 350, ballSize, ballSize, ballSpeed, ballSpeed); //generate the 5th ball in the middle of the screen
            numBallsInPlay++; //increase the number of balls in play by 1
            addBall5 = false; //make is so the 5th ball cannot spawn again

        }
        //this controls that a power can only spawn and be on the screen for 5 seonds, after 5 seconds the power up disappears
        if (gameTime == 10 || gameTime == 25 || gameTime == 40 || gameTime == 55 || gameTime == 70 || gameTime == 85 || gameTime == 100) {
            activePower = false; //allows another power to spawn later because none are spawned anymore
            power1Active = false;//if any of the 3 powers havent been collected it will dispear
            power2Active = false;
            power3Active = false;

        }
        if (power1On || power2On || power3On) { //if one of the power ups were collected
            powerCurrentTime = System.currentTimeMillis(); //gets how long the power up is active for
            powerTime = (powerCurrentTime - powerTimeStart) / 1000;

            if (powerTime == 5) { //when the player has had the power up for 5 seconds
                if (power1On) { //reset the power up effect depending on the effect
                    player.setHeight(75); //this is for player size effect
                    player.setWidth(40);
                    power1On = false;
                } else if (power2On) {
                    for (int i = 0; i < numBallsInPlay; i++) { //reset the player ball speeds back to the default
                        if (balls[i].getDx() < 0) { //makes sure the ball is still traveling  inthe same direction
                            balls[i].setDx(-ballSpeed);
                        } else {
                            balls[i].setDx(ballSpeed);
                        }
                        if (balls[i].getDy() < 0) {
                            balls[i].setDy(-ballSpeed);
                        } else {
                            balls[i].setDy(ballSpeed);
                        }
                    }
                    power2On = false; //turns the effect of power 2 to off
                } else {
                    for (int i = 0; i < numBallsInPlay; i++) { //resets the ball sizes
                        balls[i].setHeight(ballSize);
                        balls[i].setWidth(ballSize);
                    }
                    power3On = false;
                }
            }
        }

        playerMove(); //controls player movement
        checkBallBounce(); //controls when a ball bounces off a wall
        checkBallHit(); //check when a ball hits a player

        if (power1Active == true) { //if a power has been generated
            power1Hit(); //checks if the player collides with the power

        } else if (power2Active == true) {
            power2Hit();

        } else if (power3Active == true) {
            power3Hit();

        }
        for (int i = 0; i < numBallsInPlay; i++) { //this moves the balls
            balls[i].move();
        }
        player.move();//moves the player

        repaint(); //refreshes the screen
    }

    public void powerGen() { //this genereates a random power when it is called 
        int powerNum = (int) (Math.random() * 3 + 0); //generates a random number 1 or 2 or 3
        powerX = (int) (Math.random() * 1100 + 50); //generates a random x and y coordinate for the pwoer
        powerY = (int) (Math.random() * 650 + 50);
        if (powerNum == 0) { //each number corrisponds with a power up
            power1 = new Power1(powerX, powerY, 10); //creates anew power at these coordinates
            power1Active = true;  //sets the power being drawn to active
        } else if (powerNum == 1) {
            power2 = new Power2(powerX, powerY, 14);
            power2Active = true;
        } else {
            power3 = new Power3(powerX, powerY, 10);
            power3Active = true;
        }
        activePower = true;
    }

    private void doDrawing(Graphics g) { //this draws everything on the screen
        Graphics2D g1 = (Graphics2D) g; //create graphics 2d
        g2 = g1; //store it in a global variable
        //helps with rendering and makes frames smootther
        g1.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g1.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g1.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g1.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g1.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g1.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g1.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        for (int i = 0; i < numBallsInPlay; i++) { //this draws all balls on the screen
            balls[i].draw(g1);
        }

        if (power1Active == true) { //drwas a power if it is active
            power1.draw(g1);
        }
        if (power2Active == true) {
            power2.draw(g1);
        }
        if (power3Active == true) {
            power3.draw(g1);
        } 
        player.draw(g1); 
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g); // prepare the panel for drawing

        doDrawing(g); // call the method to draw 

    }

    public void checkBallHit() { //check if a ball collides with a person

        for (int i = 0; i < numBallsInPlay; i++) { //loops through however many active balls there are
            //if a ball collides with a person
            if (balls[i].getYLoc() + balls[i].getHeight() - 3 > player.getYLoc() && balls[i].getYLoc() < player.getYLoc() + player.getHeight() //vertical
                    && balls[i].getXLoc() + balls[i].getWidth() - 3 > player.getXLoc() && balls[i].getXLoc() < player.getXLoc() + player.getWidth()) { //horizontal
                stopGame();//stop the game
            }
        }
    }

    public void stopGame() {//when the game stops
        t.stop(); //stop the timer
        JFrame frame = (JFrame) SwingUtilities.getRoot(this);//get the jframe
        AudioPlayer.player.stop(BGM); //stop the music
        AudioPlayer.player.start(BGM2); //play the music
        JOptionPane.showMessageDialog(null, "You have collided with a ball!"); //output this message
        scoreBoard = new ScoreBoard((int) gameTime); //create a new score board window
 
        scoreBoard.setVisible(true); //show the score board menu
        frame.setVisible(false); //close this menu
    }

    public void playerMove() {//moves the player
        if (up) {  //if the player is traveling up
            if (player.getYLoc() < 0) { //check if the player is at the top
                player.setDy(0); //if it is stop moving the player up
                up = false;

            } else {
                player.setDy(playerSpeedy * -1); //if it is not at the top move the player up
            }
        }
        if (down) { //this controls moving player down
            if ((player.getYLoc() + player.getHeight() + 20) > 740) {
                player.setDy(0);
                down = false;
            } else {
                player.setDy(playerSpeedy);
            }
        } 
        if (left) { //moving player left
            if (player.getXLoc() < 0) {
                player.setDx(0);
                left = false;
            } else {
                player.setDx(playerSpeedx * -1);
            }
        }
        if (right) { //moving player right
            if (player.getXLoc() + player.getWidth() + 6 > 1250) {
                player.setDx(0);
                right = false;
            } else {
                player.setDx(playerSpeedx);
            }

        }
    }

    public void checkBallBounce() { //check if a ball hits a wall
        for (int i = 0; i < numBallsInPlay; i++) { //loops through all the balls
            if (balls[i].getYLoc() < 0) { //checks if the ball is against one of the walls 
                balls[i].setDy(balls[i].getDy() * -1); //sets the direction to the other direction
            }
            if (balls[i].getYLoc() + balls[i].getWidth() > 735) {
                balls[i].setDy(balls[i].getDy() * -1);
            }
            if (balls[i].getXLoc() > 1240 - balls[i].getHeight()) {
                balls[i].setDx(balls[i].getDx() * -1);
            }
            if (balls[i].getXLoc() < 0) {
                balls[i].setDx(balls[i].getDx() * -1);
            }
        }
    }

    public void power1Hit() { //if power 1 is active it will check if the player collides with it
        if ((powerY + 19) > player.getYLoc() && (powerY - 19) < (player.getYLoc() + player.getHeight()) //controls virtical boundries
                && (powerX + 19) > player.getXLoc() && (powerX - 19) < player.getXLoc() + player.getWidth()) { //horizontal boundries
            power1.effect(player); //if the player collides with it set the power effect to active
            powerTimeStart = System.currentTimeMillis();//gets the current time so the power will only last 3 second
            power1On = true; // set the power effect to active
            power1Active = false; //stop drawing the power
        }

    }

    public void power2Hit() { //this is for the2nd power
        if ((powerY + 15) > player.getYLoc() && (powerY - 15) < (player.getYLoc() + player.getHeight())
                && (powerX + 15) > player.getXLoc() && (powerX - 15) < player.getXLoc() + player.getWidth()) {
            for (int i = 0; i < numBallsInPlay; i++) {
                power2.effect(balls[i]);
            }
            powerTimeStart = System.currentTimeMillis();
            power2On = true;
            power2Active = false;
        }

    }
 
    public void power3Hit() { //this is for the 3rd power
        if ((powerY + 17) > player.getYLoc() && (powerY - 17) < (player.getYLoc() + player.getHeight())
                && (powerX + 17) > player.getXLoc() && (powerX - 17) < player.getXLoc() + player.getWidth()) {
            for (int i = 0; i < numBallsInPlay; i++) {
                power3.effect(balls[i]);
            }
            powerTimeStart = System.currentTimeMillis();
            power3On = true;
            power3Active = false;
        }

    }

    public void keyPressed(KeyEvent e) { //if a key is pressed
        int code = e.getKeyCode(); //get the keycode
 
        if (code == KeyEvent.VK_UP) {//if the key is up
            up = true; //move the player up
        }
        if (code == KeyEvent.VK_DOWN) { //if the key is dwon 
            down = true;//move the player down
        }

        if (code == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (code == KeyEvent.VK_LEFT && player.getXLoc() > 0) {
            left = true;
        }

    }

    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    public void keyReleased(KeyEvent e) { //if the key is released
        int code = e.getKeyCode(); //find what key is released

        if (code == KeyEvent.VK_UP) { //if the key is up
            player.setDy(0); //stop moving the player up
            up = false; //set moving up to false
        }
        if (code == KeyEvent.VK_LEFT) { //this is for left
            player.setDx(0);
            left = false;
        }
        if (code == KeyEvent.VK_DOWN) { //down
            player.setDy(0);
            down = false;
        }
        if (code == KeyEvent.VK_RIGHT) { //right
            player.setDx(0);
            right = false;
        }
    }
}
