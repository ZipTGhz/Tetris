package view;

import controller.CustomWindowListener;
import view.setting.GameSettingDialog;

import java.awt.CardLayout;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {

  private CardLayout cardLayout = new CardLayout();
  private JPanel cardPanel = new JPanel(cardLayout);
  public MainMenuPanel mp = null;
  public GamePanel gp = null;

  public GameSettingDialog gs;
  public BufferedImage originalImage;
  public GameDialog gd;

  public GameFrame() {
    gd = new GameDialog(this);
    mp = new MainMenuPanel(this);
    gp = new GamePanel(this);
    gs = new GameSettingDialog(this);
    cardPanel.add(mp, "mp");
    cardPanel.add(gp, "gp");
    try {
      originalImage =
        ImageIO.read(getClass().getResource("/resources/images/tetris.jpg"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    setIconImage(originalImage);
    setTitle("TETRIS!");
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new CustomWindowListener(gd));
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
