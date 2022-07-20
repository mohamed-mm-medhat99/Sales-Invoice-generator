package salesinvoice.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import salesInvoice.view.invoiceDetailsDialog;
import salesInvoice.view.newInvoiceDialog;
import salesinvoice.model.invoiceDetailsTable;
import salesinvoice.model.invoiceHeader;
import salesinvoice.model.invoiceLine;
import salesinvoice.model.invoiceTable;
import salesinvoice.view.MainFrame;

/**
 *
 * @author yogy7
 */
public class controller implements ActionListener, ListSelectionListener {

    private MainFrame mainFrame;
    private newInvoiceDialog newInvoice;
    private invoiceDetailsDialog invoiceDetails;

    public controller(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    //switch cases to know which button the user pressed and which scenario to performe.
    @Override
    public void actionPerformed(ActionEvent ae) {
        /*controllerMethods methods = new controllerMethods();*/
        String command = ae.getActionCommand();
        System.out.println("Actions : " + command);

        switch (command) {
            case "Load File": {
                try {
                    loadFile();
                } catch (IOException ex) {
                    Logger.getLogger(controller.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
            case "Save File": {

                saveFile();

            }
            break;

            case "Create New Invoice":
                newInvoiceBtn();
                break;

            case "OKBUTTON": //for the new invoice dialog
                createInvoiceOK();
                break;

            case "cancelInvoiceBtn": //for the new invoice dialog
                cancelInvoiceBtn();
                break;

            case "Delete Invoice":
                deleteInvoice();
                break;

            case "Add new Item":
                System.out.println("works fine");
                newLineBtn();
                break;

            case "newLineBtn":
                System.out.println("works fine");
                createNewItemsBtn();
                break;

            case "cancelLineBtn":
                cancelLineBtn();
                System.out.println("cancel works fine");
                break;

            case "Delete Item":
                deleteItem();
                System.out.println("delete works fin");
                break;

        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedInvoiceIndex = mainFrame.getAllInvoicesTable().getSelectedRow();
        if (selectedInvoiceIndex != -1) {
            System.out.println("selected Row =" + mainFrame.getAllInvoicesTable().getSelectedRow());
            invoiceHeader selectedIndex = mainFrame.getHeaders().get(selectedInvoiceIndex);
            mainFrame.getNumLabel().setText("" + selectedIndex.getNum());
            mainFrame.getDateLabel().setText(selectedIndex.getDate());
            mainFrame.getCustomerLabel().setText(selectedIndex.getCustomerName());
            mainFrame.getTotalLable().setText("" + selectedIndex.getInvoiceTotalPrice());
            invoiceDetailsTable linesTable = new invoiceDetailsTable(selectedIndex.getLines());
            mainFrame.getInvoceItems().setModel(linesTable);
            linesTable.fireTableDataChanged();
        }
    }
    // load header and lines files
    public void loadFile() throws IOException {
        JFileChooser fileSelector = new JFileChooser();
        int selection = fileSelector.showOpenDialog(mainFrame);
        if (selection == JFileChooser.APPROVE_OPTION) {
            File headerfile = fileSelector.getSelectedFile();
            Path headerPath = Paths.get(headerfile.getAbsolutePath());
            List<String> headerLines = Files.readAllLines(headerPath);
           

            ArrayList<invoiceHeader> invoicesheaders = new ArrayList<>();

            for (String headerLine : headerLines) {
                String[] splitedHeader = headerLine.split(",");
                int invoiceNo = Integer.parseInt(splitedHeader[0]);
                String date = splitedHeader[1];
                String invoiceOwner = splitedHeader[2];

                invoiceHeader header = new invoiceHeader(invoiceNo, date, invoiceOwner);
                invoicesheaders.add(header);
            }

            selection = fileSelector.showOpenDialog(mainFrame);
            if (selection == JFileChooser.APPROVE_OPTION) {
                File detailsfile = fileSelector.getSelectedFile();
                Path detailedInvoicePath = Paths.get(detailsfile.getAbsolutePath());
                List<String> detailedLines = Files.readAllLines(detailedInvoicePath);
               

                ArrayList<invoiceLine> invoicesDetailedLines = new ArrayList<>();

                for (String detailedInvoice : detailedLines) {
                    String[] splitedLines = detailedInvoice.split(",");
                    int relatedInvoice = Integer.parseInt(splitedLines[0]);
                    String itemName = splitedLines[1];
                    int price = Integer.parseInt(splitedLines[2]);
                    int itemQuantity = Integer.parseInt(splitedLines[3]);

                    invoiceHeader inv_header = null;
                    for (invoiceHeader header : invoicesheaders) {
                        if (header.getNum() == relatedInvoice) {
                            inv_header = header;
                            break;
                        }
                    }

                    invoiceLine invoiceDetails = new invoiceLine(itemName, price, itemQuantity, inv_header);
                    inv_header.getLines().add(invoiceDetails);
                }
            }
            mainFrame.setHeaders(invoicesheaders);
            invoiceTable invoicesTable = new invoiceTable(invoicesheaders);
            mainFrame.setInvoicesTableModel(invoicesTable);
            mainFrame.getAllInvoicesTable().setModel(invoicesTable);
            mainFrame.getInvoicesTableModel().fireTableDataChanged();

        }
    }
    //save files after apply some changes
    private void saveFile() {
        ArrayList<invoiceHeader> allInvoices = mainFrame.getHeaders();
        String inv_headers = "";
        String inv_lines = "";
        for (invoiceHeader header : allInvoices) {
            String invoiceToOriginal = header.convertToOriginal();
            inv_headers += invoiceToOriginal;
            inv_headers += "\n";

            for (invoiceLine lines : header.getLines()) {
                String invoiceLineToOriginal = lines.convertToOriginal();
                inv_lines += invoiceLineToOriginal;
                inv_lines += "\n";
            }
        }
        try {
            JFileChooser file = new JFileChooser();
            int result = file.showSaveDialog(mainFrame);

            if (result == JFileChooser.APPROVE_OPTION) {
                File headersFile = file.getSelectedFile();

                FileWriter headersFileWriter = new FileWriter(headersFile);

                headersFileWriter.write(inv_headers);
                headersFileWriter.flush();
                headersFileWriter.close();

                result = file.showSaveDialog(mainFrame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File linesFile = file.getSelectedFile();
                    FileWriter linesFileWriter = new FileWriter(linesFile);

                    linesFileWriter.write(inv_lines);
                    linesFileWriter.flush();
                    linesFileWriter.close();

                }
            }
        } catch (Exception ex) {
        }
    }
    
    //delete an invoice and update the table
    private void deleteInvoice() {
        int selectedRow = mainFrame.getAllInvoicesTable().getSelectedRow();
        if (selectedRow != -1) {
            mainFrame.getHeaders().remove(selectedRow);
            mainFrame.getInvoicesTableModel().fireTableDataChanged();
        }
    }
    
    //delete a specific item from an invoice and update the table
    private void deleteItem() {
        int selectedRow = mainFrame.getInvoceItems().getSelectedRow();

        if (selectedRow != -1) {
            invoiceDetailsTable linesTableModel = (invoiceDetailsTable) mainFrame.getInvoceItems().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            mainFrame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void newInvoiceBtn() {
        newInvoice = new newInvoiceDialog(mainFrame);
        newInvoice.setVisible(true);
    }

    private void cancelInvoiceBtn() {
        newInvoice.setVisible(false);
        newInvoice.dispose();
        newInvoice = null;
    }

    private void newLineBtn() {
        invoiceDetails = new invoiceDetailsDialog(mainFrame);
        invoiceDetails.setVisible(true);
    }

    private void cancelLineBtn() {
        invoiceDetails.setVisible(false);
        invoiceDetails.dispose();
        invoiceDetails = null;
    }
    
    //ok button in the create new invoice dialog
    private void createInvoiceOK() {
        String invoiceDate = newInvoice.getInvoiveDateField().getText();
        String invoiceCustomerName = newInvoice.getCustomerNameField().getText();
        int num = mainFrame.getHighstInvoiceNumber();
        try {
            String[] parts = invoiceDate.split("-");
            if (parts.length < 3) {
                JOptionPane.showMessageDialog(mainFrame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(parts[0]);
                int month = Integer.parseInt(parts[1]);
                int year = Integer.parseInt(parts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(mainFrame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    invoiceHeader invoice = new invoiceHeader(num, invoiceDate, invoiceCustomerName);
                    mainFrame.getHeaders().add(invoice);
                    mainFrame.getInvoicesTableModel().fireTableDataChanged();
                    newInvoice.setVisible(false);
                    newInvoice.dispose();
                    newInvoice = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(mainFrame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    //ok button in the create new item in the invoice dialog
    private void createNewItemsBtn() {
        String name = invoiceDetails.getItemNameField().getText();
        String total = invoiceDetails.getPriceField().getText();
        String quantaty = invoiceDetails.getCountField().getText();

        int count = Integer.parseInt(quantaty);
        int itemsTotal = Integer.parseInt(total);

        int invoiceSelectednumber = mainFrame.getAllInvoicesTable().getSelectedRow();

        if (invoiceSelectednumber != -1) {
            invoiceHeader invoice = mainFrame.getHeaders().get(invoiceSelectednumber);
            invoiceLine line = new invoiceLine(name, count, itemsTotal, invoice);
            invoice.getLines().add(line);
            invoiceDetailsTable itemsTableModel = (invoiceDetailsTable) mainFrame.getInvoceItems().getModel();
            itemsTableModel.fireTableDataChanged();
            mainFrame.getInvoicesTableModel().fireTableDataChanged();
        }
        invoiceDetails.setVisible(false);
        invoiceDetails.dispose();
        invoiceDetails = null;
    }

}
