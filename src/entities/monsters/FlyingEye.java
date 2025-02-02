package entities.monsters;

import entities.Entity;
import main.GamePanel;

import java.awt.*;
import java.util.Random;

public class FlyingEye extends Entity {

    public FlyingEye(GamePanel gamePanel) {
        super(gamePanel);
        random = new Random();

        type = 1;
        speed = 2;
        direction = "right";
        lastDirection = "right";

        // CHECKBOX SETUP
        solidArea = new Rectangle();
        solidArea.x = 9;
        solidArea.y = 24;
        solidArea.width = 40;
        solidArea.height = 25;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        maxSpriteNumbers = 8;
        maxSpriteNumbersForTakingHit = 4;
        maxSpriteNumbersForDeath = 4;

        maxLife = 50;
        maxStamina = 50;
        currentLife = maxLife;
        currentStamina = maxStamina;

        getFlyingEyeSprites();
    }

    public void setAction() {
        actionLockCounter++;

        int i = random.nextInt(100) + 1;

        if (actionLockCounter == 150) {
            if (i <= 49) {
                direction = "left";
            } else {
                direction = "right";
            }

            actionLockCounter = 0;
        }
    }

    private void getFlyingEyeSprites() {
        byte DEATH_FRAMES = 4;
        byte FLIGHT_FRAMES = 8;
        byte ATTACK_FRAMES = 8;
        byte TAKE_HIT_FRAMES = 4;

        leftDeath = loadSprites("/monsters/FlyingEye/death/death_left", DEATH_FRAMES);
        rightDeath = loadSprites("/monsters/FlyingEye/death/death_right", DEATH_FRAMES);
        leftRunning = loadSprites("/monsters/FlyingEye/flight/flight_left", FLIGHT_FRAMES);
        rightRunning = loadSprites("/monsters/FlyingEye/flight/flight_right", FLIGHT_FRAMES);
        leftTakeHit = loadSprites("/monsters/FlyingEye/takeHit/take_hit_left", TAKE_HIT_FRAMES);
        rightTakeHit = loadSprites("/monsters/FlyingEye/takeHit/take_hit_right", TAKE_HIT_FRAMES);
        leftAttack = loadSprites("/monsters/FlyingEye/attack/attack_left", ATTACK_FRAMES);
        rightAttack = loadSprites("/monsters/FlyingEye/attack/attack_right", ATTACK_FRAMES);
    }
}
