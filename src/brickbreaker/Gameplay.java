
package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import jaco.mp3.player.MP3Player;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;


public class Gameplay extends JPanel implements KeyListener, ActionListener, MouseMotionListener{
    private boolean play ;
    private int score = 0;
    
    private int totalBricks = 21;
    
    private Timer timer;
    private int delay = 1;
    
    private int playerX = 310;
    
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -2;
    private int ballYdir = -2;
    private final String backgroundMusic = "src/brickbreaker/music/RelaxMusic.mp3";
    private final String winMusic = "src/brickbreaker/music/fireworks.mp3";
    private final MP3Player mp3player = new MP3Player(new File(backgroundMusic));
    private final MP3Player winPlayer = new MP3Player(new File(winMusic));
    private int xs[]={0, 3,9,6, 9, 3, 0, -3,-9,-6,-9,-3}, xds[]=new int[12];
    private int ys[]={10,5,5,0,-5,-5,-10,-5,-5, 0, 5, 5}, yds[]=new int[12];
    private int xm[]=new int[12], xdm[]=new int[12];
    private int ym[]=new int[12], ydm[]=new int[12];
    private int xl[]=new int[12];
    private int yl[]=new int[12];
    private MapGenerator mp;
    
    public Gameplay(){
        addKeyListener(this);
        addMouseMotionListener(this);
        mp = new MapGenerator(3,7);
        mp3player.play();               //For music
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay,this);
        timer.start();
    }
    
    public void paint(Graphics g){
        //background
        g.setColor(Color.BLACK);
        g.fillRect(1,1,692,592);
        
        
        //drawing map
        mp.draw((Graphics2D)g);
        //borders
        g.setColor(Color.YELLOW);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);
        
        //scores
        g.setColor(Color.PINK);
        g.setFont(new Font("serif", Font.BOLD,25));
        g.drawString(""+score, 590,30);
        
        
        //paddle
        g.setColor(Color.BLUE);
        g.fillRect(playerX,550,100,8);
        
        //ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX,ballposY ,20,20);
        
        
        
        if(totalBricks <= 0){
            play = false;
            mp3player.pause();
            ballXdir = 0;
            ballYdir = 0;
            winPlayer.play();
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("You Won", 190,300);
            
            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter to Restart", 230,350);
            
            //Animation
            
            for(int i=0;i<12;i++){
            xl[i]=xs[i]*3+350; yl[i]=ys[i]*3+200;
            xm[i]=xs[i]*2+250; ym[i]=ys[i]*2+100;
            xdm[i]=xm[i]+450; ydm[i]=ym[i];
            xds[i]=xs[i]+550; yds[i]=ys[i]+50;
            xs[i]+=150; ys[i]+=50;
            }
            g.setColor(Color.ORANGE);
            g.fillPolygon(xs,ys,12);
            g.fillPolygon(xds,yds,12);
            g.setColor(Color.PINK);
            g.fillPolygon(xm,ym,12);
            g.fillPolygon(xdm,ydm,12);
            g.setColor(Color.RED);
            g.fillPolygon(xl,yl,12);

        
        }
        
        if(ballposY > 570){
            mp3player.pause();
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("Game Over, Score is : "+score, 190,300);
            
            g.setFont(new Font("serif", Font.BOLD,20));
            g.drawString("Press Enter to Restart", 230,350);
        }
        
        g.dispose();
    }
    //Methods of KeyListener
    @Override
    public void keyTyped(KeyEvent e) {   }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerX >=600)
                playerX = 600;
            else
                moveRight();
        }
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerX < 10)
                playerX = 10;
            else
                moveLeft();
        }
        if((e.getKeyCode() == KeyEvent.VK_ENTER) && !play){
             play = true;
             ballposX = 120;
             ballposY = 350;
             ballXdir = -2;
             ballYdir = -2;
             playerX = 310;
             score = 0;
             totalBricks = 21;
             mp = new MapGenerator(3,7);    //generate bricks
             mp3player.play();              //play music
             repaint();
        }
    }
    
    public void moveRight(){
        play= true;
        playerX+=20;
    }
    public void moveLeft(){
        play= true;
        playerX-=20;
    }
    @Override
    public void keyReleased(KeyEvent e) {   }

    //Methods of ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                //easy path change: ballYdir = -ballYdir;
                //better path change: my change
                if(ballposX-playerX<=15){
                    ballXdir = -3;
                    ballYdir = -1;
                }
                else if(ballposX-playerX<=30){
                    ballXdir = -2;
                    ballYdir = -1;    
                }
                else if(ballposX-playerX>=85){
                    ballXdir = 3;
                    ballYdir = -1;
                }
                else if(ballposX-playerX>=70){
                    ballXdir = 2;
                    ballYdir = -1;
                }
                else{
                    //no change in ballXdir
                    ballYdir = -2;
                }
            }
            
            A: for(int i=0; i<mp.map.length; i++){
                for(int j=0; j<mp.map[0].length; j++){
                    if(mp.map[i][j] > 0){
                        int brickX = j* mp.brickWidth + 80;
                        int brickY = i* mp.brickHeight + 50;
                        int brickWidth = mp.brickWidth;
                        int brickHeight = mp.brickHeight;
                        
                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle( ballposX, ballposY, 20 ,20);
                        Rectangle brickRect = rect;
                        if(ballRect.intersects(brickRect)){
                            mp.setBrickValue(0,i,j);
                            totalBricks--;
                            score+=5;
                            if(ballposX + 20-Math.abs(ballXdir) <=brickRect.x || ballposX + Math.abs(ballXdir) >= brickRect.x + brickRect.width){
                                ballXdir = -ballXdir;
                            }
                            else{
                                 ballYdir = -ballYdir;
                            }
                            break A;
                        }
                    }
                }
            }
            ballposX += ballXdir;
            ballposY += ballYdir;
            if(ballposX < 0){
                ballXdir = -ballXdir;
            }
            if(ballposY < 0){
                ballYdir = -ballYdir;
            }
            if(ballposX > 670){
                ballXdir = -ballXdir;
            }
        }
        repaint();
    }

    
    //Methods of MouseMotionListener
    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {
       playerX=e.getX()-50;
    }


}
