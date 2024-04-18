package view;

import controller.MouseHandle;
import controller.Sound;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel {

  public BufferedImage image[] = new BufferedImage[10];
  public BufferedImage playButton, infoButton, optButton, aboutButton;

  private final int SIZE = 32;
  private final int screenWidth = SIZE * 16, screenHeight = SIZE * 20;
  public int playX, playY;
  public int infoX, infoY;
  public int optX, optY;
  public int aboutX, aboutY;

  GameFrame gf;
  public Sound music = new Sound();

  public MainMenuPanel(GameFrame gf) {
    this.gf = gf;
    setPreferredSize(new Dimension(screenWidth, screenHeight));
    addMouseListener(new MouseHandle(gf, this));
    setTheme();
  }

  private void setTheme() {
    try {
      image[0] =
        ImageIO.read(getClass().getResource("/resources/images/theme.jpg"));

      image[1] =
        ImageIO.read(
          getClass().getResource("/resources/images/style01/play01.png")
        );
      image[2] =
        ImageIO.read(
          getClass().getResource("/resources/images/style01/play03.png")
        );
      playButton = image[1];
      playX = screenWidth / 2 - playButton.getWidth() / 2;
      playY = screenHeight / 2 - playButton.getHeight() / 2 - SIZE * 4;

      image[3] =
        ImageIO.read(
          getClass().getResource("/resources/images/style01/information01.png")
        );
      image[4] =
        ImageIO.read(
          getClass().getResource("/resources/images/style01/information03.png")
        );
      infoButton = image[3];
      infoX = screenWidth / 2 - infoButton.getWidth() / 2;
      infoY = screenHeight / 2 - infoButton.getHeight() / 2 - SIZE * 1;

      image[5] =
        ImageIO.read(
          getClass().getResource("/resources/images/style01/option01.png")
        );
      image[6] =
        ImageIO.read(
          getClass().getResource("/resources/images/style01/option03.png")
        );
      optButton = image[5];
      optX = screenWidth / 2 - optButton.getWidth() / 2;
      optY = screenHeight / 2 - optButton.getHeight() / 2 + SIZE * 2;

      image[7] =
        ImageIO.read(
          getClass().getResource("/resources/images/style01/about01.png")
        );
      image[8] =
        ImageIO.read(
          getClass().getResource("/resources/images/style01/about03.png")
        );
      aboutButton = image[7];
      aboutX = screenWidth / 2 - aboutButton.getWidth() / 2;
      aboutY = screenHeight / 2 - aboutButton.getHeight() / 2 + SIZE * 5;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(image[0], 0, 0, SIZE * 16, SIZE * 20, null);
    g.drawImage(playButton, playX, playY, null);
    g.drawImage(infoButton, infoX, infoY, null);
    g.drawImage(optButton, optX, optY, null);
    g.drawImage(aboutButton, aboutX, aboutY, null);
    g.dispose();
  }

  public void playMusic(int index) {
    music.setFile(index);
    music.play();
    music.loop();
  }

  public void stopMusic() {
    music.stop();
  }
}
