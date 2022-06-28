package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import view.View;
import model.FileOperations;

/**
 *
 * @author Anas
 */
public class MainFrameWindowListener implements WindowListener {

    private FileOperations fileOperations;
    private InvoiceTableListener invoiceTableListener;
    private View view = null;

    public MainFrameWindowListener(View view, FileOperations fileOperations, InvoiceTableListener invoiceTableListener) {
        this.view = view;
        this.fileOperations = fileOperations;
        this.invoiceTableListener = invoiceTableListener;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
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
