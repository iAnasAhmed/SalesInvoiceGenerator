package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.View;

/**
 *
 * @author Anas
 */
public class Controller implements ActionListener, KeyListener {

    private InvoiceHeader invoiceHeader;
    private InvoiceLine invoiceLine;
    private View view;
    private FileOperations fileOperations;
    private TablesController loadTablesContents;

    public volatile static ArrayList<InvoiceHeader> invoices = new ArrayList<>();
    public volatile static int selectedRow = 0;
    public volatile static int maxNumberOfExistedInvoices = 0;

    private InvoiceTableListener invoiceTableListener;
    private FileMenuItemsListener fileMenuItemsListener;
    private MainFrameWindowListener mainFrameWindowListener;
    private AddItemDialogWindowListener addItemDialogWindowListener;
    private InvoiceDateTextFieldListener invoiceDateTextFieldListener;
    private CustomerNameTextFieldListener customerNameTextFieldListener;

    public Controller(InvoiceHeader invoiceHeader, InvoiceLine invoiceLine, View view) {
        this.invoiceHeader = invoiceHeader;
        this.invoiceLine = invoiceLine;
        this.view = view;
        fileOperations = new FileOperations(this.view);

        fileMenuItemsListener = new FileMenuItemsListener(view, fileOperations, invoiceTableListener);
        mainFrameWindowListener = new MainFrameWindowListener(view, fileOperations, invoiceTableListener);

        addItemDialogWindowListener = new AddItemDialogWindowListener(view);

        invoiceDateTextFieldListener = new InvoiceDateTextFieldListener(view);
        customerNameTextFieldListener = new CustomerNameTextFieldListener(view);

        turnOnAllActionListerners(view);

        loadTablesContents = new TablesController();
        
    }

    private void turnOnAllActionListerners(View view) {
        view.getLoadFile().addActionListener(fileMenuItemsListener);
        view.getLoadFile().setActionCommand("Load Files");

        view.getSaveFile().addActionListener(fileMenuItemsListener);
        view.getSaveFile().setActionCommand("Save File");

        view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);

        view.addWindowListener(mainFrameWindowListener);

        View.getAddItemDialog().addWindowListener(addItemDialogWindowListener);

        view.getInvoiceDateTextField().addActionListener(invoiceDateTextFieldListener);
        view.getInvoiceDateTextField().addFocusListener(invoiceDateTextFieldListener);

        view.getCustomerNameTextField().addActionListener(customerNameTextFieldListener);
        view.getCustomerNameTextField().addFocusListener(customerNameTextFieldListener);

        view.getCreatNewInvoiceButton().addActionListener(this);
        view.getCreatNewInvoiceButton().setActionCommand("Creat New Invoice");

        view.getCreatNewInvoiceOK().addActionListener(this);
        view.getCreatNewInvoiceOK().setActionCommand("Creat New Invoice OK");

        view.getCreatNewInvoiceCancel().addActionListener(this);
        view.getCreatNewInvoiceCancel().setActionCommand("Creat New Invoice Cancel");

        view.getDeleteInvoiceButton().addActionListener(this);
        view.getDeleteInvoiceButton().setActionCommand("Delete Invoice");

        view.getAddItemButton().addActionListener(this);
        view.getAddItemButton().setActionCommand("Add Item");

        view.getNewItemPrice().addKeyListener(this);
        
        view.getAddItemDialogCancel().addActionListener(this);
        view.getAddItemDialogCancel().setActionCommand("Add Item Dialog Cancel");

        view.getAddItemDialogOK().addActionListener(this);
        view.getAddItemDialogOK().setActionCommand("Add Item Dialog OK");

        view.getDeleteItemButton().addActionListener(this);
        view.getDeleteItemButton().setActionCommand("Delete Item");

    }

    /**
     *
     * Implement All Listeners
     *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Creat New Invoice" -> {
                if (view.getFocusOwner() != null) {
                    InvoicesHeaderController.showCreatNewInvoiceDialog(view);
                }
            }

            case "Creat New Invoice OK" -> {
                view.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                InvoicesHeaderController.addNewInvoice(view, invoices);
                view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
            }


            case "Creat New Invoice Cancel" -> {
                view.getNewCustomerName().setText("");
                View.getNewInvoiceDialog().setVisible(false);
            }

            case "Delete Invoice" -> {
                if (view.getFocusOwner() != null) {
                    view.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                    InvoicesHeaderController.deleteSelectedInvoice(view, invoices);
                    view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
                }
            }

            case "Add Item" -> {
                if (view.getFocusOwner() != null) {
                    InvoicesLineController.showNewItemDialog(view);
                }
            }
            
            case "Add Item Dialog OK" -> {
                InvoicesLineController.addNewItem(view, invoices);
                InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                InvoicesHeaderController.updateTableTotal(view, invoices);
                InvoicesLineController.updater(view, invoices, selectedRow);
                TablesController.loadInvoicesLineTable(view, invoices);

                int sizeOfinvoicesLinesForTheSelectedInvoice = invoices.get(view.getInvoiceTable().getSelectedRow()).getInvoicerow().size();
                view.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice - 1), (sizeOfinvoicesLinesForTheSelectedInvoice - 1));
                View.getAddItemDialog().setVisible(false);
            }

            case "Add Item Dialog Cancel" -> {
                View.getAddItemDialog().setVisible(false);
                view.getNewItemName().setText("");
                view.getNewItemPrice().setText("");
                view.getNewItemPriceSpinner().setValue((Object) 1);
            }

            case "Delete Item" -> {
                if (view.getFocusOwner() != null) {
                    InvoicesLineController.deleteItem(view, invoices);
                    InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                    InvoicesHeaderController.updateTableTotal(view, invoices);
                    InvoicesLineController.updater(view, invoices, selectedRow);
                    TablesController.loadInvoicesLineTable(view, invoices);

                    int sizeOfinvoicesLinesForTheSelectedInvoice = invoices.get(view.getInvoiceTable().getSelectedRow()).getInvoicerow().size();

                    if (sizeOfinvoicesLinesForTheSelectedInvoice > 0) {
                        view.getInvoicesLineTable().setRowSelectionInterval((sizeOfinvoicesLinesForTheSelectedInvoice - 1), (sizeOfinvoicesLinesForTheSelectedInvoice - 1));
                    }
                }
            }

            case "Cancel Any Changes" -> {
                if ((FileOperations.selectedInvoiceHeader != null) && (FileOperations.selectedInvoiceLine != null)) {
                    view.getInvoiceTable().getSelectionModel().removeListSelectionListener(invoiceTableListener);
                    invoices = fileOperations.readFile();
                    InvoicesHeaderController.calculateInvoiceTableTotal(invoices);
                    TablesController.loadInvoicesHeaderTable(view, invoices);

                    maxNumberOfExistedInvoices = 0;
                    fileOperations.getMaxNumberOfExistedInvoices(maxNumberOfExistedInvoices, invoices);
                    view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);

                    if (invoices.size() >= 1) {
                        view.getInvoiceTable().setRowSelectionInterval(0, 0);
                    }
                }
            }
            
            case "Save Items" -> {
                 try {
                    fileOperations.writeFile(Controller.invoices);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent evnt) {
        char price = evnt.getKeyChar();
        if (Character.isLetter(price) && !evnt.isAltDown() && evnt.isShiftDown() && evnt.isControlDown()) {
            evnt.consume();
        }
        
        if ((price == 'f') || (price == 'd')) {
            evnt.consume();
        }
        
        try {
            Float.parseFloat(view.getNewItemPrice().getText() + price);
        } catch (NumberFormatException e) {
            evnt.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
