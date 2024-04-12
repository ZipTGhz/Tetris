package main;

public class Main {

  public static void main(String[] args) {
    GameFrame window = new GameFrame(768, 999);
    GamePanel gp = new GamePanel();
    window.add(gp);
    gp.startGameThread();
    window.setVisible(true);
  }
}
