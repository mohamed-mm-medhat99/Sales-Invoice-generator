
package salesinvoice.model;

/**
 *
 * @author yogy7
 */
public class invoiceLine {
    private String type;
    private int totalPrice;
    private int quantity;
    private invoiceHeader header; 


    public invoiceLine(String type, int totalPrice, int quantity) {
        this.type = type;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public invoiceLine(String type, int totalPrice, int quantity, invoiceHeader header) {
        this.type = type;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
        this.header = header;
    }

    public invoiceHeader getHeader() {
        return header;
    }
    
    public double getEachLineTotal()
    {
        return totalPrice * quantity;
    }
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "invoiceLine{" + "type=" + type + ", totalPrice=" + totalPrice + ", quantity=" + quantity + ", header=" + header + '}';
    }
    
    
    
    public String convertToOriginal()
    {
      return header.getNum() + type + "'" + totalPrice + "'" + quantity;    
    }
    
}
