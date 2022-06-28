package view;

import controller.Controller;
import java.io.FileNotFoundException;
import model.InvoiceHeader;
import model.InvoiceLine;

/**
 *
 * @author Anas
 */
public class App {
    public static void main(String[] args) throws FileNotFoundException {
        View view = new View();
        view.setVisible(true);
        view.setLocations();
        view.setResizable(false);
        view.loadFiles();

        Controller controller = new Controller(new InvoiceHeader(), new InvoiceLine(), view);
    }
}
