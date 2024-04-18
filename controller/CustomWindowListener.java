package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import view.GameDialog;

public class CustomWindowListener implements WindowListener {

  GameDialog gd;

  public CustomWindowListener(GameDialog gd) {
    this.gd = gd;
  }

  @Override
  public void windowOpened(WindowEvent e) {}

  @Override
  public void windowClosing(WindowEvent e) {
    if (gd.exitDialog() == true) System.exit(0);
  }

  @Override
  public void windowClosed(WindowEvent e) {}

  @Override
  public void windowIconified(WindowEvent e) {}

  @Override
  public void windowDeiconified(WindowEvent e) {}

  @Override
  public void windowActivated(WindowEvent e) {}

  @Override
  public void windowDeactivated(WindowEvent e) {}
}
