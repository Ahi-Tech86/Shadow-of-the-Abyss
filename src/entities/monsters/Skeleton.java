package entities.monsters;

import entities.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Skeleton extends Entity {

    public Skeleton(GamePanel gamePanel) {
        super(gamePanel);
        random = new Random();

        speed = 2;
        direction = "right";
        lastDirection = "right";

        // CHECKBOX SETUP
        solidArea = new Rectangle();
        solidArea.x = 15;
        solidArea.y = 15;
        solidArea.width = 33;
        solidArea.height = 48;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        maxSpriteNumber = 4;

        maxLife = 75;
        maxStamina = 75;
        currentLife = maxLife;
        currentStamina = maxStamina;

        getSkeletonSprites();
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

        System.out.println(collisionOn);
    }

    private void getSkeletonSprites() {
        int k = 1;

        leftRunning = new BufferedImage[4];
        rightRunning = new BufferedImage[4];

        for (int i = 0; i < 4; i++) {
            leftRunning[i] = getSpriteImage("/monsters/Skeleton/walk/walk_left" + k + ".png");
            rightRunning[i] = getSpriteImage("/monsters/Skeleton/walk/walk_right" + k + ".png");

            ++k;
        }
    }
}
