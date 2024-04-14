package menu;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import main.GameFrame;

public class MainMenuPanel extends JPanel {

  BufferedImage image[] = new BufferedImage[10];
  BufferedImage playButton, htpButton, exitButton;

  private final int SIZE = 32;
  private final int screenWidth = SIZE * 16, screenHeight = SIZE * 20;
  int playX, playY;
  int htpX, htpY;
  int exitX, exitY;

  GameDialog gd;
  GameFrame gf;

  public MainMenuPanel(GameFrame gf, GameDialog gd) {
    this.gf = gf;
    this.gd = gd;
    setPreferredSize(new Dimension(screenWidth, screenHeight));
    addMouseListener(new MouseHandle(gf, this));
    setTheme();
  }

  private void setTheme() {
    try {
      image[0] = ImageIO.read(getClass().getResource("/img/theme.jpg"));

      image[1] = ImageIO.read(getClass().getResource("/img/play1.png"));
      image[2] = ImageIO.read(getClass().getResource("/img/play2.png"));
      playButton = image[1];
      playX = screenWidth / 2 - playButton.getWidth() / 2;
      playY = screenHeight / 2 - playButton.getHeight() / 2 - SIZE * 4;

      image[3] = ImageIO.read(getClass().getResource("/img/htp1.png"));
      image[4] = ImageIO.read(getClass().getResource("/img/htp2.png"));
      htpButton = image[3];
      htpX = screenWidth / 2 - htpButton.getWidth() / 2;
      htpY = screenHeight / 2 - htpButton.getHeight() / 2 - SIZE * 1;

      image[5] = ImageIO.read(getClass().getResource("/img/exit1.png"));
      image[6] = ImageIO.read(getClass().getResource("/img/exit2.png"));
      exitButton = image[5];
      exitX = screenWidth / 2 - exitButton.getWidth() / 2;
      exitY = screenHeight / 2 - exitButton.getHeight() / 2 + SIZE * 2;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(image[0], 0, 0, SIZE * 16, SIZE * 20, null);
    g.drawImage(playButton, playX, playY, null);
    g.drawImage(htpButton, htpX, htpY, null);
    g.drawImage(exitButton, exitX, exitY, null);
  }
}
