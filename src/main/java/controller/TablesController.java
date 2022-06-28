package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.*;
import view.View;

/**
 *
 * @author Anas
 */
public class TablesController {

    public static void loadInvoicesHeaderTable(View view, ArrayList<InvoiceHeader> invoices) {
        
        while (InvoicesHeaderTableModel.setInvoicesHeaderTableModel(view).getRowCount() > 0)
            InvoicesHeaderTableModel.setInvoicesHeaderTableModel(view).removeRow(0);
        
        Object data[] = new Object[4];
        for (int i = 0; i < invoices.size(); i++) {
            data[0] = invoices.get(i).getInoviceNumber();
            
            String pattern = "MM-dd-yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(invoices.get(i).getInoviceDate());
            
            data[1] = date;
            data[2] = invoices.get(i).getInoviceCustomerName();
            data[3] = invoices.get(i).getInoviceTotal();
            InvoicesHeaderTableModel.setInvoicesHeaderTableModel(view).addRow(data);
        }
    }

    public static void loadInvoicesLineTable(View view, ArrayList<InvoiceHeader> invoices) {
        float total;
        Object data[] = new Object[5];
        int selectedRow = view.getInvoiceTable().getSelectedRow();

        if (selectedRow == -1) {
            while (InvoicesLineTableModel.setInvoicesLineTableModel(view).getRowCount() > 0)
                InvoicesLineTableModel.setInvoicesLineTableModel(view).removeRow(0);
        
        } else {
            while (InvoicesLineTableModel.setInvoicesLineTableModel(view).getRowCount() > 0) {
                InvoicesLineTableModel.setInvoicesLineTableModel(view).removeRow(0);
            }
            
            InvoicesLineTableModel.setInvoicesLineTableModel(view).setRowCount(0);
            for (int j = 0; j < invoices.get(selectedRow).getInvoicerow().size(); j++) {
                total = ((invoices.get(selectedRow).getInvoicerow().get(j).getItemPrice()) * (invoices.get(selectedRow).getInvoicerow().get(j).getItemCount()));
                invoices.get(selectedRow).getInvoicerow().get(j).setItemTotal(total);
                data[0] = invoices.get(selectedRow).getInvoicerow().get(j).getMainInvoice().getInoviceNumber();
                data[1] = invoices.get(selectedRow).getInvoicerow().get(j).getItemName();
                data[2] = invoices.get(selectedRow).getInvoicerow().get(j).getItemPrice();
                data[3] = invoices.get(selectedRow).getInvoicerow().get(j).getItemCount();
                data[4] = invoices.get(selectedRow).getInvoicerow().get(j).getItemTotal();
                InvoicesLineTableModel.setInvoicesLineTableModel(view).addRow(data);
                InvoicesLineController.updater(view, invoices, selectedRow);
            }
        }
    }
}
