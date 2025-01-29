package main;

import objects.CrystalObject;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObject() {
        gamePanel.objects[0] = new CrystalObject(3 * gamePanel.tileSize, 10 * gamePanel.tileSize, false);
        gamePanel.objects[1] = new CrystalObject(4 * gamePanel.tileSize, 10 * gamePanel.tileSize, false);
    }
}
