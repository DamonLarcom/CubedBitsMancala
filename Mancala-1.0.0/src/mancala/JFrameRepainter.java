package mancala;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class JFrameRepainter implements WindowListener {

    private boolean windowReady;

    private Timer timer;

    private JFrame frameToRepaint;

    public JFrameRepainter(JFrame frameToRepaint, int fpsTarget) {
        if(frameToRepaint == null)
            throw new IllegalArgumentException("Cannot repaint a null JFrame!");

        if(fpsTarget <= 0)
            throw new IllegalArgumentException("FPS target is too low!");

        this.frameToRepaint = frameToRepaint;
        frameToRepaint.addWindowListener(this);
        timer = new Timer(100 / fpsTarget, this::callRepaint);
        windowReady = frameToRepaint.isVisible();
    }

    public void start() {
        timer.start();
    }

    @Override
    public void windowOpened(WindowEvent ignore) {

    }

    @Override
    public void windowClosing(WindowEvent ignore) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        timer.stop();
    }

    @Override
    public void windowIconified(WindowEvent e) {
        windowReady = false;
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        windowReady = true;
    }

    @Override
    public void windowActivated(WindowEvent ignore) {
        windowReady = true;
    }

    @Override
    public void windowDeactivated(WindowEvent ignore) {
        windowReady = false;
    }

    private void callRepaint(ActionEvent ignore) {
        if(windowReady)
            frameToRepaint.repaint();
    }
}
