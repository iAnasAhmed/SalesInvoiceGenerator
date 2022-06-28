package controller;

import java.text.ParseException;
import java.util.ArrayList;

import model.*;
import view.View;

/**
 *
 * @author Anas
 */
public class InvoicesHeaderController {

    TablesController loadTablesContents = new TablesController();

    static void updateTableDate(View view, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(view)
                .setValueAt(invoices.get(view.getInvoiceTable().getSelectedRow()).getInoviceDate(), view.getInvoiceTable().getSelectedRow(), 1);
    }

    static void updateTableCustomerName(View view, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(view)
                .setValueAt(invoices.get(view.getInvoiceTable().getSelectedRow()).getInoviceCustomerName(), view.getInvoiceTable().getSelectedRow(), 2);
    }

    static void updateTableTotal(View view, ArrayList<InvoiceHeader> invoices) {
        InvoicesHeaderTableModel.setInvoicesHeaderTableModel(view)
                .setValueAt(invoices.get(view.getInvoiceTable().getSelectedRow()).getInoviceTotal(), view.getInvoiceTable().getSelectedRow(), 3);
    }

    static void showCreatNewInvoiceDialog(View view) {
        view.setLocations();
        view.getNewInvoiceDateField().setText("");
        View.getNewInvoiceDialog().setVisible(true);
    }

    static void addNewInvoice(View view, ArrayList<InvoiceHeader> invoices) {
        if (view.getNewCustomerName().getText().equalsIgnoreCase("")) {
            View.getNewInvoiceDialog().setModal(false);
            View.setJOptionPaneMessagMessage(view.getInvoicesTablePanel(), "Please Enter A Name For The Customer", "Empty Name Entered", "ERROR_MESSAGE");
            View.getNewInvoiceDialog().setModal(true);
            showCreatNewInvoiceDialog(view);
        } else {
            try {
                Controller.maxNumberOfExistedInvoices++;

                InvoiceHeader newRow = new InvoiceHeader((Controller.maxNumberOfExistedInvoices), (view.getDate().parse(view.getNewInvoiceDateField().getText())), (view.getNewCustomerName().getText()));
                invoices.add(newRow);
                TablesController.loadInvoicesHeaderTable(view, invoices);
                View.getNewInvoiceDialog().setVisible(false);
                view.getInvoiceTable().setRowSelectionInterval((invoices.size() - 1), (invoices.size() - 1));
                view.getNewCustomerName().setText("");
                view.getNewInvoiceDateField().setText("");
                InvoicesLineController.updater(view, invoices, invoices.size() - 1);
                TablesController.loadInvoicesLineTable(view, invoices);
            } catch (ParseException ex) {
                View.getNewInvoiceDialog().setModal(false);
                View.setJOptionPaneMessagMessage(view.getInvoicesTablePanel(), "Please Enter A Valid Date", "Invalid Date Entered", "ERROR_MESSAGE");
                View.getNewInvoiceDialog().setModal(true);
                showCreatNewInvoiceDialog(view);
            }

        }
    }

    static void deleteSelectedInvoice(View view, ArrayList<InvoiceHeader> invoices) {
        int invoiceToBeDeleted = view.getInvoiceTable().getSelectedRow();

        if (invoiceToBeDeleted == -1) {
            View.setJOptionPaneMessagMessage(view.getInvoicesTablePanel(), "Select Invoice Row First", "Error", "ERROR_MESSAGE");
        } else {
            invoices.remove(invoiceToBeDeleted);
            TablesController.loadInvoicesHeaderTable(view, invoices);
            if (!invoices.isEmpty()) {
                view.getInvoiceTable().setRowSelectionInterval((invoices.size() - 1), (invoices.size() - 1));
                InvoicesLineController.updater(view, invoices, invoices.size() - 1);
                TablesController.loadInvoicesLineTable(view, invoices);
            } else {
                while (InvoicesLineTableModel.setInvoicesLineTableModel(view).getRowCount() > 0) {
                    InvoicesLineTableModel.setInvoicesLineTableModel(view).removeRow(0);
                }
            }
        }
    }

    public static void calculateInvoiceTableTotal(ArrayList<InvoiceHeader> invoices) {
        float total;
        for (int i = 0; i < invoices.size(); i++) {
            total = 0;
            for (int j = 0; j < invoices.get(i).getInvoicerow().size(); j++) {
                total = total + ((invoices.get(i).getInvoicerow().get(j).getItemPrice()) * (invoices.get(i).getInvoicerow().get(j).getItemCount()));
            }
            invoices.get(i).setInoviceTotal(total);
        }
    }
}
