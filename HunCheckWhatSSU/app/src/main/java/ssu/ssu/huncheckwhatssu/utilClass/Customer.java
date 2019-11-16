package ssu.ssu.huncheckwhatssu.utilClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;

public class Customer implements Parcelable {
    // 사용자 고유 번호
    String id;
    String name;
    String phoneNumber;
    String address;
    float creditRating;

    public Customer(){}

    public Customer(String id) {
        this.id = id;
    }

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
        dest.writeFloat(creditRating);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String _getName() {
        return name;
    }

    public void _setName(String name) {
        this.name = name;
    }

    public String _getPhoneNumber() {
        return phoneNumber;
    }

    public void _setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String _getAddress() {
        return address;
    }

    public void _setAddress(String address) {
        this.address = address;
    }

    public float _getCreditRating() {
        return creditRating;
    }

    public void _setCreditRating(float creditRating) {
        this.creditRating = creditRating;
    }
}
