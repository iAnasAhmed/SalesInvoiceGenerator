package controller;

import java.util.ArrayList;

import model.*;
import view.View;

/**
 *
 * @author Anas
 */
public class InvoicesLineController {

    public static void updater(View view, ArrayList<InvoiceHeader> invoices, int selectedRow) {
        if (selectedRow != -1) {
            view.getInvoiceNumberLabel().setText(Integer.toString(invoices.get(selectedRow).getInoviceNumber()));
            view.getInvoiceDateTextField().setText(view.getDate().format(invoices.get(selectedRow).getInoviceDate()));
            view.getCustomerNameTextField().setText(invoices.get(selectedRow).getInoviceCustomerName());
            view.getInvoiceTotalLabel().setText(Float.toString(invoices.get(selectedRow).getInoviceTotal()));
        }
    }

    public static void dateValidator(View view, ArrayList<InvoiceHeader> invoices) {
        int choice = view.showYesNoCancelDialog(view.getInvoicesItemsPanel(), "Do you want to save date changes?", "Confirmation");
        switch (choice) {
            case 0 -> {
                try {
                    if (!view.getInvoiceDateTextField().getText().matches("^\\d{2}\\-\\d{2}\\-\\d{4}")) {
                        throw new Exception();
                    }

                    FileOperations.date.setLenient(false);
                    FileOperations.date.parse(view.getInvoiceDateTextField().getText());
                    invoices.get(view.getInvoiceTable().getSelectedRow()).setInoviceDate(view.getDate().parse(view.getInvoiceDateTextField().getText()));
                    InvoicesHeaderController.updateTableDate(view, invoices);
                    view.getInvoiceDateTextField().requestFocus();
                } catch (Exception ex) {
                    View.setJOptionPaneMessagMessage(view.getInvoicesItemsPanel(), "Please enter a valid date (e.g 06-06-2022)", "wrong date", "ERROR_MESSAGE");
                    view.getInvoiceDateTextField().setText(view.getDate().format(invoices.get(view.getInvoiceTable().getSelectedRow()).getInoviceDate()));
                    view.getInvoiceDateTextField().requestFocus();
                }
            }
            case 1 ->
                view.getInvoiceDateTextField().requestFocus();
            default -> {
                view.getInvoiceDateTextField().setText(view.getDate().format(invoices.get(Controller.selectedRow).getInoviceDate()));
                view.getInvoiceDateTextField().requestFocus();
            }
        }
    }

    public static void changeCustomerNameTextField(View view, ArrayList<InvoiceHeader> invoices) {
        int choice = view.showYesNoCancelDialog(view.getInvoicesItemsPanel(), "Do you want to save new customer name?", "Confirmation");
        switch (choice) {
            case 0 -> {
                invoices.get(view.getInvoiceTable().getSelectedRow()).setInoviceCustomerName(view.getCustomerNameTextField().getText());
                InvoicesHeaderController.updateTableCustomerName(view, invoices);
                view.getCustomerNameTextField().requestFocus();
            }
            case 1 ->
                view.getCustomerNameTextField().requestFocus();
            default -> {
                view.getCustomerNameTextField().setText(invoices.get(Controller.selectedRow).getInoviceCustomerName());
                view.getCustomerNameTextField().requestFocus();
            }
        }
    }

    static void addNewItem(View view, ArrayList<InvoiceHeader> invoices) {
        String itemName;
        float price = 0;
        int count = 0;

        boolean flag = false;
        itemName = view.getNewItemName().getText();

        if (itemName.equalsIgnoreCase("")) {
            View.getAddItemDialog().setVisible(false);
            View.setJOptionPaneMessagMessage(view.getInvoicesItemsPanel(), "Please enter a valid name", "Empty Item Name", "ERROR_MESSAGE");
            showNewItemDialog(view);
        } else if (view.getNewItemPrice().getText().equalsIgnoreCase("")) {
            View.getAddItemDialog().setVisible(false);
            View.setJOptionPaneMessagMessage(view.getInvoicesItemsPanel(), "Please enter a price", "Empty Price", "ERROR_MESSAGE");
            showNewItemDialog(view);
        } else {
            
            try {
                price = Float.parseFloat(view.getNewItemPrice().getText());
                view.getNewItemPriceSpinner().commitEdit();
                count = (Integer) view.getNewItemPriceSpinner().getValue();
            } catch (Exception e) {
                flag = true;
                e.printStackTrace();
            }

            if (!flag) {
                InvoiceHeader temp = invoices.get(view.getInvoiceTable().getSelectedRow());
                InvoiceLine newItem = new InvoiceLine(itemName, price, count, temp);
                temp.getInvoicerow().add(newItem);
            }

            view.getNewItemName().setText("");
            view.getNewItemPrice().setText("");
            view.getNewItemPriceSpinner().setValue((Object) 1);
        }
    }

    static void showNewItemDialog(View view) {
        view.setLocations();
        View.getAddItemDialog().setTitle("Add new item to invoice " + view.getInvoiceNumberLabel().getText());
        View.getAddItemDialog().setVisible(true);
    }

    static void deleteItem(View view, ArrayList<InvoiceHeader> invoices) {
        if (view.getInvoicesLineTable().getSelectedRow() >= 0) {
            int rowToBeDeleted;
            rowToBeDeleted = view.getInvoicesLineTable().getSelectedRow();
            invoices.get(view.getInvoiceTable().getSelectedRow()).getInvoicerow().remove(rowToBeDeleted);
        }
    }
}
