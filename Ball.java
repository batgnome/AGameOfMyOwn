
package agameofmyown;

import java.awt.Graphics;
import java.util.Random;

//ball is an extension of entities so its got all the same stuff as
//entities, like position and speed and size and all nice stuff
public class Ball extends Entities
{
    
    //constructor for a ball, instantiates a new ball. 
    //A ball needs to know whats game its in to
    //access the game's variables
    public Ball(Game game)
    {
        
        //making a ball and a set position for any ball made with this constructor
        this.game = game;
        this.bx = game.W/2-32;
        this.by = game.H/2-128;
        this.bxSpeed = 6;
        this.bySpeed = 3;
        this.B_WIDTH = 32;
        this.B_HEIGHT = 32; 
        
    }
    
    //this is a real hands on ball making constructor
    //if you want to manually change the positions and speed and size of the game
    //you still need to know which game you are in though
    public Ball(int x, int y, int xSpeed, int ySpeed, int WIDTH, int HEIGHT, Game game)
    {
        this.bx = x;
        this.by = y;
        this.bxSpeed = xSpeed;
        this.bySpeed = ySpeed;
        this.B_WIDTH = WIDTH;
        this.B_HEIGHT = HEIGHT;
        this.game = game;
    }
    
    //this increases the position of the ball by the speed,
    //in the game class tick is run over and over, so the ball
    //keeps moving
    @Override
    public void tick()
    {
        bx += bxSpeed;
        by += bySpeed;
        bounceEdges();
    }
    
    @Override
    public void render(Graphics g)
    {
        g.fillOval(bx, by, B_WIDTH, B_HEIGHT);
    }
    
    //the ball still bounces on the top and bottom
    //but goes through the left and right
    @Override
    public void bounceEdges()
    {
        if(by > game.H-64) bySpeed = -bySpeed;
        if(by < 0) bySpeed = -bySpeed;
        if(bx < 0) 
        {
            //game.player2.score();
            reset();
            
        }
        if(bx > game.W) 
        {
            //game.player.score();
            reset();
        }
    }
    void reset()
    {
        this.bx = game.W/2-32;
        this.by = game.H/2-32;
        this.bxSpeed *= -1;
    }

}
