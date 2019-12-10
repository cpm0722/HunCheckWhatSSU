package ssu.ssu.huncheckwhatssu.utilClass;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import ssu.ssu.huncheckwhatssu.FirebaseCommunicator;


public class Trade implements Parcelable {

    public enum TradeState {
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
    String upLoadDate;

    String tradePlace; // 주소
    String tradeDate; // 거래일
    int sellingPrice;

    double latitude;
    double longitude;

    double sellerRate;
    double purchaserRate;
    String sellerComment;
    String purchaserComment;


    public Trade() {
    }

    public Trade(Book book, String sellerId) {
        this.book = book;
        this.sellerId = sellerId;
        this.purchaserId = null;
        this.tradeState = TradeState.WAIT;
        this.tradePlace = null;
        this.tradeDate = null;

        this.loadDate = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        this.upLoadDate = simpleDateFormat.format(loadDate.getTimeInMillis());

        this.tradePlace = null;
        this.tradeDate = null;
        this.sellingPrice = 0;

        this.latitude = 0;
        this.longitude = 0;

        this.sellerRate = -1;
        this.purchaserRate = -1;
        this.sellerComment = null;
        this.purchaserComment = null;

        this.seller = new Customer();
        seller.setId(sellerId);
        seller.setCustomerDataFromUID(null);
    }

    public Trade(Book book, String sellerId, int sellingPrice) {
        this(book, sellerId);
        this.sellingPrice = sellingPrice;
    }

    public Trade(Book book, String sellerId, String purchaserId, TradeState tradeState, String tradePlace, Calendar tradeDate) {
        this.book = book;
        this.sellerId = sellerId;
        this.purchaserId = purchaserId;
        this.tradeState = tradeState;
        this.tradePlace = tradePlace;
        this.sellerRate = -1;
        this.purchaserRate = -1;
        this.sellerComment = null;
        this.purchaserComment = null;
        this.seller = new Customer();
        seller.setId(sellerId);
        seller.setCustomerDataFromUID(null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        if (tradeDate == null) {
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
        this.sellerRate = -1;
        this.purchaserRate = -1;
        this.sellerComment = null;
        this.purchaserComment = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        if (tradeDate == null) {
            this.tradeDate = null;
        } else {
            this.tradeDate = simpleDateFormat.format(new Date(tradeDate.getTimeInMillis()));
        }
    }

    protected Trade(Parcel in) {

        tradeId = in.readString();
        book = in.readParcelable(Book.class.getClassLoader());
        sellerId = in.readString();
        purchaserId = in.readString();
        seller = in.readParcelable(Customer.class.getClassLoader());
        purchaser = in.readParcelable(Customer.class.getClassLoader());
        tradeState = TradeState.valueOf(in.readString());
        upLoadDate = in.readString();
        tradePlace = in.readString();
        tradeDate = in.readString();
        sellingPrice = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        sellerRate = in.readDouble();
        purchaserRate = in.readDouble();
        sellerComment = in.readString();
        purchaserComment = in.readString();
    }
    public void Copy(Trade src) {

        this.tradeId = src.getTradeId();
        this.book = src.getBook();
        this.sellerId = src.getSellerId();
        this.purchaserId = src.getPurchaserId();
        this.seller = src.getSeller();
        this.purchaser = src.getPurchaser();
        this.tradeState = src.getTradeState();
        this.upLoadDate = src.getUpLoadDate();
        this.tradePlace = src.getTradePlace();
        this.tradeDate = src.getTradeDate();
        this.sellingPrice = src.getSellingPrice();
        this.latitude = src.getLatitude();
        this.longitude = src.getLongitude();
        this.sellerRate = src.getSellerRate();
        this.purchaserRate = src.getPurchaserRate();
        this.sellerComment = src.getSellerComment();
        this.purchaserComment = src.getPurchaserComment();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tradeId);
        dest.writeParcelable(book, flags);
        dest.writeString(sellerId);
        dest.writeString(purchaserId);
        dest.writeParcelable(seller, flags);
        dest.writeParcelable(purchaser, flags);
        dest.writeString(tradeState.name());
        dest.writeString(upLoadDate);
        dest.writeString(tradePlace);
        dest.writeString(tradeDate);
        dest.writeInt(sellingPrice);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeDouble(sellerRate);
        dest.writeDouble(purchaserRate);
        dest.writeString(sellerComment);
        dest.writeString(purchaserComment);
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

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Calendar getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Calendar loadDate) {
        this.loadDate = loadDate;
    }

    public static Creator<Trade> getCREATOR() {
        return CREATOR;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getUpLoadDate() {
        return upLoadDate;
    }

    public void setUpLoadDate(String upLoadDate) {
        this.upLoadDate = upLoadDate;
    }

    public double getSellerRate() {
        return sellerRate;
    }

    public void setSellerRate(double sellerRate) {
        this.sellerRate = sellerRate;
    }

    public double getPurchaserRate() {
        return purchaserRate;
    }

    public void setPurchaserRate(double purchaserRate) {
        this.purchaserRate = purchaserRate;
    }

    public String getSellerComment() {
        return sellerComment;
    }

    public void setSellerComment(String sellerComment) {
        this.sellerComment = sellerComment;
    }

    public String getPurchaserComment() {
        return purchaserComment;
    }

    public void setPurchaserComment(String purchaserComment) {
        this.purchaserComment = purchaserComment;
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
    public void convertTradeDate(){

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

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId='" + tradeId + '\'' +
                ", book=" + book +
                ", sellerId='" + sellerId + '\'' +
                ", purchaserId='" + purchaserId + '\'' +
                ", seller=" + seller +
                ", purchaser=" + purchaser +
                ", tradeState=" + tradeState +
                ", loadDate=" + loadDate +
                ", upLoadDate='" + upLoadDate + '\'' +
                ", tradePlace='" + tradePlace + '\'' +
                ", tradeDate='" + tradeDate + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", sellerRate=" + sellerRate +
                ", purchaserRate=" + purchaserRate +
                ", sellerComment='" + sellerComment + '\'' +
                ", purchaserComment='" + purchaserComment + '\'' +
                '}';
    }

    public void toMap(Map<String, Object> result) {
        result.put("book", book);
        result.put("tradeId", this.tradeId);
        result.put("sellerId", this.sellerId);
        result.put("purchaserId", this.purchaserId);
        result.put("tradeState", this.tradeState);
        result.put("tradePlace", this.tradePlace);
        result.put("tradeDate", this.tradeDate);
        result.put("upLoadDate", this.upLoadDate);
        result.put("sellingPrice",this.sellingPrice);
        if(this.latitude != 0)
            result.put("latitude",this.latitude);
        if(this.longitude != 0 )
            result.put("longitude",this.longitude);
        result.put("sellerRate", this.sellerRate);
        result.put("purchaserRate", this.purchaserRate);
        result.put("sellerComment", this.sellerComment);
        result.put("purchaserComment", this.purchaserComment);

    }
}

