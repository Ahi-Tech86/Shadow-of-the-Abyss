package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {
    public int speed;
    public String direction;
    public int worldX, worldY;

    // VARIABLE FOR CHECKBOX
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;

    // VARIABLE FOR CHECKING SIDES COLLISION
    public boolean collisionOn = false;

    // VARIABLES FOR CHANGING SPRITES
    public int spriteCounter = 0;
    public int spriteNum = 1;

    // FOR IDLE STATE
    BufferedImage[] leftIdle, rightIdle;
    // FOR JUMPING STATE
    BufferedImage[] leftJump, rightJump;
    // FOR RUNNING STATE
    BufferedImage[] leftRunning, rightRunning;

    protected BufferedImage getSpriteImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
