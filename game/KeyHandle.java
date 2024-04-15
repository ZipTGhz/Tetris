package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.GameFrame;

public class KeyHandle implements KeyListener {

  GamePanel gp;
  GameFrame gf;

  KeyHandle(GameFrame gf, GamePanel gp) {
    this.gf = gf;
    this.gp = gp;
  }

  @Override
  public void keyTyped(KeyEvent e) {}

  @Override
  public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();
    if (keyCode == KeyEvent.VK_ESCAPE) {
      gp.pause = true;
      gp.playSE(13);
      gf.gd.returnMainMenuDialog();
      gp.pause = false;
    }
    if (gp.gameOver) {
      if (gp.isWrite == false && gp.highScore < gp.score) {
        gp.f.writeHighScore(gp.score);
      }
      gp.isWrite = true;
      return;
    }
    if (keyCode == KeyEvent.VK_P) {
      gp.pause = !gp.pause;
      gp.playSE(9);
    }
    if (gp.pause) return;
    if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
      gp.playSE(5);
      gp.currentBlock.move(-1, 0);
      gp.ghostBlock.move(-1, 0);
      if (
        gp.cc.isInsideScreen(gp.currentBlock) == false ||
        gp.cc.isCollision(gp.currentBlock)
      ) {
        gp.currentBlock.move(1, 0);
        gp.ghostBlock.move(1, 0);
      }
    } else if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
      gp.playSE(5);
      gp.currentBlock.move(1, 0);
      gp.ghostBlock.move(1, 0);

      if (
        gp.cc.isInsideScreen(gp.currentBlock) == false ||
        gp.cc.isCollision(gp.currentBlock)
      ) {
        gp.currentBlock.move(-1, 0);
        gp.ghostBlock.move(-1, 0);
      }
    } else if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
      gp.playSE(6);
      gp.currentBlock.rotate();
      gp.ghostBlock.rotate();
      if (
        gp.cc.isInsideScreen(gp.currentBlock) == false ||
        gp.cc.isCollision(gp.currentBlock)
      ) {
        gp.currentBlock.un_rotate();
        gp.ghostBlock.un_rotate();
      }
    } else if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
      gp.currentBlock.move(0, 1);
      if (gp.cc.isCollision(gp.currentBlock) == true) {
        gp.playSE(7);
        gp.currentBlock.move(0, -1);
        gp.lockBlock();
        gp.clearCompletedRow();
        if (gp.checkGameOver()) gp.gameOver = true;
      }
      if (gp.cc.isInsideScreen(gp.currentBlock) == false) gp.currentBlock.move(
        0,
        -1
      );
    } else if (keyCode == KeyEvent.VK_SPACE) {
      gp.timer.stop();
      gp.instantMoveDown();
      gp.playSE(8);
      gp.timer.start();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {}
}
