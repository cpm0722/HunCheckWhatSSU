package ssu.ssu.huncheckwhatssu.utilClass;

import android.media.Image;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Book {
    // 책의 고유번호
    long ISBN;
    long key;
    // --
    String name;
    String author;
    Calendar publicationDate;
    long original_price;
    long sellPrice;
    Image bookCoverImg;
    BookState bookState;

    public Book(long ISBN, long key, String name, String author, Calendar publicationDate, long original_price, long sellPrice, Image bookCoverImg, BookState bookState) {
        this.ISBN = ISBN;
        this.key = key;
        this.name = name;
        this.author = author;
        this.publicationDate = publicationDate;
        this.original_price = original_price;
        this.sellPrice = sellPrice;
        this.bookCoverImg = bookCoverImg;
        this.bookState = bookState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Calendar getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Calendar publicationDate) {
        this.publicationDate = publicationDate;
    }

    public long getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(long original_price) {
        this.original_price = original_price;
    }

    public long getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Image getBookCoverImg() {
        return bookCoverImg;
    }

    public void setBookCoverImg(Image bookCoverImg) {
        this.bookCoverImg = bookCoverImg;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("ISBN", this.ISBN);
        result.put("key", this.key);
        result.put("name", this.name);
        result.put("author", this.author);
        result.put("publicationDate", this.publicationDate);
        result.put("original_price", this.original_price);
        result.put("sellPrice", this.sellPrice);
        result.put("bookState", this.bookState);
        return result;
    }
}

class BookState {
    enum bookState {
        BEST,GOOD,BAD,WORST;
    }

    bookState bookState01;
    bookState bookState02;
    bookState bookState03;
    bookState bookState04;
    bookState bookState05;
    bookState bookState06;

    public BookState(bookState bookState01, bookState bookState02, bookState bookState03, bookState bookState04, bookState bookState05, bookState bookState06) {
        this.bookState01 = bookState01;
        this.bookState02 = bookState02;
        this.bookState03 = bookState03;
        this.bookState04 = bookState04;
        this.bookState05 = bookState05;
        this.bookState06 = bookState06;
    }

    public bookState getBookState01() {
        return bookState01;
    }

    public void setBookState01(bookState bookState01) {
        this.bookState01 = bookState01;
    }

    public bookState getBookState02() {
        return bookState02;
    }

    public void setBookState02(bookState bookState02) {
        this.bookState02 = bookState02;
    }

    public bookState getBookState03() {
        return bookState03;
    }

    public void setBookState03(bookState bookState03) {
        this.bookState03 = bookState03;
    }

    public bookState getBookState04() {
        return bookState04;
    }

    public void setBookState04(bookState bookState04) {
        this.bookState04 = bookState04;
    }

    public bookState getBookState05() {
        return bookState05;
    }

    public void setBookState05(bookState bookState05) {
        this.bookState05 = bookState05;
    }

    public bookState getBookState06() {
        return bookState06;
    }

    public void setBookState06(bookState bookState06) {
        this.bookState06 = bookState06;
    }
}