package main;

import java.awt.*;

public class UserInterface {

    GamePanel gamePanel;
    Graphics2D graphics2D;

    Font arial_40;
    Color white = Color.WHITE;
    public boolean messageOn = false;
    public String message = "";
    private int messageCounter = 120;

    public UserInterface(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D graphics2D) {
        this.graphics2D = graphics2D;

        graphics2D.setFont(arial_40);
        graphics2D.setColor(white);
        
        if (gamePanel.gameState == gamePanel.PLAY_STATE) {
            
        } else if (gamePanel.gameState == gamePanel.PAUSE_STATE) {
            drawPauseScreen();
        }
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
}
