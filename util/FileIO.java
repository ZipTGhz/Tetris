package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIO {

  File file;

  public FileIO(String relative_path) {
    file = new File(System.getProperty("user.dir"), relative_path);
  }

  public int read_int() {
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

  public void write_int(int value) {
    try {
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      bw.write(Integer.toString(value));
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
