package Orders;

/**
 * A class to hold details about a order
 */
public class Order {
    private String type;
    private float price;
    private float quantity;

    public String getType(){
        return this.type;
    }

    public float getPrice(){
        return this.price;
    }

    public float getQuantity(){
        return this.quantity;
    }

    public void setType(String type){ this.type = type; }

    public void setPrice(int price){
        this.price = price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}