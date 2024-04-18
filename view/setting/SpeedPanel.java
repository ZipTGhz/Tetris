package view.setting;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import util.FileIO;
import view.GameFrame;

public class SpeedPanel extends SettingPanel {

  FileIO f = new FileIO("/txt_file/speed.txt");
  private GridBagConstraints c = new GridBagConstraints();
  private JLabel speedLabel;
  private JSlider speedSlider;

  private int speedValue;

  public SpeedPanel(GameFrame gf) {
    super(gf);
    setLayout(new GridBagLayout());
    initSpeedPanel();
  }

  private void initSpeedPanel() {
    //INIT AND CONFIG
    speedValue = f.read_int();
    speedLabel = new JLabel("SPEED");
    speedSlider = new JSlider(1, 9, speedValue);
    speedSlider.setMajorTickSpacing(4);
    speedSlider.setMinorTickSpacing(1);
    speedSlider.setPaintTicks(true);
    speedSlider.setPaintLabels(true);
    //ADD COMPONENT
    addComponent(speedLabel, 0, 0, c);
    addComponent(speedSlider, 1, 0, c);
    speedSlider.addChangeListener(
      new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
          int value = speedSlider.getValue();
          speedValue = value;
          f.write_int(speedValue);
        }
      }
    );
  }

  public int getSpeedValue() {
    return speedValue;
  }
}
