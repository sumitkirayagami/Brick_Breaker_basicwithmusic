
package brickbreaker;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
public class BrickBreaker extends JFrame {

    BrickBreaker(){
        
        // Transparent 16 x 16 pixel cursor image.
       BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
       // Create a new blank cursor.
       Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
       cursorImg, new Point(0, 0), "blank cursor");
        // Set the blank cursor to the JFrame.
       
        getContentPane().setCursor(blankCursor);
    }
    public static void main(String[] args) {
        BrickBreaker obj=new BrickBreaker();
        Gameplay gamePlay = new Gameplay();
        
        
        
        
        obj.setBounds(10,10,700,600);
        obj.setTitle("Brick Breaker");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
    }
    
}
