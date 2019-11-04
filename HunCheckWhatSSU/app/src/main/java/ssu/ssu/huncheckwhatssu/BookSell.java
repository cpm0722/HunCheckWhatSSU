package ssu.ssu.huncheckwhatssu;

public class BookSell {
    String title;
    String price;
    String seller;

    public BookSell(String title, String price, String seller) {
        this.title = title;
        this.price = price;
        this.seller = seller;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}

