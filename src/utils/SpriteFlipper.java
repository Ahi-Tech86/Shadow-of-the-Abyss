package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SpriteFlipper {

    public static void main(String[] args) {
        String[] spritesPath = {
                "/character/takeHit/take_hit_right1.png",
                "/character/takeHit/take_hit_right2.png",
                "/character/takeHit/take_hit_right3.png",
        };

        int i = 1;
        for (String path : spritesPath) {
            try {
                BufferedImage originalImage = ImageIO.read(
                        Objects.requireNonNull(SpriteFlipper.class.getResourceAsStream(path))
                );
                BufferedImage flippedImage = flipImageHorizontally(originalImage);
                ImageIO.write(flippedImage, "png", new File("take_hit_left" + i++ + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static BufferedImage flipImageHorizontally(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage flippedImage = new BufferedImage(width, height, originalImage.getType());

        Graphics2D graphics2D = flippedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, width, height, width, 0, 0, height, null);
        graphics2D.dispose();

        return flippedImage;
    }
}
