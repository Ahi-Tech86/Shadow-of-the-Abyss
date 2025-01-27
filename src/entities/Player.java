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

    public final int screenX;
    public final int screenY;

    BufferedImage left1, left2, left3, left4, left5, left6, left7, left8;
    BufferedImage right1, right2, right3, right4, right5, right6, right7, right8;
    BufferedImage idleLeft1, idleLeft2, idleLeft3, idleLeft4, idleLeft5, idleLeft6, idleLeft7, idleLeft8;
    BufferedImage jumpLeft1, jumpLeft2, jumpLeft3, jumpLeft4, jumpLeft5, jumpLeft6, jumpLeft7, jumpLeft8;
    BufferedImage jumpRight1, jumpRight2, jumpRight3, jumpRight4, jumpRight5, jumpRight6, jumpRight7, jumpRight8;
    BufferedImage idleRight1, idleRight2, idleRight3, idleRight4, idleRight5, idleRight6, idleRight7, idleRight8;

    // VARIABLES FOR GRAVITY
    private double velocityY;
    private final double gravity = 0.3;
    private final double jumpSpeed = - 6.5; // jump height
    private boolean isJumping = false;

    private String lastDirection = "right";

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        // VARIABLES FOR SET UP CAMERA
        screenX = gamePanel.screenWidth / 2 + (gamePanel.tileSize * 2);
        screenY = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 2);

        solidArea = new Rectangle();
        solidArea.x = 57;
        solidArea.y = 17;
        solidArea.width = 18;
        solidArea.height = 46;

        setDefaultVariables();
        getPlayerImage();
    }

    private void setDefaultVariables() {
        worldX = gamePanel.tileSize * 2;
        worldY = gamePanel.tileSize * 11;
        speed = 4;
        direction = "idle";
    }

    public void getPlayerImage() {
        try {
            int i = 1;

            left1 = ImageIO.read(getClass().getResourceAsStream("/character/running/left" + i++ + ".png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/character/running/left" + i++ + ".png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/character/running/left" + i++ + ".png"));
            left4 = ImageIO.read(getClass().getResourceAsStream("/character/running/left" + i++ + ".png"));
            left5 = ImageIO.read(getClass().getResourceAsStream("/character/running/left" + i++ + ".png"));
            left6 = ImageIO.read(getClass().getResourceAsStream("/character/running/left" + i++ + ".png"));
            left7 = ImageIO.read(getClass().getResourceAsStream("/character/running/left" + i++ + ".png"));
            left8 = ImageIO.read(getClass().getResourceAsStream("/character/running/left" + i++ + ".png"));

            i = 1;
            right1 = ImageIO.read(getClass().getResourceAsStream("/character/running/right" + i++ + ".png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/character/running/right" + i++ + ".png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/character/running/right" + i++ + ".png"));
            right4 = ImageIO.read(getClass().getResourceAsStream("/character/running/right" + i++ + ".png"));
            right5 = ImageIO.read(getClass().getResourceAsStream("/character/running/right" + i++ + ".png"));
            right6 = ImageIO.read(getClass().getResourceAsStream("/character/running/right" + i++ + ".png"));
            right7 = ImageIO.read(getClass().getResourceAsStream("/character/running/right" + i++ + ".png"));
            right8 = ImageIO.read(getClass().getResourceAsStream("/character/running/right" + i++ + ".png"));

            i = 1;
            idleLeft1 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_left" + i++ + ".png"));
            idleLeft2 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_left" + i++ + ".png"));
            idleLeft3 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_left" + i++ + ".png"));
            idleLeft4 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_left" + i++ + ".png"));
            idleLeft5 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_left" + i++ + ".png"));
            idleLeft6 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_left" + i++ + ".png"));
            idleLeft7 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_left" + i++ + ".png"));
            idleLeft8 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_left" + i++ + ".png"));

            i = 1;
            idleRight1 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_right" + i++ + ".png"));
            idleRight2 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_right" + i++ + ".png"));
            idleRight3 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_right" + i++ + ".png"));
            idleRight4 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_right" + i++ + ".png"));
            idleRight5 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_right" + i++ + ".png"));
            idleRight6 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_right" + i++ + ".png"));
            idleRight7 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_right" + i++ + ".png"));
            idleRight8 = ImageIO.read(getClass().getResourceAsStream("/character/idle/idle_right" + i++ + ".png"));

            i = 1;
            jumpLeft1 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_left" + i++ + ".png"));
            jumpLeft2 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_left" + i++ + ".png"));
            jumpLeft3 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_left" + i++ + ".png"));
            jumpLeft4 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_left" + i++ + ".png"));
            jumpLeft5 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_left" + i++ + ".png"));
            jumpLeft6 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_left" + i++ + ".png"));
            jumpLeft7 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_left" + i++ + ".png"));
            jumpLeft8 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_left" + i++ + ".png"));

            i = 1;
            jumpRight1 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_right" + i++ + ".png"));
            jumpRight2 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_right" + i++ + ".png"));
            jumpRight3 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_right" + i++ + ".png"));
            jumpRight4 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_right" + i++ + ".png"));
            jumpRight5 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_right" + i++ + ".png"));
            jumpRight6 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_right" + i++ + ".png"));
            jumpRight7 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_right" + i++ + ".png"));
            jumpRight8 = ImageIO.read(getClass().getResourceAsStream("/character/jumping/jump_right" + i++ + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.spacePressed) {
            // KEYS HANDLE
            if (keyHandler.upPressed) {

            } else if (keyHandler.downPressed) {

            } else if (keyHandler.rightPressed) {
                direction = "right";
                lastDirection = "right";
            } else if (keyHandler.leftPressed) {
                direction = "left";
                lastDirection = "left";
            }

            // SPRITES CHANGING
            if (keyHandler.rightPressed || keyHandler.leftPressed || isJumping) {
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

            // CHECK TILE COLLISION
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= 0;
                        break;
                    case "down":
                        worldY += 0;
                        break;
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

            spriteCounter++;
            if (spriteCounter > 5) {
                spriteCounter = 0;
                spriteNum++;
                if (spriteNum > 8) {
                    spriteNum = 1;
                }
            }
        }

        if (keyHandler.spacePressed && !isJumping) {
            velocityY = jumpSpeed;
            isJumping = true;
        }

        // APPLY GRAVITY
        velocityY += gravity;
        worldY += velocityY;

        // Check if player has landed
        if (worldY >= gamePanel.screenHeight - (2 * gamePanel.tileSize)) {
            worldY = gamePanel.screenHeight - (2 * gamePanel.tileSize);
            velocityY = 0;
            isJumping = false;
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        if (isJumping) {
            switch (lastDirection) {
                case "right":
                    switch (spriteNum) {
                        case 1:
                            image = jumpRight1;
                            break;
                        case 2:
                            image = jumpRight2;
                            break;
                        case 3:
                            image = jumpRight3;
                            break;
                        case 4:
                            image = jumpRight4;
                            break;
                        case 5:
                            image = jumpRight5;
                            break;
                        case 6:
                            image = jumpRight6;
                            break;
                        case 7:
                            image = jumpRight7;
                            break;
                        case 8:
                            image = jumpRight8;
                            break;
                    }
                    break;
                case "left":
                    switch (spriteNum) {
                        case 1:
                            image = jumpLeft1;
                            break;
                        case 2:
                            image = jumpLeft2;
                            break;
                        case 3:
                            image = jumpLeft3;
                            break;
                        case 4:
                            image = jumpLeft4;
                            break;
                        case 5:
                            image = jumpLeft5;
                            break;
                        case 6:
                            image = jumpLeft6;
                            break;
                        case 7:
                            image = jumpLeft7;
                            break;
                        case 8:
                            image = jumpLeft8;
                            break;
                    }
                    break;
            }

        } else {
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
                    switch (lastDirection) {
                        case "right":
                            switch (spriteNum) {
                                case 1:
                                    image = idleRight1;
                                    break;
                                case 2:
                                    image = idleRight2;
                                    break;
                                case 3:
                                    image = idleRight3;
                                    break;
                                case 4:
                                    image = idleRight4;
                                    break;
                                case 5:
                                    image = idleRight5;
                                    break;
                                case 6:
                                    image = idleRight6;
                                    break;
                                case 7:
                                    image = idleRight7;
                                    break;
                                case 8:
                                    image = idleRight8;
                                    break;
                            }
                            break;
                        case "left":
                            switch (spriteNum) {
                                case 1:
                                    image = idleLeft1;
                                    break;
                                case 2:
                                    image = idleLeft2;
                                    break;
                                case 3:
                                    image = idleLeft3;
                                    break;
                                case 4:
                                    image = idleLeft4;
                                    break;
                                case 5:
                                    image = idleLeft5;
                                    break;
                                case 6:
                                    image = idleLeft6;
                                    break;
                                case 7:
                                    image = idleLeft7;
                                    break;
                                case 8:
                                    image = idleLeft8;
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }

        graphics2D.drawImage(image, screenX, screenY, 128, 64, null);

        // CHECKBOX DISPLAYING
//        Composite originalComposite = graphics2D.getComposite();
//        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
//        graphics2D.setColor(Color.RED);
//        graphics2D.drawRect(this.screenX + this.solidArea.x, this.screenY + this.solidArea.y, this.solidArea.width, this.solidArea.height);
//        graphics2D.setComposite(originalComposite);
    }
}
