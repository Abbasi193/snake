package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

    private int[] snakeX = new int[500];
    private int[] snakeY = new int[500];
    private int score;
    private String path;
    private static int highscore = 0;
    private boolean running = true;
    private boolean up = false;
    private boolean down = false;
    private boolean right = false;
    private boolean left = false;
    private ImageIcon head;
    private ImageIcon food;
    private ImageIcon body;
    private Random rand = new Random();
    private int posX = (1 + rand.nextInt(30)) * 25;
    private int posY = (1 + rand.nextInt(21)) * 25;
    private Timer timer;
    private int snakeLength = 3;        //starting snake length
    private int moves = 0;        //count moves because we want to display our snake at the start using if statement

    public Gameplay() {

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        
        if (moves == 0) {
            snakeX[0] = 75;
            snakeX[1] = 50;
            snakeX[2] = snakeY[0] = snakeY[1] = snakeY[2] = 25;
        }

        g.setColor(Color.decode("#4caf50"));
        g.fillRect(25, 25, 750, 525);

        //drawing the mouths of the snake with starting at right face
        path="assets/rightmouth.png";
        head= new ImageIcon(path);
        head.paintIcon(this, g, snakeX[0], snakeY[0]);
        
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 800, 25);
        g.setColor(Color.white);
        g.setFont(new Font("calibri", Font.PLAIN, 14));
        g.drawString("Score: " + score, 710, 18);

        for (int i = 0; i < snakeLength; i++) {
            if (i == 0) {        
                if(right)
                    path="assets/rightmouth.png";
                if (left) 
                    path="assets/leftmouth.png";
                if (up) 
                    path="assets/upmouth.png";
                if (down) 
                    path="assets/downmouth.png";
                head = new ImageIcon(path);
                head.paintIcon(this, g, snakeX[i], snakeY[i]);
            }
            if (i != 0) {
                body = new ImageIcon("assets/snakeimage.png");
                body.paintIcon(this, g, snakeX[i], snakeY[i]);
            }
        }
        //spawning enemy and interaction with enemy        
        if (snakeX[0] == posX && snakeY[0] == posY) {
            score += 10;
            snakeLength+=1;
            do {
                posX = 25 * (1 + rand.nextInt(30));
                posY = 25 * (1 + rand.nextInt(21));
            } while (check());
        }
        food = new ImageIcon("assets/enemy.png");
        food.paintIcon(this, g, posX, posY);

        //checking if we hit ourselves
        for (int i = 1; i < snakeLength; i++) {
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                running = false;
                right = false;
                left = false;
                up = false;
                down = false;
                if (score > highscore) {
                    highscore = score;
                }
                g.setColor(Color.white);
                g.setFont(new Font("calibri", Font.BOLD, 30));
                g.drawString("GAME OVER", 320, 250);
                g.setFont(new Font("calibri", Font.ITALIC, 20));
                g.drawString("Press space to restart", 320, 300);
                g.setFont(new Font("calibri", Font.ITALIC, 25));
                g.drawString("Score is: " + score, 320, 350);
                g.setFont(new Font("calibri", Font.ITALIC, 25));
                g.drawString("Highscore is: " + highscore, 320, 375);
            }
        }
        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public boolean check() {
        for (int i = 0; i < snakeLength; i++) {
            if (snakeX[i] == posX && snakeY[i] == posY) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!running) {         //if isnt running then only valid move is space
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {      //space key interaction
                moves = 0;
                score = 0;
                snakeLength = 3;
                running = true;
                repaint();
            }
        }
        if (running) {  //valid only if game running is true
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {          //interactions for left right etc                
                if (!left) {
                    right = true;
                    up = down = false;
                    moves++;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                
                if (!right&&!(moves==0)) {
                    left = true;
                    up = down = false;
                    moves++;
                }
                
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                if (!down) {
                    up = true;
                    right = left = false;
                    moves++;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                if (!up) {
                    down = true;
                    right = left = false;
                    moves++;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        timer.start();
        if (right) {
            for (int r = snakeLength - 1; r >= 0; r--) {
                if (r == 0) {
                    snakeX[r] = snakeX[r] + 25;
                } 
                else {
                    snakeX[r] = snakeX[r - 1];
                    snakeY[r] = snakeY[r - 1];
                }

                if (snakeX[r] > 750) {
                    snakeX[r] = 25;
                }
            }
        }

        if (left) {
            for (int r = snakeLength - 1; r >= 0; r--) {
                if (r == 0) {
                    snakeX[r] = snakeX[r] - 25;
                }
                else {
                    snakeX[r] = snakeX[r - 1];
                    snakeY[r] = snakeY[r - 1];
                }
                if (snakeX[r] < 25) {
                    snakeX[r] = 750;
                }
            }
        }

        if (up) {
            for (int r = snakeLength - 1; r >= 0; r--) {

                if (r == 0) {
                    snakeY[r] = snakeY[r] - 25;
                } 
                else {
                    snakeX[r] = snakeX[r - 1];
                    snakeY[r] = snakeY[r - 1];
                }

                if (snakeY[r] < 25) {
                    snakeY[r] = 525;
                }
            }
        }
        
        if (down) {
            for (int r = snakeLength - 1; r >= 0; r--) {

                if (r == 0) {
                    snakeY[r] = snakeY[r] + 25;
                } else {

                    snakeX[r] = snakeX[r - 1];
                    snakeY[r] = snakeY[r - 1];
                }
                if (snakeY[r] > 525) {
                    snakeY[r] = 25;
                }
            }
        }
        repaint();
    }
}