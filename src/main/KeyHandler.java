package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean leftPressed, rightPressed, spacePressed, fPressed;

    // DEBUG
    public boolean checkDrawTime;

    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // USELESS
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();

        if (code == KeyEvent.VK_A) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }
        if (code == KeyEvent.VK_F) {
            fPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            if (gamePanel.gameState == gamePanel.PLAY_STATE) {
                gamePanel.gameState = gamePanel.PAUSE_STATE;
            } else if (gamePanel.gameState == gamePanel.PAUSE_STATE) {
                gamePanel.gameState = gamePanel.PLAY_STATE;
            }

        }
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int code = keyEvent.getKeyCode();

        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }
        if (code == KeyEvent.VK_F) {
            fPressed = false;
        }
    }
}
