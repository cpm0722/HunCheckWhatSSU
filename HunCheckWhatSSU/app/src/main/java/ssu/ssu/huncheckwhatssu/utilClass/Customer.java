package ssu.ssu.huncheckwhatssu.utilClass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Customer implements Parcelable {
    // 사용자 고유 번호
    String id;
    String name;
    String phoneNumber;
    String address;
    float creditRating;

    public Customer(){
    }

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

//    (이거해라빨간줄~~)
    public void setCustomerDataFromUID() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("tb_customer");
        Query query = reference.equalTo(this.getId());

        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot != null && dataSnapshot.exists()) {
                    Customer customer = dataSnapshot.child("customer").getValue(Customer.class);

                    _setName(customer._getName());
                    _setAddress(customer._getAddress());
                    _setCreditRating(customer._getCreditRating());
                    _setPhoneNumber(customer._getPhoneNumber());
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

    public void toMap(Map<String, Object> result) {
        result.put("id", this.id);
        result.put("name", this.name);
        result.put("phoneNumber", this.phoneNumber);
        result.put("address", this.address);
        return;
    }

}
