package ssu.ssu.huncheckwhatssu.utilClass;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import ssu.ssu.huncheckwhatssu.FirebaseCommunicator;

public class Trade {
    Book book;
    Customer seller;
    TradeState state;

    enum TradeState{
        WAIT, PRECONTRACT, COMPLETE
    }

    Trade(Book book, Customer customer){
        this.book = book;
        this.seller = customer;
        this.state = TradeState.WAIT;
    }

    public Book getBook(){
        return this.book;
    }

    public Customer getSeller(){
        return this.seller;
    }

    public void setBook(Book book){
        this.book = book;
        return;
    }

    public void setSeller(Customer customer){
        this.seller = customer;
        return;
    }



}
