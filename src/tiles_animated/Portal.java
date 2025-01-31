package tiles_animated;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Portal extends AnimatedTiles {

    public Portal(GamePanel gamePanel, int col, int row) {
        super(gamePanel, col, row);

        isSolid = true;
        solidArea = new Rectangle();
        solidArea.x = 25;
        solidArea.y = 15;
        solidArea.width = 14;
        solidArea.height = 43;

        loadSprites();
    }

    private void loadSprites() {
        int k = 1;
        sprites = new BufferedImage[8];

        for (int i = 0; i < 8; i++) {
            try {
                sprites[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tileset/dynamic_tiles/portal" + k + ".png")));

                ++k;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
