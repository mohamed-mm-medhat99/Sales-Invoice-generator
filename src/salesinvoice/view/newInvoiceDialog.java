/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesInvoice.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import salesinvoice.view.MainFrame;

/**
 *
 * @author yogy7
 */
public class newInvoiceDialog extends JDialog {
    private JTextField customerName;
    private JTextField invoiceDate;
    private JLabel customerNameLbl;
    private JLabel invoiceDateLbl;
    private JButton okBtn;
    private JButton cancelBtn;

    public newInvoiceDialog(MainFrame mainFrame) {
        customerNameLbl = new JLabel("customerName:");
        customerName = new JTextField(20);
        invoiceDateLbl = new JLabel("invoiceDate:");
        invoiceDate = new JTextField(20);
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("OKBUTTON");
        cancelBtn.setActionCommand("cancelInvoiceBtn");
        
        
        okBtn.addActionListener(mainFrame.getControl());
        cancelBtn.addActionListener(mainFrame.getControl());
        
        
        setLayout(new GridLayout(3, 2));
        
        add(invoiceDateLbl);
        add(invoiceDate);
        add(customerNameLbl);
        add(customerName);
        add(okBtn);
        add(cancelBtn);
        
        pack();
        
    }

    public JTextField getCustomerNameField() {
        return customerName;
    }

    public JTextField getInvoiveDateField() {
        return invoiceDate;
    }
    
}
