package view;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;

public class GameSetting {

  BufferedImage theme;
  //Để truy cập vào game panel và menu panel (để chỉnh âm lượng)
  private GameFrame gf;

  private JDialog gameDialog;
  private Container container;

  private AudioPanel audioPanel;

  public GameSetting(GameFrame gf) {
    this.gf = gf;
    gameDialog = new JDialog(gf, "How to play", true);
    gameDialog.setSize(500, 400);
    gameDialog.setResizable(false);
    gameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    initGameSetting();
  }

  private void initGameSetting() {
    container = gameDialog.getContentPane();
    gameDialog.setLayout(new GridLayout(2, 1));
    audioPanel = new AudioPanel(gf);
    container.add(audioPanel);
  }

  public void showGameSetting() {
    gameDialog.setLocationRelativeTo(gf);
    gameDialog.setVisible(true);
  }
}
