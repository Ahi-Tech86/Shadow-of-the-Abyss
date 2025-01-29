package main;

import entities.Entity;
import tiles.Ladder;

import java.awt.*;

public class CollisionChecker {

    GamePanel gamePanel;

    private final int ladderTileNum = 3;
    private final int ladderBrickTileNum = 4;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol;
        int entityRightCol;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTilesNum[entityLeftCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTilesNum[entityLeftCol][entityBottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tileNum1 = gamePanel.tileManager.mapTilesNum[entityRightCol][entityTopRow];
                tileNum2 = gamePanel.tileManager.mapTilesNum[entityRightCol][entityBottomRow];

                if (gamePanel.tileManager.tiles[tileNum1].collision || gamePanel.tileManager.tiles[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

    public int getGroundY(Entity entity) {
        int entityCheckboxBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        return (entityCheckboxBottomWorldY / gamePanel.tileSize) - 1;
    }

    public boolean isOnGround(Entity entity) {
        int entityCheckboxBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
        int futureY = entityCheckboxBottomWorldY + 1;

        int entityCheckboxBottomWorldXLeft = entity.worldX + entity.solidArea.x;
        int entityCheckboxBottomWorldXRight = entity.worldX + entity.solidArea.x + entity.solidArea.width;

        // Index of the tile column corresponding to the lower left corner the entity's checkbox
        int leftCol = entityCheckboxBottomWorldXLeft / gamePanel.tileSize;
        // Index of the tile column corresponding to the lower right corner the entity's checkbox
        int rightCol = entityCheckboxBottomWorldXRight / gamePanel.tileSize;
        // Index of the tile column corresponding to the lower left bottom parts of the entity's checkbox
        int bottomRow = futureY / gamePanel.tileSize;

        // The number of the corresponding to lower left corner of the entity's checkbox
        int tileNumLeft = gamePanel.tileManager.mapTilesNum[leftCol][bottomRow];
        // The number of the corresponding to lower right corner of the entity's checkbox
        int tileNumRight = gamePanel.tileManager.mapTilesNum[rightCol][bottomRow];

        return gamePanel.tileManager.tiles[tileNumLeft].collision || gamePanel.tileManager.tiles[tileNumRight].collision;
    }

    public boolean isCollisionDetected(Entity entity) {
        int entityCheckboxBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
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
                if (tileNum == ladderTileNum || tileNum == ladderBrickTileNum) {
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
                            entityLeftWorldX,
                            entityTopWorldY,
                            entity.solidArea.width,
                            entity.solidArea.height
                    );

                    if (playerCheckbox.intersects(ladderCheckbox)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
