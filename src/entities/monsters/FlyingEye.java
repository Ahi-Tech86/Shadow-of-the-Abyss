package entities.monsters;

import entities.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class FlyingEye extends Entity {

    public FlyingEye(GamePanel gamePanel) {
        super(gamePanel);
        random = new Random();

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
        int k = 1;

        leftRunning = new BufferedImage[8];
        rightRunning = new BufferedImage[8];

        for (int i = 0; i < 8; i++) {
            leftRunning[i] = getSpriteImage("/monsters/FlyingEye/flight/flight_left" + k + ".png");
            rightRunning[i] = getSpriteImage("/monsters/FlyingEye/flight/flight_right" + k + ".png");

            ++k;
        }
    }
}
