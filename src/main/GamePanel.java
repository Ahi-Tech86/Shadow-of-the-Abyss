package main;

import entities.Player;
import objects.SuperObject;
import tiles.TileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {

    // SCREEN SETTINGS
    final int scale = 2;
    final int originalTileSize = 32;
    public final int tileSize = originalTileSize * scale;

    // 1024x576
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 9;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // WORLD SETTINGS
    public final int maxWorldCol = 60;
    public final int maxWorldRow = 12;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxScreenRow;

    // BACKGROUND IMAGES
    private BufferedImage backgroundLayer1;
    private BufferedImage backgroundLayer2;
    private BufferedImage backgroundLayer3;
    private BufferedImage backgroundLayer4;

    // FPS
    int FPS = 60;

    Thread gameThread;
    KeyHandler keyHandler = new KeyHandler();
    public SuperObject[] objects = new SuperObject[10];
    TileManager tileManager = new TileManager(this);
    public Player player = new Player(this, keyHandler);
    public AssetSetter assetSetter = new AssetSetter(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

        loadBackgroundImages();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        int oneSecond = 1000000000;
        double drawInterval = (double) oneSecond / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                // 1 UPDATE INFORMATION
                update();

                // 2 DRAW THE SCREEN
                repaint();

                delta--;
                drawCount++;
            }

            if (timer >= oneSecond) {
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    private void loadBackgroundImages() {
        try {
            backgroundLayer1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/background.png")));
            backgroundLayer2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/background2.png")));
            backgroundLayer3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/background3.png")));
            backgroundLayer4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/background/background4.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupGame() {
        assetSetter.setObject();
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        // DRAWING BACKGROUND LAYERS
        graphics2D.drawImage(backgroundLayer1, 0, 0, screenWidth, screenHeight, null);
        graphics2D.drawImage(backgroundLayer2, 0, 0, screenWidth, screenHeight, null);
        graphics2D.drawImage(backgroundLayer3, 0, 0, screenWidth, screenHeight, null);
        graphics2D.drawImage(backgroundLayer4, 0, 0, screenWidth, screenHeight, null);

        // DRAWING TILES
        tileManager.draw(graphics2D);

        // DRAWING OBJECTS
        for (SuperObject object : objects) {
            if (object != null) {
                object.draw(graphics2D, this);
            }
        }

        player.draw(graphics2D);

        graphics.dispose();
    }
}
