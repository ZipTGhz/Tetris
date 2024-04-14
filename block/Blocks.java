package block;

public class Blocks {

  public static class LBlock extends Block {

    public LBlock() {
      id = 1;
      cell =
        new Position[][] {
          {
            new Position(2, 0),
            new Position(2, 1),
            new Position(1, 1),
            new Position(0, 1),
          },
          {
            new Position(1, 0),
            new Position(1, 1),
            new Position(1, 2),
            new Position(2, 2),
          },
          {
            new Position(0, 1),
            new Position(1, 1),
            new Position(2, 1),
            new Position(0, 2),
          },
          {
            new Position(0, 0),
            new Position(1, 0),
            new Position(1, 1),
            new Position(1, 2),
          },
        };
      move(3, 0);
    }
  }

  public static class JBlock extends Block {

    public JBlock() {
      id = 2;
      cell =
        new Position[][] {
          {
            new Position(0, 0),
            new Position(0, 1),
            new Position(1, 1),
            new Position(2, 1),
          },
          {
            new Position(1, 0),
            new Position(2, 0),
            new Position(1, 1),
            new Position(1, 2),
          },
          {
            new Position(0, 1),
            new Position(1, 1),
            new Position(2, 1),
            new Position(2, 2),
          },
          {
            new Position(1, 0),
            new Position(1, 1),
            new Position(0, 2),
            new Position(1, 2),
          },
        };
      move(3, 0);
    }
  }

  public static class IBlock extends Block {

    public IBlock() {
      id = 3;
      cell =
        new Position[][] {
          {
            new Position(0, 1),
            new Position(1, 1),
            new Position(2, 1),
            new Position(3, 1),
          },
          {
            new Position(2, 0),
            new Position(2, 1),
            new Position(2, 2),
            new Position(2, 3),
          },
          {
            new Position(0, 2),
            new Position(1, 2),
            new Position(2, 2),
            new Position(3, 2),
          },
          {
            new Position(1, 0),
            new Position(1, 1),
            new Position(1, 2),
            new Position(1, 3),
          },
        };
      move(3, -1);
    }
  }

  public static class OBlock extends Block {

    public OBlock() {
      id = 4;
      cell =
        new Position[][] {
          {
            new Position(0, 0),
            new Position(0, 1),
            new Position(1, 0),
            new Position(1, 1),
          },
          {
            new Position(0, 0),
            new Position(0, 1),
            new Position(1, 0),
            new Position(1, 1),
          },
          {
            new Position(0, 0),
            new Position(0, 1),
            new Position(1, 0),
            new Position(1, 1),
          },
          {
            new Position(0, 0),
            new Position(0, 1),
            new Position(1, 0),
            new Position(1, 1),
          },
        };
      move(4, 0);
    }
  }

  public static class SBlock extends Block {

    public SBlock() {
      id = 5;
      cell =
        new Position[][] {
          {
            new Position(0, 1),
            new Position(1, 1),
            new Position(1, 0),
            new Position(2, 0),
          },
          {
            new Position(1, 0),
            new Position(1, 1),
            new Position(2, 1),
            new Position(2, 2),
          },
          {
            new Position(1, 1),
            new Position(2, 1),
            new Position(0, 2),
            new Position(1, 2),
          },
          {
            new Position(0, 0),
            new Position(0, 1),
            new Position(1, 1),
            new Position(1, 2),
          },
        };
      move(3, 0);
    }
  }

  public static class TBlock extends Block {

    public TBlock() {
      id = 6;
      cell =
        new Position[][] {
          {
            new Position(0, 1),
            new Position(1, 0),
            new Position(1, 1),
            new Position(2, 1),
          },
          {
            new Position(1, 0),
            new Position(1, 1),
            new Position(2, 1),
            new Position(1, 2),
          },
          {
            new Position(0, 1),
            new Position(1, 1),
            new Position(2, 1),
            new Position(1, 2),
          },
          {
            new Position(1, 0),
            new Position(0, 1),
            new Position(1, 1),
            new Position(1, 2),
          },
        };
      move(3, 0);
    }
  }

  public static class ZBlock extends Block {

    public ZBlock() {
      id = 7;
      cell =
        new Position[][] {
          {
            new Position(0, 0),
            new Position(1, 0),
            new Position(1, 1),
            new Position(2, 1),
          },
          {
            new Position(2, 0),
            new Position(1, 1),
            new Position(2, 1),
            new Position(1, 2),
          },
          {
            new Position(0, 1),
            new Position(1, 1),
            new Position(1, 2),
            new Position(2, 2),
          },
          {
            new Position(1, 0),
            new Position(0, 1),
            new Position(1, 1),
            new Position(0, 2),
          },
        };
      move(3, 0);
    }
  }

  public static class GhostBlock extends Block {

    public GhostBlock(Block b) {
      this.id = 8;
      this.rotationState = b.rotationState;
      this.cell = new Position[b.cell.length][b.cell[0].length]; // Tạo một mảng mới để sao chép các vị trí của block ban đầu
      for (int i = 0; i < b.cell.length; i++) {
        for (int j = 0; j < b.cell[0].length; ++j) {
          this.cell[i][j] = new Position(b.cell[i][j].x, b.cell[i][j].y);
        }
      }
    }
  }

  public Block[] get_all_Blocks() {
    return new Block[] {
      new IBlock(),
      new JBlock(),
      new LBlock(),
      new OBlock(),
      new SBlock(),
      new TBlock(),
      new ZBlock(),
    };
  }
}
