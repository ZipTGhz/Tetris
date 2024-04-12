package block;

import java.awt.Color;

public class Colors {

  static Color dark_grey = new Color(26, 31, 40);
  static Color green = new Color(47, 230, 23);
  static Color red = new Color(232, 18, 18);
  static Color orange = new Color(226, 116, 17);
  static Color yellow = new Color(237, 234, 4);
  static Color purple = new Color(166, 0, 247);
  static Color cyan = new Color(21, 204, 209);
  static Color blue = new Color(13, 64, 216);
  static Color white = Color.WHITE;

  public static Color[] getCellColors() {
    return new Color[] {
      dark_grey,
      green,
      red,
      orange,
      yellow,
      purple,
      cyan,
      blue,
      white,
    };
  }
}
