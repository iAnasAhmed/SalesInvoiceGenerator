package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AddItemDialogWindowListener implements WindowListener {

    private View view = null;

    public AddItemDialogWindowListener(View view) {
        this.view = view;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        view.getNewItemName().setText("");
        view.getNewItemPrice().setText("");
        view.getNewItemPriceSpinner().setValue((Object) 1);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}

