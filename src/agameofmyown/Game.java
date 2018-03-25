package agameofmyown;

import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Dimension;
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
    final int SIZE = 22
            ;
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
        player  = new Player(8, H / 2 - 32 * 4, 16, 32 * 4, 7, 7, this, KeyEvent.VK_W, KeyEvent.VK_S, Color.red,"player 1");
        player2 = new Player(W-64+24, H / 2 - 32 * 4, 16,32 *4, 7, 7, this, KeyEvent.VK_UP,KeyEvent.VK_DOWN,Color.BLUE,"player 2");
        
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
        
       
            for(int j = 0; j < H; j++)
            {
                if(j%4==0)
                g.fillRect(W/2-8, j*8, 8,16);
            }
            
        Font font = new Font(Font.SANS_SERIF,Font.BOLD,32);
        g.setFont(font);
        g.setColor(Color.yellow);
        String score = "Score";
        g.drawString(score,W/2-(score.length()*10),32);
        g.setColor(Color.red);
        g.drawString(this.score(1), 128,32);
        g.setColor(Color.blue);
        g.drawString(this.score(2), this.W-128,32);
        g.setColor(Color.blue);
        g.drawRect(player.bx-1, player.by, player.B_WIDTH, player.B_HEIGHT/8);
        g.drawRect(player.bx-1, player.by+player.B_HEIGHT/8, player.B_WIDTH, player.B_HEIGHT/8);
        
        

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
            ////System.out.println("ball: "+ball.bx);
            //System.out.println("player2: "+player2.bx);
            

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
        int bh = b.by-16;
        int split = bh/8;
        if ((e.bx + e.B_WIDTH >= b.bx)
                && (e.bx <= b.bx + b.B_WIDTH)
                && (e.by >= b.by - 16)
                && e.by <= b.by + b.B_HEIGHT)
        {
            e.bx += -5 * e.bxSpeed;
            e.bxSpeed *= -1;
            if((e.by >=bh)&&(e.by <bh+split))
            {
                System.out.println("1/8");
            }
            if((e.by >=bh+split)&&(e.by < bh+2*split))
            {
                System.out.println("2/8");
            }
            if((e.by >=bh+2*split)&&(e.by <bh+3*split))
            {
                System.out.println("3/8");
            }
            if((e.by >=bh+3*split)&&(e.by <bh+4*split))
            {
                System.out.println("4/8");
            }
            if((e.by >=bh+5*split)&&(e.by <bh+6*split))
            {
                System.out.println("5/8");
            }
            if((e.by >=bh+6*split)&&(e.by <bh+7*split))
            {
                System.out.println("6/8");
            }
            if((e.by >=bh+7*split)&&(e.by <bh+8*split))
            {
                System.out.println("8/8");
            }
        }
        
        
    }

    String score(int i)
    {
        
        if((ball.bx < -32)&&(i == 1))
        {
            p1+=1;
            reset();
            
        }else
        if(ball.bx > W+32) 
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
        Random r = new Random();
        double angle = r.nextInt((int) (2*Math.PI));
        ball.bxSpeed = (int)(5*Math.cos(angle));
        ball.bySpeed = (int)(5*Math.sin(angle));
    }

}
