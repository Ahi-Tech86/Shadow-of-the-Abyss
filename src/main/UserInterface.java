package main;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class UserInterface {

    GamePanel gamePanel;
    Graphics2D graphics2D;

    Font arial_40;
    Font maruMonica;
    public boolean messageOn = false;
    public String message = "";
    private int messageCounter = 120;
    public int commandNum = 1;

    // Colors
    private final Color gray = Color.GRAY;
    private final Color white = Color.WHITE;
    private final Color black = new Color(0, 0, 0);
    private final Color healthBarColor = new Color(141, 0, 0);
    private final Color staminaBarColor = new Color(0, 137, 93);

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);

        InputStream inputStream = getClass().getResourceAsStream("/fonts/MP16OSF.ttf");
        try {
            if (inputStream != null) {
                maruMonica = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(maruMonica);
        graphics2D.setFont(graphics2D.getFont().deriveFont(43F));
        graphics2D.setColor(white);

        if (gamePanel.gameState == gamePanel.TITLE_STATE) {
            drawTitleScreen();
        } else if (gamePanel.gameState == gamePanel.PLAY_STATE) {
            drawUserInterface(graphics2D);
        } else if (gamePanel.gameState == gamePanel.PAUSE_STATE) {
            drawPauseScreen();
            drawUserInterface(graphics2D);
        }

    }

    private void drawTitleScreen() {
        // BACKGROUND
        graphics2D.setColor(black);
        graphics2D.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

        // TITLE NAME
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Shadow of the Abyss";
        int x = getXforCenteredText(text);
        int y = gamePanel.tileSize * 3;

        // SHADOW
        graphics2D.setColor(gray);
        graphics2D.drawString(text, x + 5, y + 5);

        // FONT
        graphics2D.setColor(white);
        graphics2D.drawString(text, x, y);

        // MENU
        graphics2D.setFont(graphics2D.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize * 3;
        graphics2D.drawString(text, x, y);
        if (commandNum == 1) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "LOAD GAME";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
        if (commandNum == 2) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }

        text = "EXIT";
        x = getXforCenteredText(text);
        y += gamePanel.tileSize;
        graphics2D.drawString(text, x, y);
        if (commandNum == 3) {
            graphics2D.drawString(">", x - gamePanel.tileSize, y);
        }
    }

    private void drawUserInterface(Graphics2D graphics2D) {
        drawHealthBar(graphics2D);
        drawStaminaBar(graphics2D);
    }

    private void drawHealthBar(Graphics2D graphics2D) {
        int x = 25;
        int y = 25;
        int width = 300;
        int height = 16;
        int arcWidth = 10;
        int arcHeight = 10;

        // DRAW BACKGROUND
        graphics2D.setColor(gray);
        //graphics2D.fillRect(x, y, width, height);
        graphics2D.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

        // DRAW HEALTH BAR
        graphics2D.setColor(healthBarColor);
        int healthWidth = (int) ((double) gamePanel.player.currentLife / gamePanel.player.maxLife * width);
        //graphics2D.fillRect(x, y, healthWidth, height);
        graphics2D.fillRoundRect(x, y, healthWidth, height, arcWidth, arcHeight);

        // DRAW BORDER
        graphics2D.setColor(black);
        //graphics2D.drawRect(x, y, width, height);
        graphics2D.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    private void drawStaminaBar(Graphics2D graphics2D) {
        int x = 25;
        int y = 57;
        int width = 300;
        int height = 16;
        int arcWidth = 10;
        int arcHeight = 10;

        // DRAW BACKGROUND
        graphics2D.setColor(gray);
        //graphics2D.fillRect(x, y, width, height);
        graphics2D.fillRoundRect(x, y, width, height, arcWidth, arcHeight);

        // DRAW HEALTH BAR
        graphics2D.setColor(staminaBarColor);
        int staminaWidth = (int) ((double) gamePanel.player.currentStamina / gamePanel.player.maxStamina * width);
        //graphics2D.fillRect(x, y, staminaWidth, height);
        graphics2D.fillRoundRect(x, y, staminaWidth, height, arcWidth, arcHeight);

        // DRAW BORDER
        graphics2D.setColor(black);
        //graphics2D.drawRect(x, y, width, height);
        graphics2D.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    private void drawPauseScreen() {
        String text = "PAUSED";

        int x = getXforCenteredText(text);
        int y = gamePanel.screenHeight / 2;

        graphics2D.drawString(text, x, y);
    }

    private int getXforCenteredText(String text) {
        int length = (int) graphics2D.getFontMetrics().getStringBounds(text, graphics2D).getWidth();
        return gamePanel.screenWidth / 2 - length / 2;
    }

    private void drawSubWindow(int x, int y, int width, int height) {
        Color color = new Color(0, 0, 0, 200);
        graphics2D.setColor(color);
        graphics2D.fillRoundRect(x, y, width, height, 35, 35);

        color = white;
        graphics2D.setStroke(new BasicStroke(5));
        graphics2D.fillRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }
}
