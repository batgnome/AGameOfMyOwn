
package agameofmyown;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class RandomButtonsAndEffects extends JButton implements KeyListener, MouseListener
{
    //variables for the space key and escape key
    boolean spacePressed = false;
    boolean escapePressed = false;
    
    
    
    //not ready
    public void keepScore()
    {
        
    }
    
    @Override
    public void keyTyped(KeyEvent e)
    {
        
    }
    
    //checks to see if space or escape is pressed
    @Override
    public void keyPressed(KeyEvent e)
    {
         
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
             spacePressed = true;
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {
            escapePressed = true;
        }
    }

    //make sure space and escape stops after you release it
    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode()==KeyEvent.VK_SPACE)
             spacePressed = false;
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
