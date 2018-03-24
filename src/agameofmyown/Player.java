
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
    String name;
    
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
    
    public Player(int x, int y, int WIDTH, int HEIGHT, int xSpeed, int ySpeed,Game game,int upKey,int downKey, Color col, String name)
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
        this.name = name;
    }
    @Override
    public String getName()
    {
        return name;
    }
    
    //makes the player move
    @Override
    public void tick()
    {
        
        wrapEdges();
        checkMove();
    }
    
    //draw the player with his own position, size, and color!
    @Override
    public void render(Graphics g)
    {
        g.setColor(col);
        g.fillRect(bx, by, B_WIDTH, B_HEIGHT);
    }
    
    public void checkMove()
    {
        if(up) this.by -= Math.abs(bySpeed);
        
        if(down) this.by += Math.abs(bySpeed);
        
    }
    
    @Override
    public void wrapEdges()
    {
        if(by > game.H - 32*6)
        {
            this.down = false;
        }
        if(by < 16)
        {
            this.up = false;
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode()==upKey) this.up = true; 
        
        if(e.getKeyCode()==downKey) this.down = true;
        
    }
    
    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode()==upKey) this.up = false;
        
        if(e.getKeyCode()==downKey) this.down = false;
    }
}
