package ssu.ssu.huncheckwhatssu.utilClass;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;

import com.google.firebase.database.DataSnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Customer implements Parcelable {
    // 사용자 고유 번호
    String id;
    String name;
    String phoneNumber;
    String address;
    String nickName;
    String major;
    int grade;

    double creditRating;
    int evaluationNumber;
    ArrayList<String> sellList;
    ArrayList<String> buyList;

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
        creditRating = in.readDouble();
        sellList = in.readArrayList(String.class.getClassLoader());
        buyList = in.readArrayList(String.class.getClassLoader());
    }

    public void setCustomerDataFromUID(final RecyclerView.Adapter adapter) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("customer/" + this.getId());

        Log.d("JS", "setCustomerDataFromUID: " + this.getId());

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("JS", "onDataChange: " + dataSnapshot.toString());

                if (dataSnapshot.getValue() != null) {
                    Customer customer = new Customer(dataSnapshot);

                    Log.d("JS", "onDataChange: " + customer.toString());

                    setName(customer.getName());
                    setAddress(customer.getAddress());
                    setCreditRating(customer.getCreditRating());
                    setPhoneNumber(customer.getPhoneNumber());

                } else {
                    // 정보 없을때 (정상적인 절차를 걸쳐서 사용하게 되면 생길 수 없는 경우)
                    setName("정보 없음");
                }
                if (adapter != null)
                    adapter.notifyDataSetChanged();
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
        dest.writeDouble(creditRating);
        dest.writeStringList(sellList);
        dest.writeStringList(buyList);
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

    public String getNickName() { return nickName; }

    public void setNickName(String nickName) { this.nickName = nickName; }

    public String getMajor() { return major; }

    public void setMajor(String major) { this.major = major; }

    public int getGrade() { return grade; }

    public void setGrade(int grade) { this.grade = grade; }

    public int getEvaluationNumber() {
        return evaluationNumber;
    }

    public void setEvaluationNumber(int evaluationNumber) {
        this.evaluationNumber = evaluationNumber;
    }

    public void toMap(Map<String, Object> result) {
        result.put("Uid", this.id);
        result.put("Name", this.name);
        result.put("PhoneNumber", this.phoneNumber);
        result.put("Address", this.address);
        result.put("NickName",this.nickName);
        result.put("Major",this.major);
        result.put("Grade",this.grade);
        result.put("sellList",sellList);
        result.put("buyList",buyList);
        if(evaluationNumber != 0 ) {
            result.put("evaluationNumber", this.evaluationNumber);
            result.put("CreditRating", this.creditRating);
        }
        return;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", nickName='" + nickName + '\'' +
                ", major='" + major + '\'' +
                ", grade=" + grade +
                ", creditRating=" + creditRating +
                ", sellList=" + sellList +
                ", buyList=" + buyList +
                '}';
    }

    public double getCreditRating() {
        return creditRating;
    }

    public void setCreditRating(double creditRating) {
        this.creditRating = creditRating;
    }

    public Customer(DataSnapshot dataSnapshot) {
        sellList = new ArrayList<>();
        buyList = new ArrayList<>();

        this.id = dataSnapshot.child("Uid").getValue(String.class);
        this.name = dataSnapshot.child("Name").getValue(String.class);
        this.phoneNumber = dataSnapshot.child("PhoneNumber").getValue(String.class);
        this.address = dataSnapshot.child("Address").getValue(String.class);
        Double tempdouble;
        if((tempdouble = dataSnapshot.child("CreditRating").getValue(Double.class))!=null)
            this.creditRating = tempdouble;
        else
            this.creditRating = 5.0;
        this.nickName = dataSnapshot.child("NickName").getValue(String.class);
        this.major = dataSnapshot.child("Major").getValue(String.class);
        Integer tempint;
        if((tempint =dataSnapshot.child("Grade").getValue(Integer.class))!=null)
            this.grade = tempint;
        if((tempint = dataSnapshot.child("evaluationNumber").getValue(Integer.class))!=null)
            this.evaluationNumber = tempint;
        else
            this.evaluationNumber = 0;

        DataSnapshot tempSnapshot = dataSnapshot.child("sellList");
        for (DataSnapshot sellSnapshot : tempSnapshot.getChildren()) {
            sellList.add(sellSnapshot.getValue(String.class));
        }
        tempSnapshot = dataSnapshot.child("buyList");

        for(DataSnapshot buySnapshot : tempSnapshot.getChildren()){
            buyList.add(buySnapshot.getValue(String.class));
        }
    }
}
