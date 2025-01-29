package objects;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class SuperObject {
    public String name;
    public int worldX, worldY;
    public BufferedImage image;
    public boolean collision = false;

    protected BufferedImage getSpriteImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D graphics2D, GamePanel gamePanel) {
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (
                worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                        worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                        worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                        worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
        ) {
            graphics2D.drawImage(image, screenX + 32 + 14, screenY + 32 + 14, 16, 16, null);
        }
    }
}
