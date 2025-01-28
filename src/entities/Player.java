package entities;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    private String lastDirection = "right";

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        // VARIABLES FOR SET UP CAMERA
        screenX = gamePanel.screenWidth / 2 + (gamePanel.tileSize * 2);
        screenY = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 2);

        // CHECKBOX SETUP
        solidArea = new Rectangle();
        solidArea.x = 57;
        solidArea.y = 17;
        solidArea.width = 18;
        solidArea.height = 46;

        setDefaultVariables();
        getPlayerSprites();
    }

    private void setDefaultVariables() {
        worldX = gamePanel.tileSize * 2;
        worldY = gamePanel.tileSize * 10;
        speed = 4;
        direction = "idle";
    }

    private void getPlayerSprites() {
        int  k = 1;

        leftIdle = new BufferedImage[8];
        rightIdle = new BufferedImage[8];
        leftJump = new BufferedImage[8];
        rightJump = new BufferedImage[8];
        leftRunning = new BufferedImage[8];
        rightRunning = new BufferedImage[8];

        for (int i = 0; i < 8; i++) {
            leftIdle[i] = getSpriteImage("/character/idle/idle_left" + k + ".png");
            rightIdle[i] = getSpriteImage("/character/idle/idle_right" + k + ".png");
            leftRunning[i] = getSpriteImage("/character/running/left" + k + ".png");
            rightRunning[i] = getSpriteImage("/character/running/right" + k + ".png");
            leftJump[i] = getSpriteImage("/character/jumping/jump_left" + k + ".png");
            rightJump[i] = getSpriteImage("/character/jumping/jump_right" + k + ".png");
            ++k;
        }
    }

    public void update() {
        // HORIZONTAL MOVING
        if (keyHandler.leftPressed || keyHandler.rightPressed) {
            // KEYS HANDLE
            if (keyHandler.rightPressed) {
                direction = "right";
                lastDirection = "right";
            } else {
                direction = "left";
                lastDirection = "left";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
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

            // SPRITES CHANGING
            spriteCounter++;
            if (spriteCounter > 5) {
                spriteCounter = 0;
                spriteNum++;
                if (spriteNum > 8) {
                    spriteNum = 1;
                }
            }

        } else {
            direction = "idle";

            spriteCounter++;
            if (spriteCounter > 7) {
                spriteCounter = 0;
                spriteNum++;
                if (spriteNum > 8) {
                    spriteNum = 1;
                }
            }
        }

        System.out.println(worldX + " " + worldY);
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        switch (direction) {
            case "right":
                image = rightRunning[spriteNum - 1];
                break;
            case "left":
                image = leftRunning[spriteNum - 1];
                break;
            case "idle":
                if (lastDirection.equals("right")) {
                    image = rightIdle[spriteNum - 1];
                } else if (lastDirection.equals("left")) {
                    image = leftIdle[spriteNum - 1];
                }
                break;
        }

        graphics2D.drawImage(image, screenX, screenY, 128, 64, null);

        // CHECKBOX DISPLAYING
        Composite originalComposite = graphics2D.getComposite();
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(this.screenX + this.solidArea.x, this.screenY + this.solidArea.y, this.solidArea.width, this.solidArea.height);

        graphics2D.setColor(Color.GREEN);
        graphics2D.drawRect(screenX, screenY, 128, 64);
        graphics2D.setComposite(originalComposite);
    }
}
