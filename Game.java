
package agameofmyown;


import com.sun.glass.events.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
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
   final int W = 32*SIZE;
   final int H = 32*SIZE;
   
   //starts a thread so the game can run all the 
   //processes without breaking
   Thread thread = new Thread(this,"");
   
   //this is for making buttons keeping score and passing keyboard inputs so my 
   //game class won't need to implement KeyListener
   RandomButtonsAndEffects sp = new RandomButtonsAndEffects();
   
   //create references for the objects in the game
   Player player;
   Player player2;
   Ball ball;
   
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

       player = new Player(32,H/2-32*4,16,32*4,5,5,this,KeyEvent.VK_W, KeyEvent.VK_S,Color.white);
       player2 = new Player(W-64-16,H/2-32*4,16,32*4,5,5,this,KeyEvent.VK_UP, KeyEvent.VK_DOWN,Color.white);
       //player = new Player(true,this);
       //player2 = new Player(false,this);
       ball = new Ball(this);
       
       //add these guys to the list
       ent.add(ball);
       ent.add(player);
       ent.add(player2);
       
       //make a window thingy
       frame.setSize(W,H);
       frame.setBackground(Color.black);
       frame.setVisible(true);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.add(this);
       frame.addKeyListener(player);
       frame.addKeyListener(player2);
       frame.addKeyListener(sp);
       
   }
   
   @Override
   public void paintComponent(Graphics g)
   {
       //draw all the players and balls
       for(Entities e: ent)
       {
           e.render(g);
       }
       
   }
   
   @Override
   public void run()
   {
       while(true)
       {
           //I have this here so that it isn't to fast
           //I should probably change it at some point
           try
           {
               TimeUnit.MILLISECONDS.sleep(10);
           }catch (InterruptedException ex)
           {
               Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           //create a new ball when space is pressed
           if(sp.spacePressed)
           {
               ent.add(new Ball(this));
               sp.spacePressed = false;
           }
           //go through the logic of all the balls and players and tick
           tick();
           //check if they hit each other
           checkCollision();
           //draw it all
           frame.repaint();
            
       }
   }
   public void tick()
   {
          // do all the logic of the balls and players
          for(Entities e: ent)
          {
              e.tick();
          }
   }
   public void render(Graphics g)
   {
       // draw all the balls and players
       for(Entities e: ent)
       {
           e.render(g);
       }
   }
   public void checkCollision()
   {
       //make some rectangles to check eachother
       Rectangle r2;
       Rectangle r1;
       //loops through all the entities in the game
       for(Entities e: ent)
       {
           r1 = new Rectangle(e.bx,e.by,e.B_WIDTH,e.B_HEIGHT);
           
           for(Entities b: ent)
           {
               if(e != b)
               {
                   r2 = new Rectangle(b.bx,b.by,b.B_WIDTH,b.B_HEIGHT);
                   
                   if(!e.equals(player)&&!e.equals(player2))
                   {
                        if(r1.intersects(r2))
                        {
                            if(e.bxSpeed < 0)
                            {
                                 e.bx = e.bx + b.B_WIDTH + Math.abs(e.bxSpeed);
                                 e.bxSpeed *= -1;
                            }else 
                            if(e.bxSpeed > 0)
                            {
                                 e.bx = e.bx - b.B_WIDTH - Math.abs(e.bxSpeed);
                                 e.bxSpeed *= -1;
                            }

                        }
                   }
               }
           }
       }
   }
    //supposed to make a new ball when 
    //someone scores
    void reset()
    {
         
    }
    
}
