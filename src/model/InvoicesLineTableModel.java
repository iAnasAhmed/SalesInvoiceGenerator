package model;
import javax.swing.table.DefaultTableModel;

import view.View;
public class InvoicesLineTableModel {

    public static DefaultTableModel setInvoicesLineTableModel(View view) {
        DefaultTableModel newTable = new DefaultTableModel() {};
        newTable = (DefaultTableModel) view.getInvoicesLineTable().getModel();
        view.getInvoicesLineTable().setDefaultEditor(Object.class, null);
        return newTable;
    }
}

