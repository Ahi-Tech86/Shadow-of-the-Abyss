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
    public Tile[] tiles;
    GamePanel gamePanel;
    public int[][] mapTilesNum;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[10];
        mapTilesNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap();
    }

    public void getTileImage() {
        try {
            tiles[0] = new Tile();
            tiles[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tileset/empty.png")));

            tiles[1] = new Tile();
            tiles[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tileset/mainlev_build0.png")));
            tiles[1].collision = true;

            tiles[2] = new Tile();
            tiles[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tileset/stone_brick.png")));
            tiles[2].collision = true;

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

            while (col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
                String line = br.readLine();

                while (col < gamePanel.maxWorldCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);

                    mapTilesNum[col][row] = num;
                    col++;
                }

                if (col == gamePanel.maxWorldCol) {
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

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {

            int tileNum = mapTilesNum[worldCol][worldRow];

            int worldX = worldCol * gamePanel.tileSize;
            int worldY = worldRow * gamePanel.tileSize;
            int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
            int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

            if (
                    worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY
            ) {
                graphics2D.drawImage(tiles[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            }

            worldCol++;

            if (worldCol == gamePanel.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
