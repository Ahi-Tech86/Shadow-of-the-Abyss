package tiles;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {
    Tile[] tiles;
    GamePanel gamePanel;
    int[][] mapTilesNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[10];
        mapTilesNum = new int[gamePanel.maxScreenCol][gamePanel.maxScreenRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tileset/empty.png")));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tileset/mainlev_build0.png")));

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tileset/stone_brick.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            InputStream is = getClass().getResourceAsStream("/map/map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {
                String line = br.readLine();

                while (col < gamePanel.maxScreenCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTilesNum[col][row] = num;
                    col++;
                }

                if (col == gamePanel.maxScreenCol) {
                    col = 0;
                    row++;
                }
            }

            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D graphics2D) {
//        int x = 0;
//
//        graphics2D.drawImage(tiles[0].image, x, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*2, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*3, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*4, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*5, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*6, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*7, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*8, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*9, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*10, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*11, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*12, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*13, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*14, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[0].image, x+64*15, 704, gamePanel.tileSize, gamePanel.tileSize, null);
//        graphics2D.drawImage(tiles[1].image, x+64*7, 704 - 48, 48, 48, null);

        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gamePanel.maxScreenCol && row < gamePanel.maxScreenRow) {

            int tileNum = mapTilesNum[col][row];

            graphics2D.drawImage(tiles[tileNum].image, x, y, gamePanel.tileSize, gamePanel.tileSize, null);

            col++;
            x += gamePanel.tileSize;

            if (col == gamePanel.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gamePanel.tileSize;
            }
        }
    }
}
