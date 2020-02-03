import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;

public class Main
  extends JFrame
  implements Runnable
{
  private static Thread thread;
  private static Main instance;
  private Graphics dbGraphics;
  private Image dbImage;
  private List<Integer> starsX = new ArrayList();
  private List<Integer> starsY = new ArrayList();
  private List<Integer> starSize = new ArrayList();
  private int stars = 10000;
  private int moveX = 0;
  private int moveY = 0;
  private int buffTimer = 600;
  private int mode = 1;
  private int x = 150;
  private int y = 150;
  private int x2 = 450;
  private int y2 = 75;
  private int x3 = 0;
  private int y3 = 600;
  private int x4 = -10000;
  private int y4 = -10000;
  private int x5 = -1200;
  private int y5 = 2000;
  private int a = 0;
  private int b = 0;
  private int ticks = 0;
  private int size = 20;
  private int enemySpeed = 25;
  private int frameSize = 900;
  private int eSpeedBuff = 0;
  
  private Main()
  {
    setTitle("Space Grab ~~ 0.0.1");
    setSize(this.frameSize, this.frameSize);
    setDefaultCloseOperation(3);
    setResizable(false);
    setLayout(null);
    setVisible(true);
    BufferedImage cursorImg = new BufferedImage(16, 16, 2);
    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
      cursorImg, new Point(0, 0), "blank cursor");
    getContentPane().setCursor(blankCursor);
    

    Random gen = new Random();
    for (int i = 0; i < this.stars; i++)
    {
      this.a = gen.nextInt(this.frameSize * 11);
      this.b = gen.nextInt(this.frameSize * 11);
      this.starsX.add(Integer.valueOf(this.a));
      this.starsY.add(Integer.valueOf(this.b));
    }
    for (int i = 0; i < this.stars; i++)
    {
      this.a = gen.nextInt(5);
      if (this.a == 0) {
        this.a += 2;
      } else {
        this.a += 1;
      }
      this.starSize.add(Integer.valueOf(this.a));
    }
    start();
  }
  
  public static Main getInstance()
  {
    if (instance == null) {
      instance = new Main();
    }
    return instance;
  }
  
  public static void main(String[] args)
  {
    getInstance();
  }
  
  public void move()
  {
    if (this.eSpeedBuff >= 1)
    {
      this.eSpeedBuff -= 1;
      this.enemySpeed = -1;
      System.out.println("eBuff up");
    }
    if (this.eSpeedBuff == 0)
    {
      this.enemySpeed = 2;
      System.out.println("eBuff down");
    }
    if (((this.x >= this.x2 - 15 ? 1 : 0) & (this.x <= this.x2 + 15 ? 1 : 0) & (this.y >= this.y2 - 15 ? 1 : 0) & (this.y <= this.y2 + 15 ? 1 : 0)) != 0)
    {
      place(1);
      place(2);
    }
    if (((this.x >= this.x5 - 15 ? 1 : 0) & (this.x <= this.x5 + 45 ? 1 : 0) & (this.y >= this.y5 - 15 ? 1 : 0) & (this.y <= this.y5 + 45 ? 1 : 0)) != 0)
    {
      place(1);
      place(5);
    }
    if (((this.x2 >= this.x5 - 15 ? 1 : 0) & (this.x2 <= this.x5 + 45 ? 1 : 0) & (this.y2 >= this.y5 - 15 ? 1 : 0) & (this.y2 <= this.y5 + 45 ? 1 : 0)) != 0)
    {
      place(2);
      place(5);
    }
    if (((this.x >= MouseInfo.getPointerInfo().getLocation().getX() - 15.0D ? 1 : 0) & (this.x <= MouseInfo.getPointerInfo().getLocation().getX() + 15.0D ? 1 : 0) & (this.y >= (int)MouseInfo.getPointerInfo().getLocation().getY() - 15 ? 1 : 0) & (this.y <= (int)MouseInfo.getPointerInfo().getLocation().getY() + 15 ? 1 : 0)) != 0)
    {
      place(1);
      this.size -= 3;
    }
    if (((this.x2 >= MouseInfo.getPointerInfo().getLocation().getX() - 15.0D ? 1 : 0) & (this.x2 <= MouseInfo.getPointerInfo().getLocation().getX() + 15.0D ? 1 : 0) & (this.y2 >= (int)MouseInfo.getPointerInfo().getLocation().getY() - 15 ? 1 : 0) & (this.y2 <= (int)MouseInfo.getPointerInfo().getLocation().getY() + 15 ? 1 : 0)) != 0)
    {
      place(2);
      this.size -= 3;
    }
    if (((this.x5 >= MouseInfo.getPointerInfo().getLocation().getX() - 45.0D ? 1 : 0) & (this.x5 <= MouseInfo.getPointerInfo().getLocation().getX() + 15.0D ? 1 : 0) & (this.y5 >= (int)MouseInfo.getPointerInfo().getLocation().getY() - 15 ? 1 : 0) & (this.y5 <= (int)MouseInfo.getPointerInfo().getLocation().getY() + 45 ? 1 : 0)) != 0)
    {
      place(5);
      this.size -= 5;
    }
    if (this.x - (int)MouseInfo.getPointerInfo().getLocation().getX() >= 1) {
      this.x -= this.enemySpeed;
    }
    if (this.x - (int)MouseInfo.getPointerInfo().getLocation().getX() <= -1) {
      this.x += this.enemySpeed;
    }
    if (this.y - (int)MouseInfo.getPointerInfo().getLocation().getY() >= 1) {
      this.y -= this.enemySpeed;
    }
    if (this.y - (int)MouseInfo.getPointerInfo().getLocation().getY() <= -1) {
      this.y += this.enemySpeed;
    }
    if (this.x2 - (int)MouseInfo.getPointerInfo().getLocation().getX() >= 1) {
      this.x2 -= this.enemySpeed;
    }
    if (this.x2 - (int)MouseInfo.getPointerInfo().getLocation().getX() <= -1) {
      this.x2 += this.enemySpeed;
    }
    if (this.y2 - (int)MouseInfo.getPointerInfo().getLocation().getY() >= 1) {
      this.y2 -= this.enemySpeed;
    }
    if (this.y2 - (int)MouseInfo.getPointerInfo().getLocation().getY() <= -1) {
      this.y2 += this.enemySpeed;
    }
    if (this.x5 - (int)MouseInfo.getPointerInfo().getLocation().getX() >= 1) {
      this.x5 -= this.enemySpeed - 1;
    }
    if (this.x5 - (int)MouseInfo.getPointerInfo().getLocation().getX() <= -1) {
      this.x5 += this.enemySpeed - 1;
    }
    if (this.y5 - (int)MouseInfo.getPointerInfo().getLocation().getY() >= 1) {
      this.y5 -= this.enemySpeed - 1;
    }
    if (this.y5 - (int)MouseInfo.getPointerInfo().getLocation().getY() <= -1) {
      this.y5 += this.enemySpeed - 1;
    }
    if (((this.x3 == (int)MouseInfo.getPointerInfo().getLocation().getX() ? 1 : 0) & (this.y3 == (int)MouseInfo.getPointerInfo().getLocation().getY() ? 1 : 0)) != 0)
    {
      place(3);
      this.size += 3;
    }
    if (this.x3 - (int)MouseInfo.getPointerInfo().getLocation().getX() >= 1) {
      this.x3 -= 1;
    }
    if (this.x3 - (int)MouseInfo.getPointerInfo().getLocation().getX() <= -1) {
      this.x3 += 1;
    }
    if (this.y3 - (int)MouseInfo.getPointerInfo().getLocation().getY() >= 1) {
      this.y3 -= 1;
    }
    if (this.y3 - (int)MouseInfo.getPointerInfo().getLocation().getY() <= -1) {
      this.y3 += 1;
    }
    if (((this.x4 >= MouseInfo.getPointerInfo().getLocation().getX() - 15.0D ? 1 : 0) & (this.x4 <= MouseInfo.getPointerInfo().getLocation().getX() + 15.0D ? 1 : 0) & (this.y4 >= (int)MouseInfo.getPointerInfo().getLocation().getY() - 15 ? 1 : 0) & (this.y4 <= (int)MouseInfo.getPointerInfo().getLocation().getY() + 15 ? 1 : 0)) != 0)
    {
      place(3);
      this.eSpeedBuff = 500;
      this.x4 = -10000;
      this.y4 = -10000;
    }
  }
  
  public void place(int point)
  {
    Random gen = new Random();
    int rx = gen.nextInt(this.frameSize * 2);
    int ry = gen.nextInt(this.frameSize * 2);
    int rxBuff = gen.nextInt(this.frameSize - 100);
    int ryBuff = gen.nextInt(this.frameSize - 100);
    while (((rx >= 0 ? 1 : 0) & (rx <= this.frameSize ? 1 : 0)) != 0) {
      rx = gen.nextInt(this.frameSize * 2);
    }
    while (((ry >= 0 ? 1 : 0) & (ry <= this.frameSize ? 1 : 0)) != 0) {
      ry = gen.nextInt(this.frameSize * 2);
    }
    if (rx % 2 == 0) {
      rx *= -1;
    }
    if (ry % 2 == 0) {
      ry *= -1;
    }
    if (point == 1)
    {
      this.x = rx;
      this.y = ry;
    }
    if (point == 2)
    {
      this.x2 = rx;
      this.y2 = ry;
    }
    if (point == 3)
    {
      this.x3 = rx;
      this.y3 = ry;
    }
    if (point == 4)
    {
      this.x4 = rxBuff;
      this.y4 = ryBuff;
    }
    if (point == 5)
    {
      this.x5 = rx;
      this.y5 = ry;
    }
  }
  
  public void start()
  {
    thread = new Thread(this);
    thread.start();
  }
  
  public void run()
  {
    for (;;)
    {
      move();
      repaint();
      this.ticks += 1;
      if (this.moveX <= this.frameSize * 10)
      {
        if (this.mode == 1)
        {
          this.moveX += 2;
          this.moveY += 2;
        }
        if (this.mode == 2)
        {
          this.moveX += 4;
          this.moveY += 4;
        }
        if (this.mode == 3)
        {
          this.moveX += 6;
          this.moveY += 6;
        }
        if (this.mode == 4)
        {
          this.moveX += 8;
          this.moveY += 8;
        }
        if (this.mode == 5)
        {
          this.moveX += 10;
          this.moveY += 10;
        }
      }
      else
      {
        this.moveX = 0;
        this.moveY = 0;
      }
      if (this.ticks == this.buffTimer) {
        place(4);
      }
      if (this.ticks == this.buffTimer + 500)
      {
        this.x4 = -10000;
        this.y4 = -10000;
        this.buffTimer += 3000;
      }
      System.out.println((int)MouseInfo.getPointerInfo().getLocation().getX() + "  " + (150 - (int)MouseInfo.getPointerInfo().getLocation().getX()) + "    " + this.ticks);
      try
      {
        Thread.sleep(6L);
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void paint(Graphics g)
  {
    if (this.dbGraphics == null)
    {
      this.dbImage = createImage(this.frameSize, this.frameSize);
      this.dbGraphics = this.dbImage.getGraphics();
    }
    this.dbGraphics.setColor(getBackground());
    this.dbGraphics.fillRect(0, 0, this.frameSize, this.frameSize);
    this.dbGraphics.setColor(getForeground());
    
    paintComponent(this.dbGraphics);
    g.drawImage(this.dbImage, 0, 0, this);
  }
  
  public void paintComponent(Graphics g)
  {
    g.setColor(new Color(0, 0, 0));
    g.fillRect(0, 0, this.frameSize, this.frameSize);
    for (int i = 0; i < this.stars; i++)
    {
      g.setColor(new Color(250, 250, 250));
      g.fillOval(((Integer)this.starsX.get(i)).intValue() - this.moveX, ((Integer)this.starsY.get(i)).intValue() - this.moveY, ((Integer)this.starSize.get(i)).intValue(), ((Integer)this.starSize.get(i)).intValue());
    }
    g.setColor(Color.RED);
    g.fillOval(this.x, this.y, 25, 25);
    g.setColor(Color.RED);
    g.fillOval(this.x2, this.y2, 25, 25);
    g.setColor(new Color(220, 0, 0));
    g.fillOval(this.x5, this.y5, 45, 45);
    g.setColor(Color.YELLOW);
    g.fillOval(this.x3, this.y3, 25, 25);
    

    g.setColor(Color.CYAN);
    g.fillOval(this.x4, this.y4, 30, 30);
    g.setColor(new Color(0, 150, 0));
    g.fillOval((int)MouseInfo.getPointerInfo().getLocation().getX(), (int)MouseInfo.getPointerInfo().getLocation().getY(), this.size, this.size);
  }
}
