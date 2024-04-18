package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameDialog {

  GameFrame gf;

  public GameDialog(GameFrame gf) {
    this.gf = gf;
  }

  public void howToPlayDialog() {
    JDialog dialog = new JDialog(gf, "How to play", true);
    JLabel info[] = new JLabel[6];
    info[0] = new JLabel("W / UP_ARROW ___ FLIP");
    info[1] = new JLabel("A / LEFT_ARROW ___ MOVE LEFT");
    info[2] = new JLabel("S / DOWN_ARROW ___ MOVE DOWN");
    info[3] = new JLabel("D / RIGHT_ARROW ___ MOVE RIGHT");
    info[4] = new JLabel("SPACE ___ INSTANT MOVE DOWN");
    info[5] = new JLabel("P ___ PAUSE GAME");
    dialog.setLayout(new GridLayout(info.length, 3));
    for (int i = 0; i < info.length; ++i) {
      info[i].setHorizontalAlignment(JLabel.CENTER);
      dialog.add(info[i]);
    }

    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setSize(400, 300);
    dialog.setLocationRelativeTo(gf);
    dialog.setResizable(false);
    dialog.setVisible(true);
  }

  public boolean exitDialog() {
    Image scaleImage = gf.originalImage.getScaledInstance(
      32,
      32,
      Image.SCALE_SMOOTH
    );
    int result = JOptionPane.showConfirmDialog(
      gf,
      "Bạn chắc chắn muốn thoát?",
      "EXIT",
      JOptionPane.YES_NO_OPTION,
      JOptionPane.QUESTION_MESSAGE,
      new ImageIcon(scaleImage)
    );
    return result == JOptionPane.YES_OPTION;
  }

  public void returnMainMenuDialog() {
    int result = JOptionPane.showConfirmDialog(
      gf,
      "Bạn có muốn trở về màn hình chính không?",
      "RETURN TO MAIN MENU",
      JOptionPane.YES_NO_OPTION
    );
    if (result == JOptionPane.YES_OPTION) {
      gf.displayMainMenu();
    }
  }

  public void aboutDialog() {
    JDialog aboutDialog = new JDialog(gf, "ABOUT", true);
    aboutDialog.setLayout(new BorderLayout());
    JLabel aboutLabel = new JLabel("Tran Van Giap - 221230818");
    aboutLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
    aboutLabel.setHorizontalAlignment(JLabel.CENTER);
    aboutDialog.add(aboutLabel, BorderLayout.CENTER);

    aboutDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    aboutDialog.setSize(400, 300);
    aboutDialog.setLocationRelativeTo(gf);
    aboutDialog.setResizable(false);
    aboutDialog.setVisible(true);
  }
}
