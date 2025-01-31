package tiles_animated;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimatedTiles {

    GamePanel gamePanel;

    public boolean isSolid;
    public int worldX, worldY;
    public Rectangle solidArea;
    public BufferedImage[] sprites;

    // VARIABLES FOR CHANGING SPRITES
    public int spriteNum = 1;
    public int spriteCounter = 0;

    public AnimatedTiles(GamePanel gamePanel, int col, int row) {
        this.gamePanel = gamePanel;
        this.worldX = col * gamePanel.tileSize;
        this.worldY = row * gamePanel.tileSize;
    }

    public void update() {
        spriteCounter++;

        if (spriteCounter > 5) {
            spriteCounter = 0;
            spriteNum++;

            if (spriteNum > 8) {
                spriteNum = 1;
            }
        }
    }

    public void draw(Graphics2D graphics2D) {
        BufferedImage image = null;

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (
                worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                        worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                        worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                        worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
        ) {
            image = sprites[spriteNum - 1];

            graphics2D.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            //graphics2D.setColor(Color.CYAN);
            //graphics2D.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
        }
    }
}
