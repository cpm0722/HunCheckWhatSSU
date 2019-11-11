package ssu.ssu.huncheckwhatssu.utilClass;

import android.media.Image;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class Book implements Parcelable {
    String isbn10;
    String isbn13;
    String title;
    String image;
    String author;
    int price;
    String publisher;
    String pubdate;
    String description;
    BookState bookState;

    public Book(String isbn10, String isbn13, String title, String image, String author, int price, String publisher, String pubdate, String description, BookState bookState) {
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.title = title;
        this.image = image;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.description = description;
        this.bookState = bookState;
    }

    protected Book(Parcel in) {
        isbn10 = in.readString();
        isbn13 = in.readString();
        title = in.readString();
        image = in.readString();
        author = in.readString();
        price = in.readInt();
        publisher = in.readString();
        pubdate = in.readString();
        description = in.readString();
        bookState = in.readParcelable(BookState.class.getClassLoader());
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
        dest.writeInt(price);
        dest.writeString(publisher);
        dest.writeString(pubdate);
        dest.writeString(description);
        dest.writeParcelable(bookState, flags);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn10='" + isbn10 + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", publisher='" + publisher + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", description='" + description + '\'' +
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
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
}