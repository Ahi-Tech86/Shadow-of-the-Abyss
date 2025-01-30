package objects;

import main.GamePanel;

import java.awt.*;

public class CrystalObject extends SuperObject {

    public CrystalObject() {
        image = getSpriteImage("/objects/crystal.png");
    }

    public CrystalObject(int worldX, int worldY, boolean isSolid) {
        name = "Crystal";
        this.worldX = worldX;
        this.worldY = worldY;
        this.collision = isSolid;
        this.solidArea.x = 48;
        this.solidArea.y = 48;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.solidArea.width = 16;
        this.solidArea.height = 16;
        image = getSpriteImage("/objects/crystal.png");
    }

    @Override
    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        if (isOnScreen(gamePanel)) {
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;
            graphics2D.drawImage(image, screenX + 48, screenY + 48, 16, 16, null);
        }
    }
}
