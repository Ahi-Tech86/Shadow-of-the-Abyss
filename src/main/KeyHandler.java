package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gamePanel;

    public boolean leftPressed, rightPressed, spacePressed, fPressed, ePressed;

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

        // TITLE STATE
        if (gamePanel.gameState == gamePanel.TITLE_STATE) {
            if (code == KeyEvent.VK_W) {
                gamePanel.userInterface.commandNum--;
                if (gamePanel.userInterface.commandNum < 1) {
                    gamePanel.userInterface.commandNum = 3;
                }
            }
            if (code == KeyEvent.VK_S) {
                gamePanel.userInterface.commandNum++;
                if (gamePanel.userInterface.commandNum > 3) {
                    gamePanel.userInterface.commandNum = 1;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gamePanel.userInterface.commandNum == 1) {
                    gamePanel.gameState = gamePanel.PLAY_STATE;
                    //gamePanel.playMusic(0);
                } else if (gamePanel.userInterface.commandNum == 2) {
                    // later
                } else if (gamePanel.userInterface.commandNum == 3) {
                    System.exit(0);
                }
            }
        }

        // PLAY STATE
        if (gamePanel.gameState == gamePanel.PLAY_STATE) {
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
            if (code == KeyEvent.VK_E) {
                ePressed = true;
            }
            if (code == KeyEvent.VK_P) {
                gamePanel.gameState = gamePanel.PAUSE_STATE;
            }
            if (code == KeyEvent.VK_T) {
                checkDrawTime = !checkDrawTime;
            }
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
        if (code == KeyEvent.VK_E) {
            ePressed = false;
        }
        if (code == KeyEvent.VK_F) {
            fPressed = false;
        }
    }
}
