package controller;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;

/**
 *
 * @author Anas
 */
public class InvoiceDateTextFieldListener implements FocusListener, ActionListener {

    private View view = null;

    public InvoiceDateTextFieldListener(View view) {
        this.view = view;
    }

    @Override
    public void focusLost(FocusEvent e) {
        if ((!(Controller.invoices.isEmpty())) && (Controller.selectedRow >= 0)) {
            if (!((view.getDate().format(Controller.invoices.get(Controller.selectedRow).getInoviceDate())).equals((view.getInvoiceDateTextField().getText())))) {
                InvoicesLineController.dateValidator(view, Controller.invoices);
                view.getInvoiceTable().setRowSelectionInterval(Controller.selectedRow, Controller.selectedRow);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!((view.getDate().format(Controller.invoices.get(Controller.selectedRow).getInoviceDate())).equals((view.getInvoiceDateTextField().getText())))) {
            InvoicesLineController.dateValidator(view, Controller.invoices);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }
}
