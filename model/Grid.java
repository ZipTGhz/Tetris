package model;

import java.awt.Color;
import java.awt.Graphics;

import model.block.Colors;

public class Grid {

  private int SIZE;
  public int row = 20;
public int col = 10;
  public int matrix[][];
  Color[] cs = Colors.getCellColors();

  public Grid(int SIZE_OF_BLOCK) {
    this.SIZE = SIZE_OF_BLOCK;
    matrix = new int[row][col];
  }

  public void setEmptyGrid() {
    for (int x = 0; x < col; ++x) {
      for (int y = 0; y < row; ++y) {
        matrix[y][x] = 0;
      }
    }
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
