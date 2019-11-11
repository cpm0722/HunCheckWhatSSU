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
        result.put("id", this.id);
        result.put("name", this.name);
        result.put("phoneNumber", this.phoneNumber);
        result.put("address", this.address);
        return;
    }

    public float getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(float creditRating) {
        this.creditRating = creditRating;
    }

}
