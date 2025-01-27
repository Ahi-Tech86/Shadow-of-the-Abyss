package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public String direction;
    public int worldX, worldY;
    public int speed;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public boolean collisionOn = false;
}
