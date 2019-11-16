package ssu.ssu.huncheckwhatssu.utilClass;


import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;


public class Trade implements Parcelable {

    public enum TradeState{
        WAIT, PRECONTRACT, COMPLETE;
    }

    String tradeId;
    Book book;
    //seller, purchaser는 Customer의 Id(String)
    String sellerId;
    String purchaserId;
    TradeState tradeState;
    String tradePlace; // 주소
    Calendar tradeDate; // 거래일

    public Trade(){}

    public Trade(Book book, String sellerId) {
        this.book = book;
        this.sellerId = sellerId;
        this.purchaserId = null;
        this.tradeState= TradeState.WAIT;
        this.tradePlace = null;
        this.tradeDate = null;
    }

    public Trade(Book book, String sellerId, String purchaserId, TradeState tradeState, String tradePlace, Calendar tradeDate) {
        this.book = book;
        this.sellerId = sellerId;
        this.purchaserId = purchaserId;
        this.tradeState = tradeState;
        this.tradePlace = tradePlace;
        this.tradeDate = tradeDate;
    }

    protected Trade(Parcel in) {
        book = in.readParcelable(Book.class.getClassLoader());
        sellerId = in.readString();
        purchaserId = in.readString();
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

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getPurchaserId() {
        return purchaserId;
    }

    public void setPurchaserId(String purchaserId) {
        this.purchaserId = purchaserId;
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

    public String getTradeId(){ return tradeId; }

    public void setTradeId(String tradeId){ this.tradeId = tradeId;}

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
                ", sellerId=" + sellerId +
                ", purchaserId=" + purchaserId +
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
        dest.writeString(sellerId);
        dest.writeString(purchaserId);
        dest.writeString(tradeState.name());
        dest.writeString(tradePlace);
        dest.writeSerializable(tradeDate);
    }

    public void toMap(Map<String, Object> result) {
        result.put("tradeId", this.tradeId);
        result.put("sellerId", this.sellerId);
        result.put("purchaserId", this.purchaserId);
        result.put("tradeState", this.tradeState);
        result.put("tradePlace", this.tradePlace);
        result.put("tradeDate", this.tradeDate);
    }
}
