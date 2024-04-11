package main;

import block.Block;

public class CollisionChecker {

  GamePanel gp;

  public CollisionChecker(GamePanel gp) {
    this.gp = gp;
  }

  public boolean isInsideScreen(Block b) {
    for (int i = 0; i < b.cell.length; ++i) {
      int x = b.cell[b.rotationState][i].x;
      int y = b.cell[b.rotationState][i].y;
      if (x < 0 || y < 0 || x >= gp.grid.col || y >= gp.grid.row) return false;
    }
    return true;
  }

  public boolean isCollision(Block b) {
    for (int i = 0; i < b.cell[0].length; ++i) {
      int x = b.cell[b.rotationState][i].x;
      int y = b.cell[b.rotationState][i].y;
      if (y == gp.grid.matrix.length || gp.grid.matrix[y][x] != 0) return true;
    }
    return false;
  }

  public int countColoredInRow(int currentRow) {
    int count = 0;
    for (int x = 0; x < gp.grid.col; ++x) {
      if (gp.grid.matrix[currentRow][x] != 0) ++count;
    }
    return count;
  }
}
