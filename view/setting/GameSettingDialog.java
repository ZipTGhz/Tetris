package view.setting;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import view.GameFrame;

public class GameSettingDialog {

  BufferedImage theme;
  //Để truy cập vào game panel và menu panel (để chỉnh âm lượng)
  private GameFrame gf;

  private JDialog gameDialog;
  private Container container;

  private AudioPanel audioPanel;
  public SpeedPanel speedPanel;

  public GameSettingDialog(GameFrame gf) {
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
    speedPanel = new SpeedPanel(gf);
    
    container.add(audioPanel);
    container.add(speedPanel);
  }

  public void showGameSetting() {
    gameDialog.setLocationRelativeTo(gf);
    gameDialog.setVisible(true);
  }
}
