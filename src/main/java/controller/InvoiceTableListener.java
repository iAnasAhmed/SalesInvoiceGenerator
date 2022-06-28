package controller;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.FileOperations;
import view.View;

/**
 *
 * @author Anas
 */
public class InvoiceTableListener implements ListSelectionListener {

    private FileOperations fileOperations;
    private View view = null;

    public InvoiceTableListener(View view, FileOperations fileOperations) {
        this.view = view;
        this.fileOperations = fileOperations;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (Controller.invoices.size() >= 1) {
            if (!e.getValueIsAdjusting()) {
                Controller.selectedRow = view.getInvoiceTable().getSelectedRow();
                TablesController.loadInvoicesLineTable(view, Controller.invoices);
                InvoicesLineController.updater(view, Controller.invoices, Controller.selectedRow);
            }
        }
    }
}
