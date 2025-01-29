package main;

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
        try {
            SpriteSheet spriteSheet = new SpriteSheet("/character/climbing/originalAssets/Climb.png");

            int spriteWidth = 128;
            int spriteHeight = 64;

            BufferedImage sprite1 = spriteSheet.getSprite(0, 0, spriteWidth, spriteHeight);
            BufferedImage sprite2 = spriteSheet.getSprite(128, 0, spriteWidth, spriteHeight);
            BufferedImage sprite3 = spriteSheet.getSprite(0, 64, spriteWidth, spriteHeight);
            BufferedImage sprite4 = spriteSheet.getSprite(128, 64, spriteWidth, spriteHeight);
            BufferedImage sprite5 = spriteSheet.getSprite(0, 128, spriteWidth, spriteHeight);
            BufferedImage sprite6 = spriteSheet.getSprite(128, 128, spriteWidth, spriteHeight);
            BufferedImage sprite7 = spriteSheet.getSprite(0, 192, spriteWidth, spriteHeight);
            BufferedImage sprite8 = spriteSheet.getSprite(128, 192, spriteWidth, spriteHeight);

            String name = "climb";
            int i = 1;

            ImageIO.write(sprite1, "png", new File("" + name + i++ + ".png"));
            ImageIO.write(sprite2, "png", new File("" + name + i++ + ".png"));
            ImageIO.write(sprite3, "png", new File("" + name + i++ + ".png"));
            ImageIO.write(sprite4, "png", new File("" + name + i++ + ".png"));
            ImageIO.write(sprite5, "png", new File("" + name + i++ + ".png"));
            ImageIO.write(sprite6, "png", new File("" + name + i++ + ".png"));
//            ImageIO.write(sprite7, "png", new File("" + name + i++ + ".png"));
//            ImageIO.write(sprite8, "png", new File("" + name + i++ + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
