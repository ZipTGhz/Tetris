package main;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.sound.sampled.FloatControl;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GameSetting {

  //Để truy cập vào game panel và menu panel (để chỉnh âm lượng)
  private GameFrame gf;

  private JDialog gameDialog;
  private Container container;
  private JLabel menu_musicLabel, game_musicLabel, seLabel;
  private GameSlider menu_musicSlider, game_musicSlider, seSlider;

  private JPanel jp1;

  public GameSetting(GameFrame gf) {
    this.gf = gf;
    gameDialog = new JDialog(gf, "How to play", true);
    gameDialog.setSize(500, 400);
    gameDialog.setResizable(false);
    gameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    initGameSetting();
  }

  public static void main(String[] args) {
    GameSetting gs = new GameSetting(null);
    gs.showGameSetting();
  }

  private void initGameSetting() {
    container = gameDialog.getContentPane();
    gameDialog.setLayout(new GridLayout(2, 1));

    menu_musicLabel = new JLabel("MUSIC (MENU)");
    game_musicLabel = new JLabel("MUSIC (GAME)");
    seLabel = new JLabel("SOUND EFFECT");

    menu_musicSlider = new GameSlider(0, 100, 100);
    game_musicSlider = new GameSlider(0, 100, 100);
    seSlider = new GameSlider(0, 100, 100);

    GridBagConstraints cPanel = new GridBagConstraints();
    jp1 = new JPanel(new GridBagLayout());
    cPanel.gridx = 0;
    cPanel.gridy = 0;
    jp1.add(menu_musicLabel, cPanel);
    cPanel.gridy = 1;
    jp1.add(game_musicLabel, cPanel);
    cPanel.gridy = 2;
    jp1.add(seLabel, cPanel);

    cPanel.gridx = 1;
    cPanel.gridy = 0;
    jp1.add(menu_musicSlider, cPanel);
    cPanel.gridy = 1;
    jp1.add(game_musicSlider, cPanel);
    cPanel.gridy = 2;
    jp1.add(seSlider, cPanel);

    menu_musicSlider.addChangeListener(
      new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
          int volume = menu_musicSlider.getValue();
          changeVolume(gf.mp.music, volume);
        }
      }
    );
    game_musicSlider.addChangeListener(
      new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
          int volume = game_musicSlider.getValue();
          changeVolume(gf.gp.music, volume);
        }
      }
    );
    seSlider.addChangeListener(
      new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
          int volume = seSlider.getValue();
          changeVolume(gf.gp.se, volume);
        }
      }
    );

    container.add(jp1);
  }

  private class GameSlider extends JSlider {

    public GameSlider(int min, int max, int value) {
      super(min, max, value);
      setMajorTickSpacing(10);
      setMinorTickSpacing(5);
      setPaintTicks(true);
      setPaintLabels(true);
    }
  }

  private void changeVolume(Sound sound, int volume) {
    FloatControl fc = (FloatControl) sound.clip.getControl(
      FloatControl.Type.MASTER_GAIN
    );
    float min = -40, max = fc.getMaximum();
    float gain =
      (volume == 0 ? fc.getMinimum() : (max - min) / 100 * volume + min);
    sound.setVolume(gain);
    fc.setValue(gain);
  }

  public void showGameSetting() {
    gameDialog.setLocationRelativeTo(gf);
    gameDialog.setVisible(true);
  }
}
