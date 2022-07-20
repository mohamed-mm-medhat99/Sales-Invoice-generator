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
public class invoiceDetailsDialog extends JDialog{
    private JTextField itemName;
    private JTextField itemCount;
    private JTextField itemPrice;
    private JLabel itemNameLbl;
    private JLabel itemCountLbl;
    private JLabel itemPriceLbl;
    private JButton okBtn;
    private JButton cancelBtn;
    
    public invoiceDetailsDialog(MainFrame mainFrame) {
        itemName = new JTextField(20);
        itemNameLbl = new JLabel("Name");
        
        itemCount = new JTextField(20);
        itemCountLbl = new JLabel("Counter");
        
        itemPrice = new JTextField(20);
        itemPriceLbl = new JLabel("Price");
        
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");
        
        okBtn.setActionCommand("newLineBtn");
        cancelBtn.setActionCommand("cancelLineBtn");
        
        okBtn.addActionListener(mainFrame.getControl());
        cancelBtn.addActionListener(mainFrame.getControl());
        
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLbl);
        add(itemName);
        add(itemCountLbl);
        add(itemCount);
        add(itemPriceLbl);
        add(itemPrice);
        add(okBtn);
        add(cancelBtn);
        
        pack();
    }

    public JTextField getItemNameField() {
        return itemName;
    }

    public JTextField getCountField() {
        return itemCount;
    }

    public JTextField getPriceField() {
        return itemPrice;
    }
}
