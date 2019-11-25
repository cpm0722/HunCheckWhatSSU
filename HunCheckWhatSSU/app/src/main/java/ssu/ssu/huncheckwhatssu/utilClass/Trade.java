package ssu.ssu.huncheckwhatssu.utilClass;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import ssu.ssu.huncheckwhatssu.FirebaseCommunicator;


public class Trade implements Parcelable {

    public enum TradeState{
        WAIT, PRECONTRACT, COMPLETE;
    }

    String tradeId;
    Book book;
    //seller, purchaser는 Customer의 Id(String)
    String sellerId;
    String purchaserId;
    Customer seller;
    Customer purchaser;
    TradeState tradeState;
    Calendar loadDate; // 추가
    String tradePlace; // 주소
    String tradeDate; // 거래일

    public Trade(){}

    public Trade(Book book, String sellerId) {
        this.book = book;
        this.sellerId = sellerId;
        this.purchaserId = null;
        this.tradeState= TradeState.WAIT;
        this.tradePlace = null;
        this.tradeDate = null;
        this.seller = new Customer();
        seller.setId(sellerId);
        seller.setCustomerDataFromUID(null);
    }

    public Trade(Book book, String sellerId, String purchaserId, TradeState tradeState, String tradePlace, Calendar tradeDate) {
        this.book = book;
        this.sellerId = sellerId;
        this.purchaserId = purchaserId;
        this.tradeState = tradeState;
        this.tradePlace = tradePlace;
        this.seller = new Customer();
        seller.setId(sellerId);
        seller.setCustomerDataFromUID(null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        if (tradeDate  == null) {
            this.tradeDate = null;
        } else {
            this.tradeDate = simpleDateFormat.format(new Date(tradeDate.getTimeInMillis()));
        }

    }

    public Trade(Book book, Customer seller, Customer purchaser, TradeState tradeState, String tradePlace, Calendar tradeDate) {
        this.book = book;
        this.tradeState = tradeState;
        this.tradePlace = tradePlace;
        this.seller = seller;
        this.purchaser = purchaser;
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

    public String getTradeDate() {
        return this.tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeId(){ return tradeId; }

    public void setTradeId(String tradeId){ this.tradeId = tradeId;}

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

    public static Creator<Trade> getCREATOR() {
        return CREATOR;
    }

    public String getTradeStateForShowView() {

        switch (this.getTradeState()) {
            case WAIT:
                return "대기중";
            case PRECONTRACT:
                return "예약됨";
            case COMPLETE:
                return "완료";
        }

        return "";
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
        dest.writeParcelable(seller, flags);
        dest.writeParcelable(purchaser, flags);
        dest.writeString(tradeState.name());
        dest.writeString(tradePlace);
        dest.writeString(tradeDate);
    }

    public void toMap(Map<String, Object> result) {
        result.put("tradeId", this.tradeId);
        result.put("sellerId", this.sellerId);
        result.put("purchaserId", this.purchaserId);
        result.put("tradeState", this.tradeState);
        result.put("tradePlace", this.tradePlace);
        result.put("tradeDate", this.tradeDate);
        result.put("loadDate", this.loadDate);
    }
}