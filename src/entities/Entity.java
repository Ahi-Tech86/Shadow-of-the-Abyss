package entities;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class Entity {

    // SYSTEM
    GamePanel gamePanel;
    public Random random;

    // VARIABLES
    public int speed;
    public String direction;
    public int worldX, worldY;
    public String lastDirection;

    // VARIABLE FOR CHECKBOX
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;

    // VARIABLE FOR CHECKING SIDES COLLISION
    public boolean collisionOn = false;

    // VARIABLES FOR CHANGING SPRITES
    public int spriteCounter = 0;
    public int spriteNum = 1;

    public int actionLockCounter = 0;

    // FOR IDLE STATE
    protected BufferedImage[] leftIdle, rightIdle;
    // FOR RUNNING STATE
    protected BufferedImage[] leftRunning, rightRunning;

    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    protected BufferedImage getSpriteImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setAction() {}

    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkPlayer(this);
        gamePanel.collisionChecker.checkObject(this, false);

        if (!collisionOn) {
            switch (direction) {
                case "right":
                    worldX += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
            }
        }

        spriteCounter++;
        if (spriteCounter > 5) {
            spriteCounter = 0;
            spriteNum++;

            if (spriteNum > 8) {
                spriteNum = 1;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (
                worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
        ) {
            switch (direction) {
                case "right":
                    image = rightRunning[spriteNum - 1];
                    break;
                case "left":
                    image = leftRunning[spriteNum - 1];
                    break;
            }

            graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            graphics2D.setColor(Color.BLUE);
            graphics2D.drawRect(screenX + this.solidArea.x, screenY + this.solidArea.y, this.solidArea.width, this.solidArea.height);
        }
    }
}
