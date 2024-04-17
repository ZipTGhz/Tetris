package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import view.GameFrame;
import view.MainMenuPanel;

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
        x >= mp.infoX &&
        y >= mp.infoY &&
        x <= mp.infoX + mp.infoButton.getWidth() &&
        y <= mp.infoY + mp.infoButton.getHeight()
      ) {
        mp.infoButton = mp.image[4];
      } else if (
        x >= mp.optX &&
        y >= mp.optY &&
        x <= mp.optX + mp.optButton.getWidth() &&
        y <= mp.optY + mp.optButton.getHeight()
      ) {
        mp.optButton = mp.image[6];
      } else if (
        x >= mp.aboutX &&
        y >= mp.aboutY &&
        x <= mp.aboutX + mp.aboutButton.getWidth() &&
        y <= mp.aboutY + mp.aboutButton.getHeight()
      ) {
        mp.aboutButton = mp.image[8];
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
        gf.playGame();
      } else if (
        x >= mp.infoX &&
        y >= mp.infoY &&
        x <= mp.infoX + mp.infoButton.getWidth() &&
        y <= mp.infoY + mp.infoButton.getHeight()
      ) {
        gf.gd.howToPlayDialog();
      } else if (
        x >= mp.optX &&
        y >= mp.optY &&
        x <= mp.optX + mp.optButton.getWidth() &&
        y <= mp.optY + mp.optButton.getHeight()
      ) {
        gf.gs.showGameSetting();
      } else if (
        x >= mp.aboutX &&
        y >= mp.aboutY &&
        x <= mp.aboutX + mp.aboutButton.getWidth() &&
        y <= mp.aboutY + mp.aboutButton.getHeight()
      ) {
        gf.gd.exitDialog();
      }
      mp.playButton = mp.image[1];
      mp.infoButton = mp.image[3];
      mp.optButton = mp.image[5];
      mp.aboutButton = mp.image[7];
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
