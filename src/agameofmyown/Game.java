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
    final int SIZE = 15;
    final int W = 32 * 17;
    final int H = 32 * SIZE;
    boolean isPaused;

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
    boolean running;
    private boolean hit = false;

    //make a list of all the balls and players (Entities)
    ArrayList<Entities> ent;

    synchronized void start()
    {
        thread.start();
        running = true;
    }

    synchronized void continueGame()
    {
        running = true;
        run();
    }

    synchronized void pause()
    {
        running = false;

    }

    //create all the necessary positions and speeds and frame sizes for all entities and windows
    void init()
    {

        ent = new ArrayList<>();
        ball = new Ball(this,10);
        createPlayers();

        

        //add these guys to the list
        ent.add(player);
        ent.add(player2);
        ent.add(ball);

        //make a window thingy
        createWindow();

    }

    void createPlayers()
    {
        player = new Player(8, H / 2 - 32 * 4, 16, 16 * 6, 7, 7, this, KeyEvent.VK_W, KeyEvent.VK_S, Color.red, "player 1");
        player2 = new Player(W - 64 + 24, H / 2 - 32 * 4, 16, 16 * 6, 4, 4, this, KeyEvent.VK_UP, KeyEvent.VK_DOWN, Color.BLUE, "player 2");
    }

    void createWindow()
    {
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
        for (Entities e : ent)
        {
            e.render(g);
        }

        drawCenterLine(g);
        drawScore(g);

    }

    void drawCenterLine(Graphics g)
    {
        for (int j = 0; j < H; j++)
        {
            if (j % 4 == 0)
            {
                g.fillRect(W / 2 - 8, j * 8, 8, 16);
            }
        }
    }

    void drawScore(Graphics g)
    {
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 32);
        g.setFont(font);
        g.setColor(Color.yellow);
        String score = "Score";
        g.drawString(score, W / 2 - (score.length() * 10), 32);
        g.setColor(Color.red);
        g.drawString(this.score(2), 128, 32);
        g.setColor(Color.blue);
        g.drawString(this.score(1), this.W - 128, 32);
    }

    @Override
    public void run()
    {
        while (running)
        {
            slowTime();
            pauseGame();
            tick();
            if(player2.by < ball.by)
            {
                player2.by+=player2.bySpeed;
            }
            if(player2.by > ball.by)
            {
                player2.by-=player2.bySpeed;
            }
            if (ball.bx > W / 2 - 32 && ball.bx < W / 2 + 32)
            {
                hit = false;
            }
            if (!hit)
            {
                checkCollision();
            }
            frame.repaint();

        }
    }

    void pauseGame()
    {

        if (sp.spacePressed)
        {
            if (!isPaused)
            {
                pause();
            } else
            {
                continueGame();
            }
        }
    }

    void slowTime()
    {
        try
        {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException ex)
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void tick()
    {
        for (Entities e : ent)
        {
            e.tick();
        }
    }

    public void render(Graphics g)
    {
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
            if ((e != b) && (!e.equals(player)) && (!e.equals(player2)))
            {
                checkPositionForCollision(e, b);
            }
        }
    }

    public void checkPositionForCollision(Entities e, Entities b)
    {
        int bh = b.B_HEIGHT;

        int split = bh / 8;
        if (checkPlayerCol(e, b))
        {
            hit = true;

            if ((e.by + 32 >= b.by) && (e.by < b.by + split))
            {
                if (Math.cos(Math.toRadians(e.angle)) > 0)
                {
                    e.angle = -135;
                } else
                {
                    e.angle = -45;
                }

            }

            if ((e.by >= b.by + split) && (e.by < b.by + 2 * split))
            {
                if (Math.cos(Math.toRadians(e.angle)) > 0)
                {
                    e.angle = -150;
                } else
                {
                    e.angle = -30;
                }

            }

            if ((e.by >= b.by + 2 * split) && (e.by < b.by + 3 * split))
            {
                if (Math.cos(Math.toRadians(e.angle)) > 0)
                {
                    e.angle = -165;
                } else
                {
                    e.angle = -15;
                }

            }

            if ((e.by >= b.by + 3 * split) && (e.by < b.by + 4 * split))
            {
                if (Math.cos(Math.toRadians(e.angle)) > 0)
                {
                    e.angle = 175;
                } else
                {
                    e.angle = -5;
                }

            }
            if ((e.by >= b.by + 4 * split) && (e.by < b.by + 5 * split))
            {
                if (Math.cos(Math.toRadians(e.angle)) > 0)
                {
                    e.angle = 185;
                } else
                {
                    e.angle = 5;
                }

            }
            if ((e.by >= b.by + 5 * split) && (e.by < b.by + 6 * split))
            {
                if (Math.cos(Math.toRadians(e.angle)) > 0)
                {
                    e.angle = 165;
                } else
                {
                    e.angle = 15;
                }
            }
            if ((e.by >= b.by + 6 * split) && (e.by < b.by + 7 * split))
            {
                if (Math.cos(Math.toRadians(e.angle)) > 0)
                {
                    e.angle = 150;
                } else
                {
                    e.angle = 30;
                }
            }
            if ((e.by >= b.by + 7 * split) && (e.by < b.by + 8 * split + 10))
            {
                if (Math.cos(Math.toRadians(e.angle)) > 0)
                {
                    e.angle = 135;
                } else
                {
                    e.angle = 45;
                }
            }

        }

    }

    boolean checkPlayerCol(Entities e, Entities b)
    {
        return ((e.bx + e.B_WIDTH >= b.bx)
                && (e.bx <= b.bx + b.B_WIDTH)
                && (e.by >= b.by - 16 - 10)
                && e.by <= b.by + b.B_HEIGHT + 10);
    }

    String score(int i)
    {

        if ((ball.bx < -32) && (i == 1))
        {
            p1 += 1;
            reset();

        } else if (ball.bx > W + 32)
        {
            p2 += 1;
            reset();
        }

        if (i == 1)
        {
            return Integer.toString(p1);
        } else
        {
            return Integer.toString(p2);
        }
    }

    void reset()
    {

        hit = false;
        ball.bx = this.W / 2 - 32;
        ball.by = this.H / 2 - 32;
        if (Math.cos(Math.toRadians(ball.angle)) > 0)
        {
            ball.angle = 180;
        } else
        {
            ball.angle = 0;
        }

    }

}
