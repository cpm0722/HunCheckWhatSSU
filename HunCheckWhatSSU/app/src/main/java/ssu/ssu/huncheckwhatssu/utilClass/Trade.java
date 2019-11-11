package ssu.ssu.huncheckwhatssu.utilClass;


public class Trade {
    Book book;
    Customer seller;
    Customer buyer;
    TradeState state;

    enum TradeState {
        WAIT, PRECONTRACT, COMPLETE
    }

    public Trade() {

    }

    public Trade(Book book, Customer customer) {
        this.book = book;
        this.seller = customer;
        this.buyer = null;
        this.state = TradeState.WAIT;
    }

    public Book getBook() {
        return this.book;
    }

    public Customer getSeller() {
        return this.seller;
    }

    public Customer getBuyer() {
        return this.buyer;}

    public void setBook(Book book) {
        this.book = book;
        return;
    }

    public void setSeller(Customer customer) {
        this.seller = customer;
        return;
    }

    public void setBuyer(Customer customer){
        this.buyer = customer;
        return;
    }


}
