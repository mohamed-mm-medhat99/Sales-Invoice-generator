package salesinvoice.model;

import java.util.ArrayList;

/**
 *
 * @author yogy7
 */
public class invoiceHeader {
    private int num;
    private String date;
    private String customerName;
    private ArrayList<invoiceLine> lines;

    public invoiceHeader() {
    }

    public invoiceHeader(int num, String date, String customerName) {
        this.num = num;
        this.date = date;
        this.customerName = customerName;
    }
    
   
    public ArrayList<invoiceLine> getLines() {
        
        if(lines == null){
        lines = new ArrayList<>();
        }
        return lines;
    }
    
     public double getInvoiceTotalPrice()
    {
        double totalOfInvoice = 0.0 ;
        for (invoiceLine lines: getLines() )
        {
            totalOfInvoice += lines.getEachLineTotal();
        }
        return totalOfInvoice;
    }
     
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return "invoiceHeader{" + "num=" + num + ", date=" + date + ", customerName=" + customerName + '}';
    }
    
    public String convertToOriginal()
    {
      return num + "'" + date + "'" + customerName;    
    }
    
    
}
