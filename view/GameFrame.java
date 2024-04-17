package view;

import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameFrame extends JFrame {

  private CardLayout cardLayout = new CardLayout();
  private JPanel cardPanel = new JPanel(cardLayout);
  public MainMenuPanel mp = null;
  public GamePanel gp = null;

  public GameSetting gs;
  public BufferedImage originalImage;
  public GameDialog gd;

  public GameFrame() {
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
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(
        new WindowListener() {
          @Override
          public void windowOpened(WindowEvent e) {}

          @Override
          public void windowClosing(WindowEvent e) {
            if (gd.exitDialog() == true) dispose();
          }

          @Override
          public void windowClosed(WindowEvent e) {}

          @Override
          public void windowIconified(WindowEvent e) {}

          @Override
          public void windowDeiconified(WindowEvent e) {}

          @Override
          public void windowActivated(WindowEvent e) {}

          @Override
          public void windowDeactivated(WindowEvent e) {}
        }
      );
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
