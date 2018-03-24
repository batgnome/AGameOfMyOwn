package agameofmyown;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//abstract so I can make a template that ball and player can use
public abstract class Entities implements KeyListener
{
    //position, speed, and size variables
    int bx;
    int by;
    int bxSpeed;
    int bySpeed;
    int B_WIDTH;
    int B_HEIGHT;
    //need a game object so I can access functions in game
    Game game;
    
    //this function is meant to be overridden
    public void tick()
    {
    }
    //this function default draws a rectangle with the default positions and sizes
    public void render(Graphics g)
    {
       g.fillRect(bx, by, B_WIDTH, B_HEIGHT);
    }
    //this functions allows an entity to bounce against the edges of the game window
    public void bounceEdges()
    {
        if(bx <= 0)             bxSpeed = -bxSpeed;
           
        if(bx >= game.W - 48)   bxSpeed = -bxSpeed;
           
        if(by <= 0)             bySpeed = -bySpeed;
           
        if(by >= game.H - 80)   bySpeed = -bySpeed;
    }
    //this function allows an entity to wrap around the edges of a game window
    public void wrapEdges()
    {
        if(bx > game.W) bx = 0;
        
        if(bx < 0)      bx = game.W;
        
        if(by > game.H) by = 0;
        
        if(by < 0)      by = game.H;
    }
    
    
    
    
    //getters and setters
    public int getBx()
    {
        return bx;
    }
    
    public void setBx(int bx)
    {
        this.bx = bx;
    }
    
    public int getBy()
    {
        return by;
    }
    
    public void setBy(int by)
    {
        this.by = by;
    }

    public int getBxSpeed()
    {
        return bxSpeed;
    }

    public void setBxSpeed(int bxSpeed)
    {
        this.bxSpeed = bxSpeed;
    }

    public int getBySpeed()
    {
        return bySpeed;
    }

    public void setBySpeed(int bySpeed)
    {
        this.bySpeed = bySpeed;
    }

    public int getB_WIDTH()
    {
        return B_WIDTH;
    }

    public void setB_WIDTH(int B_WIDTH)
    {
        this.B_WIDTH = B_WIDTH;
    }

    public int getB_HEIGHT()
    {
        return B_HEIGHT;
    }

    public void setB_HEIGHT(int B_HEIGHT)
    {
        this.B_HEIGHT = B_HEIGHT;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
         }

    @Override
    public void keyPressed(KeyEvent e)
    {
       }

    @Override
    public void keyReleased(KeyEvent e)
    {
       }
}

