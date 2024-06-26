package view;

import controller.CollisionChecker;
import controller.KeyHandle;
import controller.Sound;
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
import model.Grid;
import model.block.Block;
import model.block.Blocks;
import model.block.Colors;
import util.FileIO;

public class GamePanel extends JPanel implements Runnable {

  //CORE SETTING
  private final int SIZE = 32, FPS = 30;
  private final long oneBillion = (long) 1e9;
  private final Font tahomaFont = new Font("Tahoma", Font.PLAIN, SIZE * 2 / 3);

  //GAME'S VARIABLES
  private Random random = new Random();
  private int randomIndex;
  private int speed;

  public int score, highScore;
  public boolean pause = false, isWrite = false, gameOver = false;

  //SYSTEM
  private KeyHandle kh;
  private ArrayList<Block> bs = new ArrayList<>();
  private Block nextBlock;
  private Thread gameThread;
  private Color[] cs = Colors.getCellColors();

  GameFrame gf;

  public Grid grid = new Grid(SIZE);
  public Block currentBlock, ghostBlock;
  public CollisionChecker cc = new CollisionChecker(this);

  public Sound music = new Sound();
  public Sound se = new Sound();
  public FileIO f = new FileIO("/txt_file/high_score.txt");
  public Timer timer;

  public GamePanel(GameFrame gf) {
    this.gf = gf;

    kh = new KeyHandle(gf, this);
    addKeyListener(kh);

    music.setFile(14);
    se.setFile(1);

    setPreferredSize(new Dimension(SIZE * 16, SIZE * 20));
    setDoubleBuffered(true);
    setFocusable(true);
  }

  public void startGame() {
    gameThread = new Thread(this);

    initBlocks();
    highScore = f.read_int();
    speed = gf.gs.speedPanel.getSpeedValue();

    timer =
      new Timer(
        (10 - speed) * 100,
        new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
            if (pause || gameOver) return;
            kh.moveDown();
          }
        }
      );
    gameThread.start();
    timer.start();
    playMusic();

    gameOver = false;
    isWrite = false;
  }

  public void stopGame() {
    gameOver = true;
    isWrite = true;
    if (gameThread != null) gameThread.interrupt();
    if (music.isOpened()) stopMusic();
    grid.setEmptyGrid();
    if (timer != null) timer.stop();
    score = 0;
    bs.clear();
  }

  private void initBlocks() {
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
        if (gameOver == true && isWrite == false) {
          if (highScore < score) f.write_int(score);
          isWrite = true;
        }
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
    g.setFont(tahomaFont);

    g.drawString("HIGH SCORE: " + Integer.toString(highScore), SIZE * 10, SIZE);
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
    g.drawString("SPEED: " + Integer.toString(speed), SIZE * 11, SIZE * 14);
    if (gameOver) {
      g.drawString("GAME OVER!", SIZE * 11, SIZE * 16);
      return;
    }
    if (pause) {
      g.drawString("PAUSED!", SIZE * 11, SIZE * 16);
    }
  }

  public void lockBlock() {
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

  public void clearCompletedRow() {
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

    if (rowCompleted != 0) {
      if (rowCompleted == 1) {
        playSE(1);
      } else if (rowCompleted == 2) {
        playSE(2);
      } else if (rowCompleted == 3) {
        playSE(3);
      } else {
        playSE(4);
      }
      score += rowCompleted * speed;
    }
  }

  public boolean checkGameOver() {
    for (int x = 0; x < grid.col; ++x) {
      if (grid.matrix[0][x] != 0) return true;
    }
    return false;
  }

  public void instantMoveDown() {
    while (cc.isCollision(currentBlock) == false) currentBlock.move(0, 1);
    currentBlock.move(0, -1);
    lockBlock();
    clearCompletedRow();
    if (checkGameOver()) {
      gameOver = true;
      playSE(10);
    }
  }

  private void updateGhostBlock() {
    ghostBlock = new Blocks.GhostBlock(currentBlock);
    while (
      cc.isInsideScreen(ghostBlock) && cc.isCollision(ghostBlock) == false
    ) ghostBlock.move(0, 1);
    ghostBlock.move(0, -1);
  }

  private void playMusic() {
    music.play();
    music.loop();
  }

  private void stopMusic() {
    music.stop();
  }

  public void playSE(int index) {
    se.setFile(index);
    se.play();
  }
}
