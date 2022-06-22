package controller;

import java.util.ArrayList;

import model.*;

public class InvoicesLineController {

    public static void updater(View view, ArrayList<InvoiceHeader> invoices, int selectedRow) {
        if (selectedRow != -1) {
            view.getInvoiceNumberLabel().setText(Integer.toString(invoices.get(selectedRow).getInoviceNumber()));
            view.getInvoiceDateTextField().setText(view.getDate().format(invoices.get(selectedRow).getInoviceDate()));
            view.getCustomerNameTextField().setText(invoices.get(selectedRow).getInoviceCustomerName());
            view.getInvoiceTotalLabel().setText(Float.toString(invoices.get(selectedRow).getInoviceTotal()));
        } }  }
