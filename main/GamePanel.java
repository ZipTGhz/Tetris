package main;

import block.Block;
import block.Blocks;
import java.awt.Color;
import java.awt.Graphics;
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

  private Random random = new Random();
  private int randomIndex;
  private Block currentBlock, nextBlock;

  private Thread gameThread;
  private KeyHandle kh = new KeyHandle();

  private CollisionChecker cc = new CollisionChecker(this);
  private ArrayList<Block> bs = new ArrayList<>();

  public Grid grid = new Grid();

  Timer timer = new Timer(
    500,
    new ActionListener() {
      @Override
      public void actionPerformed(java.awt.event.ActionEvent e) {
        currentBlock.move(0, 1);
        if (cc.isCollision(currentBlock) == true) {
          currentBlock.move(0, -1);
          lockBlock();
          clearCompletedRow();
        }
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

    randomIndex = random.nextInt(bs.size());
    nextBlock = bs.get(randomIndex);
    bs.remove(randomIndex);
  }

  private class KeyHandle implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
      int keyCode = e.getKeyCode();
      if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
        currentBlock.move(-1, 0);
        if (
          cc.isInsideScreen(currentBlock) == false ||
          cc.isCollision(currentBlock)
        ) currentBlock.move(1, 0);
      } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
        currentBlock.move(1, 0);

        if (
          cc.isInsideScreen(currentBlock) == false ||
          cc.isCollision(currentBlock)
        ) currentBlock.move(-1, 0);
      } else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
        currentBlock.rotate();

        if (
          cc.isInsideScreen(currentBlock) == false ||
          cc.isCollision(currentBlock)
        ) currentBlock.un_rotate();
      } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
        currentBlock.move(0, 1);
        if (cc.isCollision(currentBlock) == true) {
          currentBlock.move(0, -1);
          lockBlock();
          clearCompletedRow();
        }
        if (cc.isInsideScreen(currentBlock) == false) currentBlock.move(0, -1);
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
    grid.draw(g);
    currentBlock.draw(g);
    g.setColor(Color.BLACK);
    for (int y = 0; y < grid.matrix.length; ++y) {
      for (int x = 0; x < grid.matrix[0].length; ++x) {
        g.drawRect(x * SIZE, y * SIZE, SIZE, SIZE);
      }
    }
    g.dispose();
  }

  private void lockBlock() {
    for (int i = 0; i < currentBlock.cell[0].length; ++i) {
      int x = currentBlock.cell[currentBlock.rotationState][i].x;
      int y = currentBlock.cell[currentBlock.rotationState][i].y;
      grid.matrix[y][x] = currentBlock.id;
    }
    currentBlock = nextBlock;

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
  }
}
