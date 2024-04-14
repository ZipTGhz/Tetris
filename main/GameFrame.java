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
  public GameDialog gd;
  private MainMenuPanel mp;
  private GamePanel gp = null;
  private Sound sound = new Sound();
  public BufferedImage originalImage;

  GameFrame() {
    gd = new GameDialog(this);
    mp = new MainMenuPanel(this, gd);
    gp = new GamePanel(this);
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
    playMusic(0);
    gp.stopGame();
    cardLayout.show(cardPanel, "mp");
    mp.requestFocusInWindow();
    add(cardPanel);
    pack();
  }

  public void playGame() {
    stopMusic();
    cardLayout.show(cardPanel, "gp");
    gp.requestFocusInWindow();
    gp.startGame();
    pack();
  }

  private void playMusic(int index) {
    sound.setFile(index);
    sound.play();
    sound.loop();
  }

  private void stopMusic() {
    sound.stop();
  }
}
