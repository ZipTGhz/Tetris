package block;

import java.awt.Color;
import java.awt.Graphics;

public class Block {

  private Color[] cs = Colors.getCellColors();
  public int id;
  public Position[][] cell;
  public int rotationState = 0;

  public Block() {}

  public void draw(Graphics g) {
    g.setColor(cs[id]);
    for (int i = 0; i < cell[0].length; ++i) {
      g.fillRect(
        cell[rotationState][i].x * 48,
        cell[rotationState][i].y * 48,
        48,
        48
      );
    }
  }

  public void rotate() {
    ++rotationState;
    if (rotationState == 4) {
      rotationState = 0;
    }
  }

  public void un_rotate() {
    --rotationState;
    if (rotationState == -1) {
      rotationState = 3;
    }
  }

  public void move(int x, int y) {
    for (int i = 0; i < cell.length; ++i) {
      for (int j = 0; j < cell[0].length; ++j) {
        cell[i][j].x += x;
        cell[i][j].y += y;
      }
    }
  }

  public void insertRow(int y) {
    for (int i = 0; i < cell.length; ++i) {
      for (int j = 0; j < cell[0].length; ++j) {
        cell[i][j].y = y;
      }
    }
  }
}
