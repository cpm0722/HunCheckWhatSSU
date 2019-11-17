package ssu.ssu.huncheckwhatssu.utilClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;

public class Customer implements Parcelable {
    // 사용자 고유 번호
    String id;
    String name;
    String phoneNumber;
    String address;
    double creditRating;
    ArrayList<String> sellList;
    ArrayList<String> buyList;

    public Customer(){}

    public Customer(String id, String name, String phoneNumber, String address, float creditRating) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.creditRating = creditRating;
    }


    protected Customer(Parcel in) {
        id = in.readString();
        name = in.readString();
        phoneNumber = in.readString();
        address = in.readString();
        creditRating = in.readFloat();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(phoneNumber);
        dest.writeString(address);
        dest.writeDouble(creditRating);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void toMap(Map<String, Object> result) {
        result.put("Uid", this.id);
        result.put("Name", this.name);
        result.put("PhoneNumber", this.phoneNumber);
        result.put("Address", this.address);
        result.put("CreditRating", this.creditRating);
        result.put("sellList",sellList);
        result.put("buyList",buyList);
        return;
    }

    public double getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(double creditRating) {
        this.creditRating = creditRating;
    }

    public Customer(DataSnapshot dataSnapshot){
        sellList = new ArrayList<>();
        buyList = new ArrayList<>();
        this.id = dataSnapshot.child("Uid").getValue(String.class);
        this.name = dataSnapshot.child("Name").getValue(String.class);
        this.phoneNumber = dataSnapshot.child("PhoneNumber").getValue(String.class);
        this.address = dataSnapshot.child("Address").getValue(String.class);
        this.creditRating = dataSnapshot.child("CreditRating").getValue(Double.class);
        DataSnapshot tempSnapshot = dataSnapshot.child("sellList");
        for(DataSnapshot sellSnapshot : tempSnapshot.getChildren()){
            sellList.add(sellSnapshot.getValue(String.class));
        }
        tempSnapshot = dataSnapshot.child("buyList");
        for(DataSnapshot buySnapshot : tempSnapshot.getChildren()){
            buyList.add(buySnapshot.getValue(String.class));
        }
    }
}
