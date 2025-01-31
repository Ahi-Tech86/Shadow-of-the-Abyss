package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SpriteSheet {
    private BufferedImage spriteSheet;

    public SpriteSheet(String path) {
        try {
            this.spriteSheet = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(int x, int y, int width, int height) {
        return spriteSheet.getSubimage(x, y, width, height);
    }

    public static void main(String[] args) {
        extractTileFromSprite();
    }

    private static void sliceSpriteSheet() {
        try {
            SpriteSheet spriteSheet = new SpriteSheet("/monsters/Skeleton/Walk.png");

            int spriteWidth = 150;
            int spriteHeight = 64;

            BufferedImage sprite1 = spriteSheet.getSprite(0, 0, spriteWidth, spriteHeight);
            BufferedImage sprite2 = spriteSheet.getSprite(150, 0, spriteWidth, spriteHeight);
            BufferedImage sprite3 = spriteSheet.getSprite(300, 0, spriteWidth, spriteHeight);
            BufferedImage sprite4 = spriteSheet.getSprite(450, 0, spriteWidth, spriteHeight);
//            BufferedImage sprite5 = spriteSheet.getSprite(512, 0, spriteWidth, spriteHeight);
//            BufferedImage sprite6 = spriteSheet.getSprite(640, 0, spriteWidth, spriteHeight);
//            BufferedImage sprite7 = spriteSheet.getSprite(768, 0, spriteWidth, spriteHeight);
//            BufferedImage sprite8 = spriteSheet.getSprite(896, 0, spriteWidth, spriteHeight);

            String name = "walk_right";

            int i = 1;

            ImageIO.write(sprite1, "png", new File("" + name + i++ + ".png"));
            ImageIO.write(sprite2, "png", new File("" + name + i++ + ".png"));
            ImageIO.write(sprite3, "png", new File("" + name + i++ + ".png"));
            ImageIO.write(sprite4, "png", new File("" + name + i++ + ".png"));
//            ImageIO.write(sprite5, "png", new File("" + name + i++ + ".png"));
//            ImageIO.write(sprite6, "png", new File("" + name + i++ + ".png"));
//            ImageIO.write(sprite7, "png", new File("" + name + i++ + ".png"));
//            ImageIO.write(sprite8, "png", new File("" + name + i++ + ".png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void extractTileFromSprite() {
        String[] spritesPath = {
                "/monsters/zraw/walk_right1.png",
                "/monsters/zraw/walk_right2.png",
                "/monsters/zraw/walk_right3.png",
                "/monsters/zraw/walk_right4.png",
        };

        int i = 1;

        for (String path : spritesPath) {
            try {
                BufferedImage sprite = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
                BufferedImage extractedSprite = sprite.getSubimage(43, 0, 64, 64);
                ImageIO.write(extractedSprite, "png", new File("walk_right" + i++ + ".png"));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void extractTile() {
        String path = "/tileset/static_tiles/assets/castle_stones.png";

        try {
            BufferedImage sprite = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
            BufferedImage extractedSprite = sprite.getSubimage(64, 64, 64, 64);
            ImageIO.write(extractedSprite, "png", new File("castle_brick.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
