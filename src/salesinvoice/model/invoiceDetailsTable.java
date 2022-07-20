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
public class invoiceDetailsTable extends AbstractTableModel{
    private ArrayList<invoiceLine> lines;
    private String [] linesColumnsTitles = {"No." , "itemName" , "itemPrice" , "Count", "itemsTotal"}; 

    
    
    public invoiceDetailsTable(ArrayList<invoiceLine> liner) {
        this.lines = liner;
    }

    

    
    public ArrayList<invoiceLine> getLines ()
    {
        return lines;
    }
    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return linesColumnsTitles.length;
    }

    @Override
    public String getColumnName(int i) {
        return linesColumnsTitles[i];
    }
    
    @Override
    public Object getValueAt(int rows, int columns) {
        invoiceLine singleLine = lines.get(rows);
        
        switch (columns)
        {
            case 0 : return singleLine.getHeader().getNum();
            case 1 : return singleLine.getType();
            case 2 : return singleLine.getTotalPrice();
            case 3 : return singleLine.getQuantity();
            case 4 : return singleLine.getEachLineTotal();
            default : return "";
        }
    }
    
}
