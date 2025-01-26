package entities;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    String direction;
    BufferedImage idle1, idle2, idle3, idle4, idle5, idle6, idle7, idle8;
    BufferedImage left1, left2, left3, left4, left5, left6, left7, left8;
    BufferedImage right1, right2, right3, right4, right5, right6, right7, right8;

    // VARIABLES FOR GRAVITY
    private double velocityY;
    private final double gravity = 0.6;
    private final double jumpSpeed = -10.0;
    private boolean isJumping = false;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        setDefaultVariables();
        getPlayerImage();
    }

    private void setDefaultVariables() {
        x = 100;
        y = 600;
        speed = 4;
        direction = "idle";
    }

    public void getPlayerImage() {
        try {
            int i = 1;

            left1 = ImageIO.read(getClass().getResourceAsStream("/character/running/left/left" + i++ + ".png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/character/running/left/left" + i++ + ".png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/character/running/left/left" + i++ + ".png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/character/running/left/left" + i++ + ".png"));
            left5 = ImageIO.read(getClass().getResourceAsStream("/character/running/left/left" + i++ + ".png"));
            left6 = ImageIO.read(getClass().getResourceAsStream("/character/running/left/left" + i++ + ".png"));
            left7 = ImageIO.read(getClass().getResourceAsStream("/character/running/left/left" + i++ + ".png"));
            left8 = ImageIO.read(getClass().getResourceAsStream("/character/running/left/left" + i++ + ".png"));

            i = 1;
            right1 = ImageIO.read(getClass().getResourceAsStream("/character/running/right/right" + i++ + ".png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/character/running/right/right" + i++ + ".png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/character/running/right/right" + i++ + ".png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/character/running/right/right" + i++ + ".png"));
            right5 = ImageIO.read(getClass().getResourceAsStream("/character/running/right/right" + i++ + ".png"));
            right6 = ImageIO.read(getClass().getResourceAsStream("/character/running/right/right" + i++ + ".png"));
            right7 = ImageIO.read(getClass().getResourceAsStream("/character/running/right/right" + i++ + ".png"));
            right8 = ImageIO.read(getClass().getResourceAsStream("/character/running/right/right" + i++ + ".png"));

            i = 1;
            idle1 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle" + i++ + ".png"));
            idle2 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle" + i++ + ".png"));
            idle3 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle" + i++ + ".png"));
            idle4 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle" + i++ + ".png"));
            idle5 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle" + i++ + ".png"));
            idle6 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle" + i++ + ".png"));
            idle7 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle" + i++ + ".png"));
            idle8 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle" + i++ + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // KEYS HANDLE
        if (keyHandler.upPressed) {
            y -= 0;
        } else if (keyHandler.downPressed) {
            y += 0;
        } else if (keyHandler.rightPressed) {
            direction = "right";
            x += speed;
        } else if (keyHandler.leftPressed) {
            direction = "left";
            x -= speed;
        }

        // SPRITES CHANGING
        if (keyHandler.rightPressed || keyHandler.leftPressed) {
            spriteCounter++;
            if (spriteCounter > 5) {
                spriteCounter = 0;
                spriteNum++;
                if (spriteNum > 8) {
                    spriteNum = 1;
                }
            }
        } else {
            spriteCounter = 0;
            spriteNum = 1;
        }

        if (keyHandler.spacePressed && !isJumping) {
            velocityY = jumpSpeed;
            isJumping = true;
        }

        // APPLY GRAVITY
        velocityY += gravity;
        y += velocityY;

        // Check if player has landed
        if (y >= gamePanel.screenHeight - (2 * gamePanel.tileSize)) {
            y = gamePanel.screenHeight - (2 * gamePanel.tileSize);
            velocityY = 0;
            isJumping = false;
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        switch (direction) {
            case "right":
                switch (spriteNum) {
                    case 1:
                        image = right1;
                        break;
                    case 2:
                        image = right2;
                        break;
                    case 3:
                        image = right3;
                        break;
                    case 4:
                        image = right4;
                        break;
                    case 5:
                        image = right5;
                        break;
                    case 6:
                        image = right6;
                        break;
                    case 7:
                        image = right7;
                        break;
                    case 8:
                        image = right8;
                        break;
                }
                break;
            case "left":
                switch (spriteNum) {
                    case 1:
                        image = left1;
                        break;
                    case 2:
                        image = left2;
                        break;
                    case 3:
                        image = left3;
                        break;
                    case 4:
                        image = left4;
                        break;
                    case 5:
                        image = left5;
                        break;
                    case 6:
                        image = left6;
                        break;
                    case 7:
                        image = left7;
                        break;
                    case 8:
                        image = left8;
                        break;
                }
                break;
            case "idle":
                switch (spriteNum) {
                    case 1:
                        image = idle1;
                        break;
                    case 2:
                        image = idle2;
                        break;
                    case 3:
                        image = idle3;
                        break;
                    case 4:
                        image = idle4;
                        break;
                    case 5:
                        image = idle5;
                        break;
                    case 6:
                        image = idle6;
                        break;
                    case 7:
                        image = idle7;
                        break;
                    case 8:
                        image = idle8;
                        break;
                }
                break;
        }

        graphics2D.drawImage(image, x, y, 128, 64, null);
    }
}
