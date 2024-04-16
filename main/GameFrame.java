package main;

import game.GamePanel;
import java.awt.CardLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import menu.GameDialog;
import menu.MainMenuPanel;

public class GameFrame extends JFrame {

  private CardLayout cardLayout = new CardLayout();
  private JPanel cardPanel = new JPanel(cardLayout);
  MainMenuPanel mp = null;
  GamePanel gp = null;
  
  public GameSetting gs;
  public BufferedImage originalImage;
  public GameDialog gd;

  GameFrame() {
    gd = new GameDialog(this);
    mp = new MainMenuPanel(this);
    gp = new GamePanel(this);
    gs = new GameSetting(this);
    cardPanel.add(mp, "mp");
    cardPanel.add(gp, "gp");
    try {
      originalImage = ImageIO.read(getClass().getResource("/img/tetris.jpg"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    setIconImage(originalImage);
    setTitle("TETRIS!");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
  }

  public void start() {
    displayMainMenu();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public void displayMainMenu() {
    mp.playMusic(0);
    gp.stopGame();
    cardLayout.show(cardPanel, "mp");
    mp.requestFocusInWindow();
    add(cardPanel);
    pack();
  }

  public void playGame() {
    mp.stopMusic();
    cardLayout.show(cardPanel, "gp");
    gp.requestFocusInWindow();
    gp.startGame();
    pack();
  }


}
