package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameDialog {

  GameFrame gf;
  private Font tahoma_BOLD_20 = new Font("Tahoma", Font.BOLD, 20);

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
      info[i].setFont(tahoma_BOLD_20);
      dialog.add(info[i]);
    }

    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setSize(500, 400);
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
    aboutDialog.setLayout(new GridLayout(3, 1));
    JLabel full_nameLabel = new JLabel(
      "Full Name: Tran Van Giap",
      JLabel.CENTER
    );
    full_nameLabel.setFont(tahoma_BOLD_20);
    JLabel idLabel = new JLabel("ID: 221230818", JLabel.CENTER);
    idLabel.setFont(tahoma_BOLD_20);
    JLabel classLabel = new JLabel("CLASS: IT4 - K63", JLabel.CENTER);
    classLabel.setFont(tahoma_BOLD_20);

    aboutDialog.add(full_nameLabel);
    aboutDialog.add(idLabel);
    aboutDialog.add(classLabel);

    aboutDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    aboutDialog.setSize(500, 400);
    aboutDialog.setLocationRelativeTo(gf);
    aboutDialog.setResizable(false);
    aboutDialog.setVisible(true);
  }
}
