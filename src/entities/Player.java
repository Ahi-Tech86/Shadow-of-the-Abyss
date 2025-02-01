package entities;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    // VARIABLES FOR JUMPING AND GRAVITATION
    private double velocity_Y = 0;
    private final double gravity = 0.6;
    private boolean isJumping = false;
    private final double jumpSpeed = -10.0;

    // VARIABLES FOR CLIMBING
    private final int climbSpeed = 2;
    public boolean isClimbing = false;

    // SPRITE ARRAYS
    BufferedImage[] climb;
    BufferedImage[] leftJump, rightJump;
    BufferedImage[] currentAttackFrames;
    BufferedImage[] leftAttackType1, leftAttackType2, leftAttackType3;
    BufferedImage[] rightAttackType1, rightAttackType2, rightAttackType3;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);
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

        // RIGHT ATTACK AREA BOX
        rightAttackArea = new Rectangle();
        rightAttackArea.x = 80;
        rightAttackArea.y = 22;
        rightAttackArea.width = 27;
        rightAttackArea.height = 31;
        // LEFT ATTACK AREA BOX
        leftAttackArea = new Rectangle();
        leftAttackArea.x = 20;
        leftAttackArea.y = 22;
        leftAttackArea.width = 32;
        leftAttackArea.height = 31;

        lastDirection = "right";

        setDefaultVariables();
        getPlayerSprites();
    }

    private void setDefaultVariables() {
        worldX = gamePanel.tileSize * 2;
        worldY = gamePanel.tileSize * 10;
        speed = 4;
        direction = "idle";

        maxLife = 100;
        maxStamina = 100;
        currentLife = maxLife;
        currentStamina = maxStamina;
        restoreStaminaCounter = 0;
    }

    private void getPlayerSprites() {
        byte IDLE_FRAMES = 8;
        byte CLIMB_FRAMES = 6;
        byte RUNNING_FRAMES = 8;
        byte JUMPING_FRAMES = 8;
        byte TAKE_HIT_FRAMES = 3;
        byte ATTACK_TYPE1_FRAMES = 9;
        byte ATTACK_TYPE2_FRAMES = 5;
        byte ATTACK_TYPE3_FRAMES = 6;

        climb = loadSprites("/character/climbing/climb", CLIMB_FRAMES);
        leftIdle = loadSprites("/character/idle/idle_left", IDLE_FRAMES);
        rightIdle = loadSprites("/character/idle/idle_right", IDLE_FRAMES);
        leftRunning = loadSprites("/character/running/left", RUNNING_FRAMES);
        rightRunning = loadSprites("/character/running/right", RUNNING_FRAMES);
        leftJump = loadSprites("/character/jumping/jump_left", JUMPING_FRAMES);
        rightJump = loadSprites("/character/jumping/jump_right", JUMPING_FRAMES);
        leftTakeHit = loadSprites("/character/takeHit/take_hit_left", TAKE_HIT_FRAMES);
        rightTakeHit = loadSprites("/character/takeHit/take_hit_right", TAKE_HIT_FRAMES);
        leftAttackType1 = loadSprites("/character/attack/type1/attack_type_1_left", ATTACK_TYPE1_FRAMES);
        rightAttackType1 = loadSprites("/character/attack/type1/attack_type_1_right", ATTACK_TYPE1_FRAMES);
        leftAttackType2 = loadSprites("/character/attack/type2/attack_type_2_left", ATTACK_TYPE2_FRAMES);
        rightAttackType2 = loadSprites("/character/attack/type2/attack_type_2_right", ATTACK_TYPE2_FRAMES);
        leftAttackType3 = loadSprites("/character/attack/type3/attack_type_3_left", ATTACK_TYPE3_FRAMES);
        rightAttackType3 = loadSprites("/character/attack/type3/attack_type_3_right", ATTACK_TYPE3_FRAMES);
    }

    private BufferedImage[] loadSprites(String pathPrefix, byte frameCount) {
        BufferedImage[] sprites = new BufferedImage[frameCount];

        for (int i = 0; i < frameCount; i++) {
            sprites[i] = getSpriteImage(pathPrefix + (i + 1) + ".png");
        }

        return sprites;
    }

    public void update() {
        // HORIZONTAL MOVING
        if ((keyHandler.leftPressed || keyHandler.rightPressed) && !isAttacking) {
            // KEYS HANDLE
            direction = keyHandler.rightPressed ? "right" : "left";
            lastDirection = direction;

            // CHECK TILE COLLISION
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // CHECK OBJECT COLLISION
            int objectIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            // CHECK MONSTER COLLISION
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
            contactWithMonster(monsterIndex);

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

        } else if (keyHandler.ePressed || isAttacking) {
            attacking();
            gamePanel.collisionChecker.checkAttackAreaIntersectWithMonster(this, gamePanel.monsters);
        } else {
            direction = "idle";
        }

        boolean isNearLadder = gamePanel.collisionChecker.isPlayerNearLadder(this);
        if (keyHandler.fPressed && isNearLadder) {
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

        if (currentStamina < maxStamina && !isAttacking) {
            restoreStaminaCounter++;

            if (restoreStaminaCounter > 10) {
                currentStamina += 1;
                restoreStaminaCounter = 0;
            }
        }

        // SPRITES CHANGING
        spriteCounter++;
        if (isClimbing) {
            if (spriteNum > 6) {
                spriteNum = 1;
            }

            if (spriteCounter > 10) {
                spriteCounter = 0;
                spriteNum++;

                if (spriteNum > 6) {
                    spriteNum = 1;
                }
            }
        } else {

            if (isInvincible) {
                if (spriteNum > 3) {
                    spriteNum = 1;
                }

                if (spriteCounter > 10) {
                    spriteCounter = 0;
                    spriteNum++;

                    if (spriteNum > 3) {
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

        if (isInvincible) {
            invincibleCounter++;

            if (invincibleCounter == 30) {
                isInvincible = false;
                invincibleCounter = 0;
            }
        }
    }

    private void attacking() {
        startAttack();

        attackSpriteCounter++;
        if (attackSpriteCounter > 4) {
            attackSpriteCounter = 0;
            attackSpriteNum++;
            if (currentStamina > 5) {
                currentStamina -= 5;
            }

            // Save the current worldX, worldY, solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // Adjust player's worldX and worldY for the attackArea
            switch (lastDirection) {
                case "right":
                    currentAttackArea = rightAttackArea;
                    worldX += currentAttackArea.width;
                    break;
                case "left":
                    currentAttackArea = leftAttackArea;
                    worldX -= currentAttackArea.width;
            }

            // AttackArea becomes solidArea
            solidArea.width = currentAttackArea.width;
            solidArea.height = currentAttackArea.height;

            // Check monster collision with the updated worldX, worldY and AttackArea
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monsters);
            damageMonster(monsterIndex);

            // After checking collision, restore the original values
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

            if (attackSpriteNum > currentAttackFrames.length) {
                attackSpriteNum = 1;
                isAttacking = false;
            }
        }
    }

    private void startAttack() {
        if (!isAttacking && currentStamina > 10) {
            attackType = (int) (Math.random() * 3) + 1;

            switch (attackType) {
                case 1:
                    if (lastDirection.equals("right")) {
                        currentAttackArea = rightAttackArea;
                        currentAttackFrames = rightAttackType1;
                    } else if (lastDirection.equals("left")) {
                        currentAttackFrames = leftAttackType1;
                        currentAttackArea = leftAttackArea;
                    } else {
                        currentAttackFrames = null;
                    }
                    break;
                case 2:
                    if (lastDirection.equals("right")) {
                        currentAttackArea = rightAttackArea;
                        currentAttackFrames = rightAttackType2;
                    } else if (lastDirection.equals("left")) {
                        currentAttackFrames = leftAttackType2;
                        currentAttackArea = leftAttackArea;
                    } else {
                        currentAttackFrames = null;
                    }
                    break;
                case 3:
                    if (lastDirection.equals("right")) {
                        currentAttackArea = rightAttackArea;
                        currentAttackFrames = rightAttackType3;
                    } else if (lastDirection.equals("left")) {
                        currentAttackArea = leftAttackArea;
                        currentAttackFrames = leftAttackType3;
                    } else {
                        currentAttackFrames = null;
                    }
                    break;
                default:
                    currentAttackFrames = null;
                    break;
            }

            isAttacking = true;
            attackSpriteNum = 1;
            attackSpriteCounter = 0;
        }
    }

    private void contactWithMonster(int monsterIndex) {
        if (monsterIndex != 999) {
            collisionOn = true;

            if (!isInvincible) {
                currentLife -= 10;
                isInvincible = true;
            }

        }
    }

    private void damageMonster(int monsterIndex) {
        if (monsterIndex != 999) {
            if (!gamePanel.monsters[monsterIndex].isInvincible) {
                gamePanel.monsters[monsterIndex].currentLife -= 15;
                gamePanel.monsters[monsterIndex].isInvincible = true;

                if (gamePanel.monsters[monsterIndex].currentLife <= 0) {
                    gamePanel.monsters[monsterIndex] = null;
                }
            }
        }
    }

    private void pickUpObject(int objectIndex) {
        if (objectIndex != 999) {
            String objectName = gamePanel.objects[objectIndex].name;

            if (objectName.equals("Crystal")) {
                gamePanel.userInterface.showMessage("You got a crystal");
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

        if (isAttacking) {
            return currentAttackFrames[attackSpriteNum - 1];
        }

        if (isClimbing) {
            return climb[spriteNum - 1];
        }

        if (isJumping) {
            return lastDirection.equals("right") ? rightJump[spriteNum - 1] : leftJump[spriteNum - 1];
        }

        if (isInvincible) {
            return lastDirection.equals("right") ? rightTakeHit[spriteNum - 1] : leftTakeHit[spriteNum - 1];
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

        // DRAWING HITBOX
        graphics2D.setColor(Color.RED);
        graphics2D.drawRect(
                this.screenX + this.solidArea.x,
                this.screenY + this.solidArea.y,
                this.solidArea.width,
                this.solidArea.height
        );

        // DRAWING SPRITE SIZE
        graphics2D.setColor(Color.GREEN);
        graphics2D.drawRect(screenX, screenY, 128, 64);

        // DRAWING ATTACK HITBOX
        graphics2D.setColor(Color.CYAN);
        graphics2D.drawRect(
                screenX + this.currentAttackArea.x,
                screenY + this.currentAttackArea.y,
                currentAttackArea.width,
                currentAttackArea.height
        );
//        graphics2D.drawRect(
//                screenX + this.rightAttackArea.x,
//                screenY + this.rightAttackArea.y,
//                rightAttackArea.width,
//                rightAttackArea.height
//        );
//        graphics2D.drawRect(
//                screenX + this.leftAttackArea.x,
//                screenY + this.leftAttackArea.y,
//                leftAttackArea.width,
//                leftAttackArea.height
//        );
        graphics2D.setComposite(originalComposite);
    }
}
