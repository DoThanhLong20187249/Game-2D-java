package Main;

import Entity.Player;
import Tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // SREEN SETTING
    public final  int originalTileSize = 16; // 16 x 16 tile
    public final  int scale = 3;
    public final int tileSize = scale * originalTileSize; // 48 x 48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHigh = tileSize * maxScreenRow;
    // WORLD MAP
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxScreenCol;
    public final int worldHeight = tileSize * maxScreenRow;

    KeyHandler keyHandler = new KeyHandler();
    public Player player = new Player(this,keyHandler);
    TileManager tileManager = new TileManager(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    // FPS
    int FPS = 60;

    Thread gameThread;

//    // set player default posittions
//
//    int playerX = 100;
//    int playerY = 100;
//    int playerSpeed = 4;



    public GamePanel (){
        this.setPreferredSize(new Dimension(screenWidth,screenHigh));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void gameStartThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS; // 0.01666 seconds
        double delta = 0;
        long lasTime = System.nanoTime();
        long currentTime;
        
        while (gameThread != null){
            // 1 Update : update information such as character posittions
            // 2 Draw : draw the screen with the updated information
            currentTime = System.nanoTime();
            delta += (currentTime - lasTime) / drawInterval;
            lasTime = currentTime;
            System.out.println("Delta: "+delta);
            if (delta >= 1 ){
                update();
                repaint();
                delta--;
            }

        }
    }

    public void update(){
        player.update();
    }

    public void paintComponent(Graphics g ){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileManager.draw(g2);
        player.draw(g2);
        g2.dispose();
    }
}
