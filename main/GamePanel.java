package main;

import block.Block;
import block.Blocks;
import block.Colors;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements Runnable {

  private final int SIZE = 48;
  private final int FPS = 30;
  private final long oneBillion = (long) 1e9;
  private boolean gameOver = false;
  private Random random = new Random();
  private int randomIndex;
  private Block currentBlock, nextBlock, ghostBlock;
  private int score = 0;
  private Thread gameThread;
  private KeyHandle kh = new KeyHandle();
  private Color[] cs = Colors.getCellColors();

  private CollisionChecker cc = new CollisionChecker(this);
  private ArrayList<Block> bs = new ArrayList<>();

  public Grid grid = new Grid();

  Timer timer = new Timer(
    250,
    new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (gameOver) return;
        currentBlock.move(0, 1);
        if (cc.isCollision(currentBlock) == true) {
          currentBlock.move(0, -1);
          lockBlock();
          clearCompletedRow();
          if (checkGameOver()) gameOver = true;
        }
        updateGhostBlock();
        if (cc.isInsideScreen(currentBlock) == false) currentBlock.move(0, -1);
      }
    }
  );

  public GamePanel() {
    initBlocks();
    this.setDoubleBuffered(true);
    addKeyListener(kh);
    this.setFocusable(true);
    timer.start();
  }

  private void initBlocks() {
    bs.addAll(Arrays.asList(new Blocks().get_all_Blocks()));

    randomIndex = random.nextInt(bs.size());
    currentBlock = bs.get(randomIndex);
    bs.remove(randomIndex);
    ghostBlock = new Blocks.GhostBlock(currentBlock);
    updateGhostBlock();

    randomIndex = random.nextInt(bs.size());
    nextBlock = bs.get(randomIndex);
    bs.remove(randomIndex);
  }

  private class KeyHandle implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
      if (gameOver) return;
      int keyCode = e.getKeyCode();
      if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
        currentBlock.move(-1, 0);
        ghostBlock.move(-1, 0);
        if (
          cc.isInsideScreen(currentBlock) == false ||
          cc.isCollision(currentBlock)
        ) {
          currentBlock.move(1, 0);
          ghostBlock.move(1, 0);
        }
        updateGhostBlock();
      } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
        currentBlock.move(1, 0);
        ghostBlock.move(1, 0);

        if (
          cc.isInsideScreen(currentBlock) == false ||
          cc.isCollision(currentBlock)
        ) {
          currentBlock.move(-1, 0);
          ghostBlock.move(-1, 0);
        }
        updateGhostBlock();
      } else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
        currentBlock.rotate();
        ghostBlock.rotate();
        if (
          cc.isInsideScreen(currentBlock) == false ||
          cc.isCollision(currentBlock)
        ) {
          currentBlock.un_rotate();
          ghostBlock.un_rotate();
        }
        updateGhostBlock();
      } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
        currentBlock.move(0, 1);
        if (cc.isCollision(currentBlock) == true) {
          currentBlock.move(0, -1);
          lockBlock();
          clearCompletedRow();
          if (checkGameOver()) gameOver = true;
        }
        updateGhostBlock();
        if (cc.isInsideScreen(currentBlock) == false) currentBlock.move(0, -1);
      } else if (keyCode == KeyEvent.VK_SPACE) {
        timer.stop();
        instantMoveDown();
        timer.start();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
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
    currentBlock.draw(g);
    ghostBlock.draw(g);
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
    g.setFont(new Font("Arial", Font.PLAIN, 32));
    g.drawString("SCORE: " + Integer.toString(score), 540, 150);
    g.drawString("NEXT SHAPE", 520, 350);
    g.setColor(cs[nextBlock.id]);
    int xx = 380, yy = 400;
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
      g.drawString("GAME OVER!", 520, 750);
    }
  }

  private void lockBlock() {
    for (int i = 0; i < currentBlock.cell[0].length; ++i) {
      int x = currentBlock.cell[currentBlock.rotationState][i].x;
      int y = currentBlock.cell[currentBlock.rotationState][i].y;
      grid.matrix[y][x] = currentBlock.id;
    }
    currentBlock = nextBlock;
    ghostBlock = new Blocks.GhostBlock(currentBlock);
    updateGhostBlock();

    randomIndex = random.nextInt(bs.size());
    nextBlock = bs.get(randomIndex);
    bs.remove(randomIndex);

    if (bs.size() == 0) bs.addAll(Arrays.asList(new Blocks().get_all_Blocks()));
  }

  private void clearCompletedRow() {
    int rowCompleted = 0;
    for (int y = grid.row - 1; y >= 0; --y) {
      if (cc.countColoredInRow(y) == 0) continue;
      if (cc.countColoredInRow(y) == grid.col) {
        ++rowCompleted;
        grid.emptyRow(y);
      } else if (rowCompleted != 0) {
        grid.cutRow(y + rowCompleted, y);
      }
    }
    score += rowCompleted;
  }

  private boolean checkGameOver() {
    for (int x = 0; x < grid.col; ++x) {
      if (grid.matrix[0][x] != 0) return true;
    }
    return false;
  }

  private void instantMoveDown() {
    while (cc.isCollision(currentBlock) == false) currentBlock.move(0, 1);
    currentBlock.move(0, -1);
    lockBlock();
    clearCompletedRow();
    if (checkGameOver()) gameOver = true;
  }

  private void updateGhostBlock() {
    while (cc.isCollision(ghostBlock) == false) ghostBlock.move(0, 1);
    ghostBlock.move(0, -1);
  }
}
