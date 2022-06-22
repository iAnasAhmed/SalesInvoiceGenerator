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
    private InvoicesLineTableListener invoicesLineTableListener;
    private FileMenuItemsListener fileMenuItemsListener;

    private AddItemDialogWindowListener addItemDialogWindowListener;

    private CustomerNameTextFieldListener customerNameTextFieldListener;

    public Controller(InvoiceHeader invoiceHeader, InvoiceLine invoiceLine, View view) {
        this.invoiceHeader = invoiceHeader;
        this.invoiceLine = invoiceLine;
        this.view = view;

        turnOnAllActionListerners(view);

        loadTablesContents = new TablesController();

    }
    private void turnOnAllActionListerners(View view) {
        view.getLoadFile().addActionListener(fileMenuItemsListener);
        view.getLoadFile().setActionCommand("Load Files");

        view.getSaveFile().addActionListener(fileMenuItemsListener);
        view.getSaveFile().setActionCommand("Save File");

        view.getQuit().addActionListener(fileMenuItemsListener);
        view.getQuit().setActionCommand("Quit");

        view.getInvoiceTable().getSelectionModel().addListSelectionListener(invoiceTableListener);
        view.getInvoicesLineTable().getSelectionModel().addListSelectionListener(invoicesLineTableListener);
        View.getAddItemDialog().addWindowListener(addItemDialogWindowListener);
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

        view.getSaveButton().addActionListener(this);
        view.getSaveButton().setActionCommand("Save Items");

        view.getNewItemPrice().addKeyListener(this);

        view.getAddItemDialogCancel().addActionListener(this);
        view.getAddItemDialogCancel().setActionCommand("Add Item Dialog Cancel");

        view.getAddItemDialogOK().addActionListener(this);
        view.getAddItemDialogOK().setActionCommand("Add Item Dialog OK");

        view.getDeleteItemButton().addActionListener(this);
        view.getDeleteItemButton().setActionCommand("Delete Item");

        view.getCancelButton().addActionListener(this);
        view.getCancelButton().setActionCommand("Cancel Any Changes");
    }

    /**
     *
     * Implement All Listeners
     *
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
        }}

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

