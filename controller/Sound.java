package controller;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

  public Clip clip = null;
  URL soundURL[] = new URL[15];
  float volumeValue = 1;

  public Sound() {
    soundURL[0] = getClass().getResource("/sounds/theme.wav");

    soundURL[1] = getClass().getResource("/sounds/single.wav");
    soundURL[2] = getClass().getResource("/sounds/double.wav");
    soundURL[3] = getClass().getResource("/sounds/triple.wav");
    soundURL[4] = getClass().getResource("/sounds/tetris.wav");

    soundURL[5] = getClass().getResource("/sounds/move.wav");
    soundURL[6] = getClass().getResource("/sounds/rotate.wav");
    soundURL[7] = getClass().getResource("/sounds/soft_drop.wav");
    soundURL[8] = getClass().getResource("/sounds/hard_drop.wav");

    soundURL[9] = getClass().getResource("/sounds/pause.wav");
    soundURL[10] = getClass().getResource("/sounds/game_over.wav");
    soundURL[11] = getClass().getResource("/sounds/level_up");

    soundURL[12] = getClass().getResource("/sounds/select.wav");
    soundURL[13] = getClass().getResource("/sounds/dialog.wav");
    soundURL[14] = getClass().getResource("/sounds/theme_piano.wav");
  }

  public void setFile(int index) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
      clip = AudioSystem.getClip();
      clip.open(ais);
      FloatControl newFc = (FloatControl) clip.getControl(
        FloatControl.Type.MASTER_GAIN
      );
      newFc.setValue(volumeValue);
    } catch (Exception e) {}
  }

  public void play() {
    clip.start();
  }

  public void loop() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void stop() {
    clip.stop();
  }

  public boolean isOpened() {
    return clip != null;
  }

  public void setVolume(float volumeValue) {
    this.volumeValue = volumeValue;
  }
}