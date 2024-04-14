package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

  File file = new File(
    System.getProperty("user.dir"),
    "/txt_file/high_score.txt"
  );

  public int readHighScore() {
    int highScore = 0;
    try {
      BufferedReader br = new BufferedReader(new FileReader(file));

      String line = br.readLine();
      highScore = Integer.parseInt(line);
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return highScore;
  }

  public void writeHighScore(int newHighScore) {
    try {
      File file = new File(
        System.getProperty("user.dir"),
        "/txt_file/high_score.txt"
      );
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      bw.write(Integer.toString(newHighScore));
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
