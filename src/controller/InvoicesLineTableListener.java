package controller;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class InvoicesLineTableListener implements ListSelectionListener {

    private View view = null;

    public InvoicesLineTableListener(View view) {
        this.view = view;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
    }
}
