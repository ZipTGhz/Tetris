package menu;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import main.GameFrame;
import main.Sound;

public class MouseHandle implements MouseListener {

  MainMenuPanel mp;
  GameFrame gf;
  Sound se = new Sound();

  public MouseHandle(GameFrame gf, MainMenuPanel mp) {
    this.gf = gf;
    this.mp = mp;
  }

  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    int click = e.getButton();
    if (click == MouseEvent.BUTTON1) {
      playSE(12);
      int x = e.getX(), y = e.getY();
      if (
        x >= mp.playX &&
        y >= mp.playY &&
        x <= mp.playX + mp.playButton.getWidth() &&
        y <= mp.playY + mp.playButton.getHeight()
      ) {
        mp.playButton = mp.image[2];
      } else if (
        x >= mp.htpX &&
        y >= mp.htpY &&
        x <= mp.htpX + mp.htpButton.getWidth() &&
        y <= mp.htpY + mp.htpButton.getHeight()
      ) {
        mp.htpButton = mp.image[4];
      } else if (
        x >= mp.exitX &&
        y >= mp.exitY &&
        x <= mp.exitX + mp.exitButton.getWidth() &&
        y <= mp.exitY + mp.exitButton.getHeight()
      ) {
        mp.exitButton = mp.image[6];
      }
      mp.repaint();
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    int click = e.getButton();
    if (click == MouseEvent.BUTTON1) {
      int x = e.getX(), y = e.getY();
      if (
        x >= mp.playX &&
        y >= mp.playY &&
        x <= mp.playX + mp.playButton.getWidth() &&
        y <= mp.playY + mp.playButton.getHeight()
      ) {
        mp.playButton = mp.image[1];
        gf.playGame();
      } else if (
        x >= mp.htpX &&
        y >= mp.htpY &&
        x <= mp.htpX + mp.htpButton.getWidth() &&
        y <= mp.htpY + mp.htpButton.getHeight()
      ) {
        mp.htpButton = mp.image[3];
        mp.gd.howToPlayDialog();
      } else if (
        x >= mp.exitX &&
        y >= mp.exitY &&
        x <= mp.exitX + mp.exitButton.getWidth() &&
        y <= mp.exitY + mp.exitButton.getHeight()
      ) {
        mp.exitButton = mp.image[5];
        mp.gd.exitDialog();
      }
      mp.repaint();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {}

  @Override
  public void mouseExited(MouseEvent e) {}

  private void playSE(int index) {
    se.setFile(index);
    se.play();
  }
}
