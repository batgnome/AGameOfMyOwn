
package agameofmyown;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

//player is an extension of entities so its got all the same stuff as
//entities, like position and speed and size and all nice stuff
public class Player extends Entities
{
    //special variables to check if an arrow is pressed
    //they just hold the answer to if its checked, they
    // don't actually check anything
    private boolean up,down,left,right;
    
    //makes a default key to use, in case you want 
    //to use WASD keys instead of arrows
    int upKey = KeyEvent.VK_UP;
    int downKey = KeyEvent.VK_DOWN;
    
    //The default color of the paddle
    Color col = Color.red;
    
    //player constructor, make a new player and set the size, position, speed and which game he's in
    public Player(int x, int y, int WIDTH, int HEIGHT, int xSpeed, int ySpeed,Game game)
    {
        this.bx = x;
        this.by = y;
        this.B_WIDTH = WIDTH;
        this.B_HEIGHT = HEIGHT;
        this.bxSpeed = xSpeed;
        this.bySpeed = ySpeed;
        this.game = game;
        
    }
    public Player(boolean right, Game game)
    {
        
        if(right==true)
        {
            this.bx = B_WIDTH+game.W;
            this.downKey = KeyEvent.VK_DOWN;
            this.downKey = KeyEvent.VK_UP;
        }
        else
        {
            this.bx = game.W;
            this.downKey = KeyEvent.VK_S;
            this.downKey = KeyEvent.VK_W;
        }
        this.B_WIDTH = 16;
        this.B_HEIGHT = 32*4;
        this.bxSpeed = 0;
        this.bySpeed = 3;
        this.game = game;
        this.col = Color.RED;
        
    }
    
    //same as above but with the ability to change which keys to press (WASD VS Arrow keys)
    public Player(int x, int y, int WIDTH, int HEIGHT, int xSpeed, int ySpeed,Game game,int upKey,int downKey, Color col)
    {
        this.bx = x;
        this.by = y;
        this.B_WIDTH = WIDTH;
        this.B_HEIGHT = HEIGHT;
        this.bxSpeed = xSpeed;
        this.bySpeed = ySpeed;
        this.game = game;
        this.downKey = downKey;
        this.upKey = upKey;
        this.col = col;
    }
    
    //makes the player move
    @Override
    public void tick()
    {
        
        wrapEdges();
        //check if you pressed the right keys to make him move
        checkMove();
    }
    
    //draw the player with his own position, size, and color!
    @Override
    public void render(Graphics g)
    {
        g.setColor(col);
        g.fillRect(bx, by, B_WIDTH, B_HEIGHT);
    }
    
    //checks if the keys are pressed, and if they are 
    //add the speed to the position
    public void checkMove()
    {
        if(up) this.by -= Math.abs(bySpeed);
        
        if(down) this.by += Math.abs(bySpeed);
        
        //if(left) bx -= Math.abs(bxSpeed);
        
        //if(right) bx += Math.abs(bxSpeed);
    }
    
    //make it so you cant go above the window
    @Override
    public void wrapEdges()
    {
        if(by > game.H - 32*6)
        {
            //this.by = game.H - 32*6 - Math.abs(bySpeed);
            this.down = false;
        }
        if(by < 16)
        {
            //this.by = 16+Math.abs(bySpeed);
            this.up = false;
        }
    }
    
    //this one actually checks the keyboard and passes along
    //what key got pressed to the checkmove function
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==upKey) this.up = true; 
        
        if(e.getKeyCode()==downKey) this.down = true;
        
        //if(e.getKeyCode()==KeyEvent.VK_LEFT) left = true;
        
        //if(e.getKeyCode()==KeyEvent.VK_RIGHT) right = true;
        
    }
    
    //makes sure you don't keep moving after you released the key
    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode()==upKey) this.up = false;
        
        if(e.getKeyCode()==downKey) this.down = false;
        
        //if(e.getKeyCode()==KeyEvent.VK_LEFT) left = false;
        
        //if(e.getKeyCode()==KeyEvent.VK_RIGHT) right = false;
    }
    //not ready yet
    void score()
    {
        
    }
}
