package main;

import entities.Entity;

public class CollisionChecker {

    GamePanel gamePanel;

    public CollisionChecker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
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

        System.out.println(bottomRow);
        System.out.println("Tile Left: " + tileNumLeft + " Collision: " + gamePanel.tileManager.tiles[tileNumLeft].collision);
        System.out.println("Tile Right: " + tileNumRight + " Collision: " + gamePanel.tileManager.tiles[tileNumRight].collision);

        return gamePanel.tileManager.tiles[tileNumLeft].collision || gamePanel.tileManager.tiles[tileNumRight].collision;
    }
}
