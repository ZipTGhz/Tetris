package main;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

  GameFrame(int width, int height) {
    setTitle("TETRIS!");
    setSize(width, height);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
  }
}
