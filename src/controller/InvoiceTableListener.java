package controller;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.FileOperations;
import view.View;
public class InvoiceTableListener implements ListSelectionListener {

    private FileOperations fileOperations;
    private View view = null;
    private InvoicesLineTableListener invoicesLineTableListener;

    public InvoiceTableListener(View view, FileOperations fileOperations, InvoicesLineTableListener invoicesLineTableListener) {
        this.view = view;
        this.fileOperations = fileOperations;
        this.invoicesLineTableListener = invoicesLineTableListener;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (Controller.invoices.size() >= 1) {
            if (!e.getValueIsAdjusting()) {
                Controller.selectedRow = view.getInvoiceTable().getSelectedRow();
                view.getInvoicesLineTable().getSelectionModel().removeListSelectionListener(invoicesLineTableListener);
                TablesController.loadInvoicesLineTable(view, Controller.invoices);
                InvoicesLineController.updater(view, Controller.invoices, Controller.selectedRow);
                view.getInvoicesLineTable().getSelectionModel().addListSelectionListener(invoicesLineTableListener);
            }
        }
    }
}
