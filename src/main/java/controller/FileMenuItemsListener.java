package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import model.*;
import view.View;

/**
 *
 * @author Anas
 */
public class FileMenuItemsListener implements ActionListener{

    private FileOperations fileOperations;
    private InvoiceTableListener invoiceTableListener;
    private View view;

    public FileMenuItemsListener(View view, FileOperations fileOperations, InvoiceTableListener invoiceTableListener) {
        this.view = view;
        this.fileOperations = fileOperations;
        this.invoiceTableListener = invoiceTableListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Load Files" -> {
                while (InvoicesHeaderTableModel.setInvoicesHeaderTableModel(view).getRowCount() > 0) {
                    InvoicesHeaderTableModel.setInvoicesHeaderTableModel(view).removeRow(0);
                }

                while (InvoicesLineTableModel.setInvoicesLineTableModel(view).getRowCount() > 0) {
                    InvoicesLineTableModel.setInvoicesLineTableModel(view).removeRow(0);
                }

                view.getInvoiceTotalLabel().setText("");
                fileOperations.getFilesPaths();
                if ((FileOperations.selectedInvoiceHeader != null) && (FileOperations.selectedInvoiceLine != null)) {
                    Controller.invoices = fileOperations.readFile();
                    fileOperations.main(Controller.invoices);
                    InvoicesHeaderController.calculateInvoiceTableTotal(Controller.invoices);
                    TablesController.loadInvoicesHeaderTable(view, Controller.invoices);
                    fileOperations.getMaxNumberOfExistedInvoices(Controller.maxNumberOfExistedInvoices, Controller.invoices);
                }
            }
            case "Save File" -> {
                try {
                    fileOperations.writeFile(Controller.invoices);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            case "Quit" -> {
                System.exit(0);
            }
        }
    }

}
