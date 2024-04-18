package view.setting;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.JDialog;
import view.GameFrame;

public class GameSettingDialog extends JDialog {

  BufferedImage theme;
  //Để truy cập vào game panel và menu panel (để chỉnh âm lượng)
  private GameFrame gf;

  private Container container;

  private AudioPanel audioPanel;
  public SpeedPanel speedPanel;

  public GameSettingDialog(GameFrame gf) {
    super(gf, "How to play", true);
    this.gf = gf;
    setTitle("GAME SETTING");
    setSize(500, 400);
    setResizable(false);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    initGameSetting();
  }

  private void initGameSetting() {
    container = getContentPane();
    setLayout(new GridLayout(2, 1));

    audioPanel = new AudioPanel(gf);
    speedPanel = new SpeedPanel(gf);

    container.add(audioPanel);
    container.add(speedPanel);
  }

  public void showGameSetting() {
    setLocationRelativeTo(gf);
    setVisible(true);
  }
}
