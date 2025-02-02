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
    protected Random random;

    // VARIABLES
    public int speed;
    public String direction;
    public int worldX, worldY;
    public String lastDirection;

    // STATUS
    public int maxLife;
    public int maxStamina;
    public int currentLife;
    public int currentStamina;
    public int restoreStaminaCounter;

    // VARIABLE FOR CHECKBOX
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;

    // VARIABLE FOR CHECKING SIDES COLLISION
    public boolean collisionOn = false;

    // VARIABLES FOR ACTION
    public int actionLockCounter = 0;

    // VARIABLES FOR DAMAGE
    public boolean isInvincible = false;
    public int invincibleCounter = 0;

    // VARIABLES FOR ATTACK
    protected int attackType = 1;
    protected int attackSpriteNum = 1;
    protected int attackSpriteCounter = 0;
    protected boolean isAttacking = false;

    // VARIABLES FOR DYING STATE
    public boolean isAlive = true;
    public boolean isDying = false;
    public int dyingCounter = 0;
    public int dyingSpriteNum = 1;
    public byte maxSpriteNumbersForDeath = 1;

    public Rectangle leftAttackArea = new Rectangle(0, 0, 0, 0);
    public Rectangle rightAttackArea = new Rectangle(0, 0, 0, 0);
    public Rectangle currentAttackArea = new Rectangle(0, 0, 0, 0);

    // 0 - player
    // 1 - monster
    public int type;

    // VARIABLES FOR CHANGING SPRITES
    public int spriteNum = 1;
    public int spriteCounter = 0;
    public int maxSpriteNumbers = 1;
    public int maxSpriteNumbersForTakingHit = 4;

    // FOR IDLE STATE
    protected BufferedImage[] leftIdle, rightIdle;
    // FOR DEATH STATE
    protected BufferedImage[] leftDeath, rightDeath;
    // FOR ATTACK STATE
    protected BufferedImage[] leftAttack, rightAttack;
    // FOR RUNNING STATE
    protected BufferedImage[] leftRunning, rightRunning;
    // FOR TAKE HIT STATE
    protected BufferedImage[] leftTakeHit, rightTakeHit;

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

    protected BufferedImage[] loadSprites(String pathPrefix, byte frameCount) {
        BufferedImage[] sprites = new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            sprites[i] = getSpriteImage(pathPrefix + (i + 1) + ".png");
        }

        return sprites;
    }

    public void setAction() {}

    public void update() {
        if (isDying) {
            dyingCounter++;
            if (dyingCounter > 15) {
                dyingCounter = 0;
                dyingSpriteNum++;

                if (dyingSpriteNum > maxSpriteNumbersForDeath) {
                    isAlive = false;
                    dyingSpriteNum = 4;
                }
            }

        } else {
            if (!isInvincible) {
                setAction();

                collisionOn = false;
                gamePanel.collisionChecker.checkTile(this);
                gamePanel.collisionChecker.checkObject(this, false);

                boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);
                if (this.type == 1 && contactPlayer) {
                    if (!gamePanel.player.isInvincible) {
                        gamePanel.player.currentLife -= 10;
                        gamePanel.player.isInvincible = true;
                    }
                }

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
            }
        }


        spriteCounter++;
        if (!isInvincible) {
            if (spriteCounter > 5) {
                spriteCounter = 0;
                spriteNum++;

                if (spriteNum > maxSpriteNumbers) {
                    spriteNum = 1;
                }
            }
        } else {
            if (spriteCounter > 5) {
                spriteCounter = 0;
                spriteNum++;

                if (spriteNum > maxSpriteNumbersForTakingHit) {
                    spriteNum = 1;
                }
            }
        }

        if (isInvincible) {
            invincibleCounter++;

            if (invincibleCounter == 30) {
                isInvincible = false;
                invincibleCounter = 0;
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
            if (isDying) {
                switch (lastDirection) {
                    case "right":
                        image = rightDeath[dyingSpriteNum - 1];
                        break;
                    case "left":
                        image = leftDeath[dyingSpriteNum - 1];
                        break;
                }

            } else {
                if (isInvincible) {
                    switch (lastDirection) {
                        case "right":
                            image = rightTakeHit[spriteNum - 1];
                            break;
                        case "left":
                            image = leftTakeHit[spriteNum - 1];
                    }
                } else {
                    switch (direction) {
                        case "right":
                            image = rightRunning[spriteNum - 1];
                            break;
                        case "left":
                            image = leftRunning[spriteNum - 1];
                            break;
                    }
                }
            }

            graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            graphics2D.setColor(Color.BLUE);
            graphics2D.drawRect(screenX + this.solidArea.x, screenY + this.solidArea.y, this.solidArea.width, this.solidArea.height);
        }
    }
}
