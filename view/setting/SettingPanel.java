package view.setting;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import view.GameFrame;

public class SettingPanel extends JPanel {

  protected GameFrame gf;
  public Font tahoma = new Font("Tahoma", Font.BOLD, 16);

  public SettingPanel(GameFrame gf) {
    this.gf = gf;
  }

  protected void addComponent(
    Component component,
    int x,
    int y,
    GridBagConstraints constraints
  ) {
    constraints.gridx = x;
    constraints.gridy = y;
    add(component, constraints);
  }
}
