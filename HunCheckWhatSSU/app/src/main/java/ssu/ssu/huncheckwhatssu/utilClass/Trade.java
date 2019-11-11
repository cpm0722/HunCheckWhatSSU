package ssu.ssu.huncheckwhatssu.utilClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Trade implements Parcelable {
    public enum TradeState {
        WAIT, PRECONTRACT, COMPLETE
    }

    Book book;
    Customer seller;
    Customer purchaser;
    TradeState tradeState;
    String tradePlace; // 주소
    Calendar tradeDate; // 거래일

    public Trade(Book book, Customer seller, Customer purchaser, TradeState tradeState, String tradePlace, Calendar tradeDate) {
        this.book = book;
        this.seller = seller;
        this.purchaser = purchaser;
        this.tradeState = tradeState;
        this.tradePlace = tradePlace;
        this.tradeDate = tradeDate;
    }

    protected Trade(Parcel in) {
        book = in.readParcelable(Book.class.getClassLoader());
        seller = in.readParcelable(Customer.class.getClassLoader());
        purchaser = in.readParcelable(Customer.class.getClassLoader());
        tradeState = TradeState.valueOf(in.readString());
        tradePlace = in.readString();
        tradeDate = (Calendar) in.readSerializable();
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

    public Calendar getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Calendar tradeDate) {
        this.tradeDate = tradeDate;
    }

    public static Creator<Trade> getCREATOR() {
        return CREATOR;
    }

    public String getTradeDate_typeOfString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        return simpleDateFormat.format(tradeDate.getTime());
    }

    public Calendar getTradeDate_typeOfCalendar() {
        return tradeDate;
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
        dest.writeSerializable(tradeDate);
    }

}
