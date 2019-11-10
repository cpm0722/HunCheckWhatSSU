package ssu.ssu.huncheckwhatssu.utilClass;

import android.os.Parcel;
import android.os.Parcelable;

public class Trade implements Parcelable {
    public enum TradeState {
        WAIT, PRECONTRACT, COMPLETE
    }

    Book book;
    Customer customer;
    TradeState tradeState;

    protected Trade(Parcel in) {
        book = in.readParcelable(Book.class.getClassLoader());
        customer = in.readParcelable(Customer.class.getClassLoader());
        tradeState = TradeState.valueOf(in.readString());
    }

    @Override
    public String toString() {
        return "Trade{" +
                "book=" + (book == null ? "null" : book.toString()) +
                ", customer=" + (customer == null ? "null" : customer.toString()) +
                ", tradeState=" + tradeState.name() +
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
        dest.writeParcelable(customer, flags);
        dest.writeString(tradeState.name());
    }



    public Trade(Book book, Customer customer, TradeState tradeState) {
        this.book = book;
        this.customer = customer;
        this.tradeState = tradeState;
    }
}
