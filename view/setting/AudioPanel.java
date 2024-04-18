package view.setting;

import controller.Sound;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.sound.sampled.FloatControl;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import view.GameFrame;

public class AudioPanel extends SettingPanel {

  private GridBagConstraints c = new GridBagConstraints();
  private JLabel menu_musicLabel, game_musicLabel, seLabel;
  private AudioSlider menu_musicSlider, game_musicSlider, seSlider;

  public AudioPanel(GameFrame gf) {
    super(gf);
    setLayout(new GridBagLayout());
    initAudioPanel();
  }

  private void initAudioPanel() {
    //INIT AND CONFIG
    menu_musicLabel = new JLabel("MUSIC (MENU)");
    game_musicLabel = new JLabel("MUSIC (GAME)");
    seLabel = new JLabel("SOUND EFFECT");

    menu_musicSlider = new AudioSlider(0, 100, 100);
    game_musicSlider = new AudioSlider(0, 100, 100);
    seSlider = new AudioSlider(0, 100, 100);

    //ADD COMPONENT
    addComponent(menu_musicLabel, 0, 0, c);
    addComponent(menu_musicSlider, 1, 0, c);
    addVolumeListener(menu_musicSlider, gf.mp.music);

    addComponent(game_musicLabel, 0, 1, c);
    addComponent(game_musicSlider, 1, 1, c);
    addVolumeListener(game_musicSlider, gf.gp.music);

    addComponent(seLabel, 0, 2, c);
    addComponent(seSlider, 1, 2, c);
    addVolumeListener(seSlider, gf.gp.se);
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

  private class AudioSlider extends JSlider {

    public AudioSlider(int min, int max, int value) {
      super(min, max, value);
      setMajorTickSpacing(10);
      setMinorTickSpacing(5);
      setPaintTicks(true);
      setPaintLabels(true);
    }
  }
}
