package controller;

import java.awt.event.*;

import view.View;

/**
 *
 * @author Anas
 */
public class CustomerNameTextFieldListener implements FocusListener, ActionListener {

    private View view = null;

    public CustomerNameTextFieldListener(View view) {
        this.view = view;
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (!((Controller.invoices.get(Controller.selectedRow).getInoviceCustomerName()).equals((view.getCustomerNameTextField().getText())))) {
            InvoicesLineController.changeCustomerNameTextField(view, Controller.invoices);
            view.getInvoiceTable().setRowSelectionInterval(Controller.selectedRow, Controller.selectedRow);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!((Controller.invoices.get(Controller.selectedRow).getInoviceCustomerName()).equals((view.getCustomerNameTextField().getText())))) {
            InvoicesLineController.changeCustomerNameTextField(view, Controller.invoices);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
    }

}
