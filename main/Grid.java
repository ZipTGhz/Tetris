package main;

import block.Colors;
import java.awt.Color;
import java.awt.Graphics;

public class Grid {

  private final int SIZE = 48;
  public int row = 20, col = 10;
  Color[] cs = Colors.getCellColors();
  public int matrix[][];

  Grid() {
    matrix = new int[row][col];
    // for (int i = 0; i < row; ++i) {
    //   for (int j = 0; j < col; ++j) {
    //     matrix[i][j] = 0;
    //   }
    // }
  }

  public void emptyRow(int currentRow) {
    for (int x = 0; x < col; ++x) {
      matrix[currentRow][x] = 0;
    }
  }

  public void cutRow(int newRow, int oldRow) {
    for (int x = 0; x < col; ++x) {
      matrix[newRow][x] = matrix[oldRow][x];
    }
    emptyRow(oldRow);
  }

  public void draw(Graphics g) {
    for (int x = 0; x < col; ++x) {
      for (int y = 0; y < row; ++y) {
        g.setColor(cs[matrix[y][x]]);
        g.fillRect(x * SIZE, y * SIZE, SIZE, SIZE);
      }
    }
  }
}
