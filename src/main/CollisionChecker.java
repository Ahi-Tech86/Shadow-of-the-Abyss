package main;

import entities.Entity;
import entities.Player;
import tiles.Ladder;

import java.awt.*;

public class CollisionChecker {

    GamePanel gamePanel;

    private final int LADDER_TILE_NUM = 3;
    private final int LADDER_WITH_BRICKS_TILE_NUM = 4;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX - entity.speed;
        int entityRightCol = entityRightWorldX + entity.speed;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        switch (entity.direction) {
            case "left":
                checkCollision(entity, (entityLeftCol) / gamePanel.tileSize, entityTopRow, entityBottomRow);
                break;
            case "right":
                checkCollision(entity, (entityRightCol) / gamePanel.tileSize, entityTopRow, entityBottomRow);
                break;
        }
    }

    public int getGroundY(Entity entity) {
        int entityCheckboxBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        return (entityCheckboxBottomWorldY / gamePanel.tileSize) - 1;
    }

    public boolean isOnGround(Entity entity) {
        return checkBottomCollision(entity, 1);
    }

    public boolean isCollisionDetected(Entity entity) {
        return checkBottomCollision(entity, 0);
    }

    public boolean isPlayerNearLadder(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        for (int row = entityTopRow; row <= entityBottomRow; row++) {
            for (int col = entityLeftCol; col <= entityRightCol; col++) {
                int tileNum = gamePanel.tileManager.mapTilesNum[col][row];
                if (tileNum == LADDER_TILE_NUM || tileNum == LADDER_WITH_BRICKS_TILE_NUM) {
                    if (checkLadderIntersection(entity, col, row, tileNum)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public int checkObject(Entity entity, boolean isPlayer) {
        int index = 999;

        for (int i = 0; i < gamePanel.objects.length; i++) {
            if (gamePanel.objects[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object's solid area position
                gamePanel.objects[i].solidArea.x = gamePanel.objects[i].worldX + gamePanel.objects[i].solidArea.x;
                gamePanel.objects[i].solidArea.y = gamePanel.objects[i].worldY + gamePanel.objects[i].solidArea.y;

                switch (entity.direction) {
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gamePanel.objects[i].solidArea)) {
                            if (gamePanel.objects[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (isPlayer) {
                                index = i;
                            }

                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gamePanel.objects[i].solidArea)) {
                            if (gamePanel.objects[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (isPlayer) {
                                index = i;
                            }
                        }
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gamePanel.objects[i].solidArea.x = gamePanel.objects[i].solidAreaDefaultX;
                gamePanel.objects[i].solidArea.y = gamePanel.objects[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    // FOR PLAYER
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                // Get object's solid area position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction) {
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(target[i].solidArea)) {
                            entity.collisionOn = true;
                            index = i;
                        }
                        break;
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }

        return index;
    }

    // FOR MONSTERS
    public void checkPlayer(Entity entity) {
        // Get entity's solid area position
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        // Get object's solid area position
        gamePanel.player.solidArea.x = gamePanel.player.worldX + gamePanel.player.solidArea.x;
        gamePanel.player.solidArea.y = gamePanel.player.worldY + gamePanel.player.solidArea.y;

        switch (entity.direction) {
            case "right":
                entity.solidArea.x += entity.speed;
                if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entity.solidArea.x -= entity.speed;
                if (entity.solidArea.intersects(gamePanel.player.solidArea)) {
                    entity.collisionOn = true;
                }
                break;
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;
        gamePanel.player.solidArea.x = gamePanel.player.solidAreaDefaultX;
        gamePanel.player.solidArea.y = gamePanel.player.solidAreaDefaultY;
    }

    private boolean checkBottomCollision(Entity entity, int yOffset) {
        int entityCheckboxBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height + yOffset;
        int entityCheckboxBottomWorldXLeft = entity.worldX + entity.solidArea.x;
        int entityCheckboxBottomWorldXRight = entity.worldX + entity.solidArea.x + entity.solidArea.width;

        // Index of the tile column corresponding to the lower left corner the entity's checkbox
        int leftCol = entityCheckboxBottomWorldXLeft / gamePanel.tileSize;
        // Index of the tile column corresponding to the lower right corner the entity's checkbox
        int rightCol = entityCheckboxBottomWorldXRight / gamePanel.tileSize;
        // Index of the tile column corresponding to the lower left bottom parts of the entity's checkbox
        int bottomRow = entityCheckboxBottomWorldY / gamePanel.tileSize;

        // The number of the corresponding to lower left corner of the entity's checkbox
        int tileNumLeft = gamePanel.tileManager.mapTilesNum[leftCol][bottomRow];
        // The number of the corresponding to lower right corner of the entity's checkbox
        int tileNumRight = gamePanel.tileManager.mapTilesNum[rightCol][bottomRow];

        return gamePanel.tileManager.tiles[tileNumLeft].collision || gamePanel.tileManager.tiles[tileNumRight].collision;
    }

    private void checkCollision(Entity entity, int col, int topRow, int bottomRow) {
        int tileNum1 = gamePanel.tileManager.mapTilesNum[col][topRow];
        int tileNum2 = gamePanel.tileManager.mapTilesNum[col][bottomRow];

        if (gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    private boolean checkLadderIntersection(Entity entity, int col, int row, int tileNum) {
        Ladder ladder = (Ladder) gamePanel.tileManager.tiles[tileNum];

        int ladderWorldX = col * gamePanel.tileSize;
        int ladderWorldY = row * gamePanel.tileSize;

        Rectangle ladderCheckbox = new Rectangle(
                ladderWorldX + ladder.solidArea.x,
                ladderWorldY + ladder.solidArea.y,
                ladder.solidArea.width,
                ladder.solidArea.height
        );

        Rectangle playerCheckbox = new Rectangle(
                entity.worldX + entity.solidArea.x,
                entity.worldY + entity.solidArea.y,
                entity.solidArea.width,
                entity.solidArea.height
        );

        return playerCheckbox.intersects(ladderCheckbox);
    }
}
