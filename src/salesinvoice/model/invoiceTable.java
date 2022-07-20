/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesinvoice.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author yogy7
 */
public class invoiceTable extends AbstractTableModel{
    private ArrayList<invoiceHeader> headers;
    private String [] columnsTitles = {"No." , "Date" , "Customer" , "Total"}; 

    public invoiceTable(ArrayList<invoiceHeader> headers) {
        this.headers = headers;
    }

    
    
    @Override
    public int getRowCount() {
        return headers.size();
    }

    @Override
    public int getColumnCount() {
       return columnsTitles.length;
    }

    @Override
    public String getColumnName(int i) { 
        return columnsTitles[i]; 
    }
    
    @Override
    public Object getValueAt(int rows, int columns) {
       invoiceHeader invoice = headers.get(rows);
       
       switch (columns) {
           case 0: return invoice.getNum();
           case 1: return invoice.getDate();
           case 2: return invoice.getCustomerName();
           case 3: return invoice.getInvoiceTotalPrice();
           default : return "";
       }
    }

}
