package ssu.ssu.huncheckwhatssu.utilClass;

import android.media.Image;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class Book implements Parcelable {
    // 판매하려는 책의 기본정보
    String isbn10;
    String isbn13;
    String title;
    String image;
    String author;
    int original_Price;
    String publisher;
    String pubDate;
    String description;

    // 고객의 책에대한 정보
    String college_id;
    String department_id;
    String subject_id;
    BookState bookState;
    int sellingPrice;
    //

    public Book(){}

    public Book(String isbn10, String isbn13, String title, String image, String author, int price, String publisher, String pubDate, String description, BookState bookState) {
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.title = title;
        this.image = image;
        this.author = author;
        this.original_Price = price;
        this.publisher = publisher;
        this.pubDate = pubDate;
        this.description = description;
        this.bookState = bookState;
    }

    public Book(String isbn10, String isbn13, String title, String image, String author, int sellingPrice, int price, String publisher, String pubDate, String description, String college_id, String department_id, String subject_id, BookState bookState) {
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.title = title;
        this.image = image;
        this.author = author;
        this.original_Price = price;
        this.sellingPrice = sellingPrice;
        this.publisher = publisher;
        this.pubDate = pubDate;
        this.description = description;
        this.college_id = college_id;
        this.department_id = department_id;
        this.subject_id = subject_id;
        this.bookState = bookState;
    }

    protected Book(Parcel in) {
        isbn10 = in.readString();
        isbn13 = in.readString();
        title = in.readString();
        image = in.readString();
        author = in.readString();
        original_Price = in.readInt();
        sellingPrice = in.readInt();
        publisher = in.readString();
        pubDate = in.readString();
        description = in.readString();
        bookState = in.readParcelable(BookState.class.getClassLoader());
        college_id = in.readString();
        department_id = in.readString();
        subject_id = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(isbn10);
        dest.writeString(isbn13);
        dest.writeString(title);
        dest.writeString(image);
        dest.writeString(author);
        dest.writeInt(original_Price);
        dest.writeInt(sellingPrice);
        dest.writeString(publisher);
        dest.writeString(pubDate);
        dest.writeString(description);
        dest.writeParcelable(bookState, flags);
        dest.writeString(college_id);
        dest.writeString(department_id);
        dest.writeString(subject_id);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", price=" + original_Price +
                ", publisher='" + publisher + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", description='" + description + '\'' +
                ", college_id='" + college_id + '\'' +
                ", department_id='" + department_id + '\'' +
                ", subject_id='" + subject_id + '\'' +
                ", bookState=" + (bookState == null ? "null" : bookState.toString()) +
                '}';
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getOriginal_Price() {
        return original_Price;
    }

    public void setOriginal_Price(int original_Price) {
        this.original_Price = original_Price;
    }

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookState getBookState() {
        return bookState;
    }

    public void setBookState(BookState bookState) {
        this.bookState = bookState;
    }

    public String getCollege_id() {
        return college_id;
    }

    public void setCollege_id(String college_id) {
        this.college_id = college_id;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }



}

