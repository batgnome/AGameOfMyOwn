package agameofmyown;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.*;

public class Game extends JPanel implements Runnable
{

    //Window size variables 
    JFrame frame = new JFrame();
    final int SIZE = 22;
    final int W = 32 * SIZE;
    final int H = 32 * SIZE;

    //starts a thread so the game can run all the 
    //processes without breaking
    Thread thread = new Thread(this, "");

    //this is for making buttons keeping score and passing keyboard inputs so my 
    //game class won't need to implement KeyListener
    RandomButtonsAndEffects sp = new RandomButtonsAndEffects(this);

    //create references for the objects in the game
    Player player2;
    Player player;
    Ball ball;
    int p1;
    int p2;
    

    //make a list of all the balls and players (Entities)
    ArrayList<Entities> ent;

    public static void main(String[] args)
    {
        //create a new game and start it
        Game game = new Game();
        game.init();
        game.start();
    }

    private synchronized void start()
    {
        thread.start();
    }

    //create all the necessary positions and speeds and frame sizes for all entities and windows
    public void init()
    {
        //instatiate the list of balls and players
        ent = new ArrayList<>();

        //instatiate the players and balls   //start the game
        player  = new Player(32, H / 2 - 32 * 4, 16, 32 * 4, 7, 7, this, KeyEvent.VK_W, KeyEvent.VK_S, Color.red,"player 1");
        player2 = new Player(W-100, H / 2 - 32 * 4, 16,32 *4, 7, 7, this, KeyEvent.VK_UP,KeyEvent.VK_DOWN,Color.BLUE,"player 2");
        
        ball = new Ball(this);

        //add these guys to the list
        ent.add(player);
        ent.add(player2);
        ent.add(ball);

        //make a window thingy
        frame.setSize(W, H);
        frame.setBackground(Color.black);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(player2);
        frame.addKeyListener(player);
        frame.addKeyListener(sp);

    }

    @Override
    public void paintComponent(Graphics g)
    {
        //draw all the players and balls
        for (Entities e : ent)
        {
            e.render(g);
        }
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,32);
        g.setFont(font);
        g.setColor(Color.red);
        g.drawString("Score",this.W/2-64,32);
        g.drawString(this.score(1), this.W/2-128,32);
        g.drawString(this.score(2), this.W/2+64,32);
        

    }
   
    @Override
    public void run()
    {
        while (true)
        {
            //I have this here so that it isn't to fast
            //I should probably change it at some point
            try
            {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }

            //create a new ball when space is pressed
            if (sp.spacePressed)
            {
                ent.add(new Ball(this));
                sp.spacePressed = false;
            }
            //go through the logic of all the balls and players and tick
            tick();
            //check if they hit each other
            checkCollision();
            
            frame.repaint();
            System.out.println("ball: "+ball.bx);
            System.out.println("player2: "+player2.bx);
            

        }
    }

    public void tick()
    {
        // do all the logic of the balls and players
        for (Entities e : ent)
        {
            e.tick();
        }
    }

    public void render(Graphics g)
    {
        // draw all the balls and players
        for (Entities e : ent)
        {
            e.render(g);
        }
    }

    public void checkCollision()
    {
        
        for (Entities e : ent)
        {
            checkAgainst(e);

        }
    }

    public void checkAgainst(Entities e)
    {
        for (Entities b : ent)
        {
            if((e!=b)&&(!e.equals(player))&&(!e.equals(player2)))
            checkPositionForCollision(e, b);
        }
    }

    public void checkPositionForCollision(Entities e, Entities b)
    {
        if((e.bx+e.B_WIDTH >= b.bx)&&
           (e.bx <= b.bx+b.B_WIDTH)&&
           (e.by >= b.by-16)&&
            e.by <= b.by+b.B_HEIGHT)
        {
            e.bx += -5*e.bxSpeed;
            e.bxSpeed *= -1;
            System.out.println("Collide! "+e.bx+" "+b.bx);
        }
        
        
    }

    String score(int i)
    {
        
        if((ball.bx < 0)&&(i == 1))
        {
            p1+=1;
            reset();
            
        }else
        if(ball.bx > W) 
        {
            p2+=1;
            reset();
        }
        if(i == 1)
        {
            return Integer.toString(p1);
        }
        else return Integer.toString(p2);
    }
    
    void reset()
    {
        ball.bx = this.W/2-32;
        ball.by = this.H/2-32;
        ball.bxSpeed *= -1;
    }

}
