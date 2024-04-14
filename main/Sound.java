package main;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

  Clip clip;
  URL soundURL[] = new URL[2];

  public Sound() {
    soundURL[0] = getClass().getResource("/sounds/theme.wav");
  }

  public void setFile(int index) {
    try {
      AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[index]);
      clip = AudioSystem.getClip();
      clip.open(ais);
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
}
