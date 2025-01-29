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

    int hasKey = 0;

    private String lastDirection = "right";

    // VARIABLES FOR JUMPING AND GRAVITATION
    private double velocity_Y = 0;
    private final double gravity = 0.6;
    private boolean isJumping = false;
    private final double jumpSpeed = -10.0;

    private final int climbSpeed = 2;
    public boolean isClimbing = false;
    BufferedImage[] climb = new BufferedImage[6];

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
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
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

            if (k < 7) {
                climb[i] = getSpriteImage("/character/climbing/climb" + k + ".png");
            }

            ++k;
        }
    }

    public void update() {
        // HORIZONTAL MOVING
        if (keyHandler.leftPressed || keyHandler.rightPressed) {
            // KEYS HANDLE
            direction = keyHandler.rightPressed ? "right" : "left";
            lastDirection = direction;

            // CHECK TILE COLLISION
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

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

        } else {
            direction = "idle";
        }

        boolean isNearLadder = gamePanel.collisionChecker.isPlayerNearLadder(this);
        if (keyHandler.ePressed && isNearLadder) {
            isClimbing = true;
            spriteNum = 1;
        }

        if (isClimbing) {
            worldY -= climbSpeed;
        }

        if (isClimbing && !isNearLadder) {
            isClimbing = false;
        }

        boolean onGround = gamePanel.collisionChecker.isOnGround(this);
        if (keyHandler.spacePressed && onGround && !isClimbing) {
            isJumping = true;
            velocity_Y = jumpSpeed;
        }

        if ((isJumping || !onGround) && !isClimbing) {
            velocity_Y += gravity;
            worldY += (int) velocity_Y;

            boolean collisionWithTile = gamePanel.collisionChecker.isCollisionDetected(this);

            if (collisionWithTile) {
                isJumping = false;
                velocity_Y = 0;
                worldY = gamePanel.collisionChecker.getGroundY(this) * gamePanel.tileSize;
            }
        }

        System.out.println(hasKey);

        // SPRITES CHANGING
        spriteCounter++;
        if (isClimbing) {
            if (spriteCounter > 10) {
                spriteCounter = 0;
                spriteNum++;

                if (spriteNum > 6) {
                    spriteNum = 1;
                }
            }
        } else {
            if (spriteCounter > 5) {
                spriteCounter = 0;
                spriteNum++;

                if (spriteNum > 8) {
                    spriteNum = 1;
                }
            }
        }

    }

    private void pickUpObject(int objectIndex) {
        if (objectIndex != 999) {
            String objectName = gamePanel.objects[objectIndex].name;

            if (objectName.equals("Crystal")) {
                hasKey++;
            }

            gamePanel.objects[objectIndex] = null;
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = getCurrentSprite();
        graphics2D.drawImage(image, screenX, screenY, 128, 64, null);
        drawHitbox(graphics2D);
    }

    private BufferedImage getCurrentSprite() {
        if (isClimbing) {
            return climb[spriteNum - 1];
        }

        if (isJumping) {
            return lastDirection.equals("right") ? rightJump[spriteNum - 1] : leftJump[spriteNum - 1];
        }

        switch (direction) {
            case "right":
                return rightRunning[spriteNum - 1];
            case "left":
                return leftRunning[spriteNum - 1];
            case "idle":
                return lastDirection.equals("right") ? rightIdle[spriteNum - 1] : leftIdle[spriteNum - 1];
            default:
                return null;
        }
    }

    private void drawHitbox(Graphics2D graphics2D) {
        Composite originalComposite = graphics2D.getComposite();
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(
                this.screenX + this.solidArea.x,
                this.screenY + this.solidArea.y,
                this.solidArea.width,
                this.solidArea.height
        );

        graphics2D.setColor(Color.GREEN);
        graphics2D.drawRect(screenX, screenY, 128, 64);
        graphics2D.setComposite(originalComposite);
    }
}
