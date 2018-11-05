package tickets.Model;

public class Ticket {

    private int ticket_id;

    private int event_id;

    private int available;

    private String section;

    private String row;

    private int price;

    private int seller_id;


    public int getTicketID() { return ticket_id;}

    public void setTicketID(int ticket_id) { this.ticket_id = ticket_id;}

    public int getEventID() { return event_id;}

    public void setEventID(int event_id) {this.event_id = event_id;}

    public int getAvailable() { return available;}

    public void setAvailable(int available) {this.available = available;}

    public void setSection(String section) {this.section = section;}

    public String getSection() { return section;}

    public void setRow(String row) {this.row = row;}

    public String getRow() { return row;}

    public void setPrice(int price) { this.price = price;}

    public int getPrice() {return price;}

    public int getSellerID() { return seller_id;}

    public void setSellerID(int seller_id) {this.seller_id = seller_id;}


}
