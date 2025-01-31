package main;

import entities.monsters.FlyingEye;
import entities.monsters.Skeleton;
import objects.CrystalObject;
import objects.SkullObject;
import tiles_animated.Portal;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.objects[0] = new CrystalObject(3 * gamePanel.tileSize, 10 * gamePanel.tileSize, false);
        gamePanel.objects[1] = new CrystalObject(4 * gamePanel.tileSize, 10 * gamePanel.tileSize, false);
        gamePanel.objects[2] = new SkullObject(4 * gamePanel.tileSize, 7 * gamePanel.tileSize, false);
    }

    public void setMonsters() {
        gamePanel.monsters[0] = new FlyingEye(gamePanel);
        gamePanel.monsters[0].worldX = 9 * gamePanel.tileSize;
        gamePanel.monsters[0].worldY = 7 * gamePanel.tileSize;

        gamePanel.monsters[1] = new FlyingEye(gamePanel);
        gamePanel.monsters[1].worldX = 10 * gamePanel.tileSize;
        gamePanel.monsters[1].worldY = 10 * gamePanel.tileSize;

        gamePanel.monsters[2] = new FlyingEye(gamePanel);
        gamePanel.monsters[2].worldX = 11 * gamePanel.tileSize;
        gamePanel.monsters[2].worldY = 10 * gamePanel.tileSize;

        gamePanel.monsters[3] = new Skeleton(gamePanel);
        gamePanel.monsters[3].worldX = 4 * gamePanel.tileSize;
        gamePanel.monsters[3].worldY = 10 * gamePanel.tileSize;
    }

    public void setAnimatedTiles() {
        gamePanel.animatedTiles[0] = new Portal(gamePanel, 0, 10);
        gamePanel.animatedTiles[0].worldX = 0 * gamePanel.tileSize + 25;
        gamePanel.animatedTiles[0].worldY = 10 * gamePanel.tileSize;
    }
}
