package ssu.ssu.huncheckwhatssu.utilClass;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class Trade implements Parcelable {

    public enum TradeState{
        WAIT, PRECONTRACT, COMPLETE;
    }

    Book book;
    Customer seller;
    Customer purchaser;
    TradeState tradeState;
    Calendar loadDate; // 추가
    String tradePlace; // 주소
    String tradeDate; // 거래일

    public Trade() {};

    public Trade(Book book, Customer customer) {
        this.book = book;
        this.seller = customer;
        this.purchaser = null;
        this.tradeState= TradeState.WAIT;
        this.tradePlace = null;
        this.tradeDate = null;
    }

    public Trade(Book book, Customer seller, Customer purchaser, TradeState tradeState, String tradePlace, Calendar tradeDate) {
        this.book = book;
        this.seller = seller;
        this.purchaser = purchaser;
        this.tradeState = tradeState;
        this.tradePlace = tradePlace;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        if (tradeDate  == null) {
            this.tradeDate = null;
        } else {
            this.tradeDate = simpleDateFormat.format(new Date(tradeDate.getTimeInMillis()));
        }

    }

    protected Trade(Parcel in) {
        book = in.readParcelable(Book.class.getClassLoader());
        seller = in.readParcelable(Customer.class.getClassLoader());
        purchaser = in.readParcelable(Customer.class.getClassLoader());
        tradeState = TradeState.valueOf(in.readString());
        tradePlace = in.readString();
        tradeDate = in.readString();
    }

    public Book getBook() {
        return book;
    }

    public TradeState getTradeState() {
        return tradeState;
    }

    public String getTradePlace() {
        return tradePlace;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getSeller() {
        return seller;
    }

    public void setSeller(Customer seller) {
        this.seller = seller;
    }

    public Customer getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(Customer purchaser) {
        this.purchaser = purchaser;
    }

    public void setTradeState(TradeState tradeState) {
        this.tradeState = tradeState;
    }

    public void setTradePlace(String tradePlace) {
        this.tradePlace = tradePlace;
    }

    public String getTradeDate() {
        return this.tradeDate;
    }

    public void _setTradeDate(Calendar tradeDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

        this.tradeDate = simpleDateFormat.format(new Date(tradeDate.getTimeInMillis()));
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public static Creator<Trade> getCREATOR() {
        return CREATOR;
    }

    public Calendar getTradeDate_typeOfCalendar() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(simpleDateFormat.parse(tradeDate));
            return c;
        } catch (Exception e) {}
        return null;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "book=" + book +
                ", seller=" + seller +
                ", purchaser=" + purchaser +
                ", tradeState=" + tradeState +
                ", tradePlace='" + tradePlace + '\'' +
                ", tradeDate=" + tradeDate +
                '}';
    }

    public static final Creator<Trade> CREATOR = new Creator<Trade>() {
        @Override
        public Trade createFromParcel(Parcel in) {
            return new Trade(in);
        }

        @Override
        public Trade[] newArray(int size) {
            return new Trade[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(book, flags);
        dest.writeParcelable(seller, flags);
        dest.writeParcelable(purchaser, flags);
        dest.writeString(tradeState.name());
        dest.writeString(tradePlace);
        dest.writeString(tradeDate);
    }

    public void toMap(Map<String, Object> result) {
        result.put("book", this.book);
        result.put("seller", this.seller.getId());
        result.put("purchaser", this.purchaser.getId());
        result.put("tradeState", this.tradeState);
        result.put("tradePlace", this.tradePlace);
        result.put("tradeDate", this.tradeDate);
        result.put("loadDate", this.loadDate);
    }



}
