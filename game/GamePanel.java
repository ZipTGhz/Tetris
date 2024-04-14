package game;

import block.Block;
import block.Blocks;
import block.Colors;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;
import main.FileIO;
import main.GameFrame;
import main.Sound;

public class GamePanel extends JPanel implements Runnable {

  //CORE SETTING
  private final int SIZE = 32;
  private final int FPS = 30;
  private final long oneBillion = (long) 1e9;

  //GAME's VARIABLES
  private int randomIndex;
  private Random random = new Random();
  int score = 0, highScore;
  boolean pause = false;
  boolean isWrite = false;
  public boolean gameOver = false;

  //SYSTEM
  private ArrayList<Block> bs = new ArrayList<>();
  private Block nextBlock;
  private Thread gameThread;
  private Color[] cs = Colors.getCellColors();
  private Sound sound = new Sound();
  Block currentBlock, ghostBlock;
  FileIO f = new FileIO();
  CollisionChecker cc = new CollisionChecker(this);
  Grid grid = new Grid(SIZE);
  Timer timer = new Timer(
    500,
    new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (pause || gameOver) return;
        currentBlock.move(0, 1);
        if (cc.isCollision(currentBlock) == true) {
          currentBlock.move(0, -1);
          lockBlock();
          clearCompletedRow();
          if (checkGameOver()) gameOver = true;
        }

        if (cc.isInsideScreen(currentBlock) == false) currentBlock.move(0, -1);
      }
    }
  );
  GameFrame gf;
  private KeyHandle kh;

  public GamePanel(GameFrame gf) {
    this.gf = gf;
    kh = new KeyHandle(gf, this);
    addKeyListener(kh);
    // playMusic(0);
    setPreferredSize(new Dimension(SIZE * 16, SIZE * 20));
    highScore = f.readHighScore();
    this.setDoubleBuffered(true);
    this.setFocusable(true);
  }

  public void startGame() {
    gameThread = new Thread(this);
    initBlocks();
    gameThread.start();
    timer.start();
  }

  public void restartGame() {
    grid.setEmptyGrid();
    timer.stop();
    score = 0;
    bs.clear();
  }

  public void stopGame() {
    if (gameThread != null) gameThread.interrupt();
    restartGame();
  }

  private void initBlocks() {
    // bs.clear();
    bs.addAll(Arrays.asList(new Blocks().get_all_Blocks()));

    randomIndex = random.nextInt(bs.size());
    currentBlock = bs.get(randomIndex);
    bs.remove(randomIndex);
    ghostBlock = new Blocks.GhostBlock(currentBlock);

    randomIndex = random.nextInt(bs.size());
    nextBlock = bs.get(randomIndex);
    bs.remove(randomIndex);
  }

  @Override
  public void run() {
    double drawInterval = oneBillion / FPS;
    double delta = 0;
    long lastTime = System.nanoTime(), currentTime;
    while (gameThread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / drawInterval;
      lastTime = currentTime;
      if (delta >= 1) {
        updateGhostBlock();
        repaint();
        --delta;
      }
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.BLACK);
    g.fillRect(0, 0, 1920, 1080);
    grid.draw(g);
    ghostBlock.draw(g);
    currentBlock.draw(g);
    drawLayer(g);
    g.dispose();
  }

  private void drawLayer(Graphics g) {
    g.setColor(Color.BLACK);
    for (int y = 0; y < grid.matrix.length; ++y) {
      for (int x = 0; x < grid.matrix[0].length; ++x) {
        g.drawRect(x * SIZE, y * SIZE, SIZE, SIZE);
      }
    }
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.PLAIN, SIZE * 2 / 3));

    g.drawString("HIGH SCORE: " + Integer.toString(highScore), SIZE * 10, SIZE);
    // g.drawString(Integer.toString(highScore), SIZE * 12, SIZE * 2);

    g.drawString("SCORE: " + Integer.toString(score), SIZE * 10, SIZE * 3);
    g.drawString("NEXT BLOCK", SIZE * 10, SIZE * 5);
    g.setColor(cs[nextBlock.id]);
    int xx = SIZE * 8, yy = SIZE * 8;
    for (int i = 0; i < nextBlock.cell[0].length; ++i) {
      int x = nextBlock.cell[nextBlock.rotationState][i].x;
      int y = nextBlock.cell[nextBlock.rotationState][i].y;
      g.fillRect(xx + x * SIZE, yy + y * SIZE, SIZE, SIZE);
    }
    g.setColor(Color.WHITE);
    for (int y = 0; y < 4; ++y) {
      for (int x = 3; x < 7; ++x) {
        g.drawRect(xx + x * SIZE, yy + y * SIZE, SIZE, SIZE);
      }
    }
    if (gameOver) {
      g.drawString("GAME OVER!", SIZE * 11, SIZE * 16);
      return;
    }
    if (pause) {
      g.drawString("PAUSED!", SIZE * 11, SIZE * 16);
    }
  }

  void lockBlock() {
    for (int i = 0; i < currentBlock.cell[0].length; ++i) {
      int x = currentBlock.cell[currentBlock.rotationState][i].x;
      int y = currentBlock.cell[currentBlock.rotationState][i].y;
      grid.matrix[y][x] = currentBlock.id;
    }
    currentBlock = nextBlock;
    ghostBlock = new Blocks.GhostBlock(currentBlock);

    randomIndex = random.nextInt(bs.size());
    nextBlock = bs.get(randomIndex);
    bs.remove(randomIndex);

    if (bs.size() == 0) bs.addAll(Arrays.asList(new Blocks().get_all_Blocks()));
  }

  void clearCompletedRow() {
    int rowCompleted = 0;
    for (int y = grid.row - 1; y >= 0; --y) {
      if (cc.countColoredInRow(y) == 0) break;
      if (cc.countColoredInRow(y) == grid.col) {
        ++rowCompleted;
        grid.emptyRow(y);
      } else if (rowCompleted != 0) {
        grid.cutRow(y + rowCompleted, y);
      }
    }
    score += rowCompleted;
  }

  boolean checkGameOver() {
    for (int x = 0; x < grid.col; ++x) {
      if (grid.matrix[0][x] != 0) return true;
    }
    return false;
  }

  void instantMoveDown() {
    while (cc.isCollision(currentBlock) == false) currentBlock.move(0, 1);
    currentBlock.move(0, -1);
    lockBlock();
    clearCompletedRow();
    if (checkGameOver()) gameOver = true;
  }

  private void updateGhostBlock() {
    ghostBlock = new Blocks.GhostBlock(currentBlock);
    while (
      cc.isInsideScreen(ghostBlock) && cc.isCollision(ghostBlock) == false
    ) ghostBlock.move(0, 1);
    ghostBlock.move(0, -1);
  }

  // private void playMusic(int index) {
  //   sound.setFile(index);
  //   sound.play();
  //   sound.loop();
  // }

  // private void stopMusic() {
  //   sound.stop();
  // }

  private void playSE(int index) {
    sound.setFile(index);
    sound.stop();
  }
}
