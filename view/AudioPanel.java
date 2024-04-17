package view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.sound.sampled.FloatControl;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controller.Sound;

public class AudioPanel extends JPanel {

  private GameFrame gf;

  private GridBagConstraints c = new GridBagConstraints();
  private JLabel menu_musicLabel, game_musicLabel, seLabel;
  private GameSlider menu_musicSlider, game_musicSlider, seSlider;

  public AudioPanel(GameFrame gf) {
    this.gf = gf;
    setLayout(new GridBagLayout());
    initAudioPanel();
  }

  private void initAudioPanel() {
    menu_musicLabel = new JLabel("MUSIC (MENU)");
    game_musicLabel = new JLabel("MUSIC (GAME)");
    seLabel = new JLabel("SOUND EFFECT");

    menu_musicSlider = new GameSlider(0, 100, 100);
    game_musicSlider = new GameSlider(0, 100, 100);
    seSlider = new GameSlider(0, 100, 100);

    addComponent(menu_musicLabel, 0, 0);
    addComponent(menu_musicSlider, 1, 0);
    addVolumeListener(menu_musicSlider, gf.mp.music);

    addComponent(game_musicLabel, 0, 1);
    addComponent(game_musicSlider, 1, 1);
    addVolumeListener(game_musicSlider, gf.gp.music);

    addComponent(seLabel, 0, 2);
    addComponent(seSlider, 1, 2);
    addVolumeListener(seSlider, gf.gp.se);
  }

  private void addComponent(Component component, int x, int y) {
    c.gridx = x;
    c.gridy = y;
    add(component, c);
  }

  private void addVolumeListener(JSlider slider, Sound sound) {
    slider.addChangeListener(
      new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
          int volume = slider.getValue();
          changeVolume(sound, volume);
        }
      }
    );
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

  private class GameSlider extends JSlider {

    public GameSlider(int min, int max, int value) {
      super(min, max, value);
      setMajorTickSpacing(10);
      setMinorTickSpacing(5);
      setPaintTicks(true);
      setPaintLabels(true);
    }
  }
}
