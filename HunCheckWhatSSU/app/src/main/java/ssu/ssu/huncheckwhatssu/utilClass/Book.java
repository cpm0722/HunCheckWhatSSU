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

class BookState implements Parcelable {
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

    @Override
    public String toString() {
        return "BookState{" +
                "bookState01=" + bookState01.name() +
                ", bookState02=" + bookState02.name() +
                ", bookState03=" + bookState03.name() +
                ", bookState04=" + bookState04.name() +
                ", bookState05=" + bookState05.name() +
                ", bookState06=" + bookState06.name() +
                '}';
    }

    protected BookState(Parcel in) {
        bookState01 = bookState.valueOf(in.readString());
        bookState02 = bookState.valueOf(in.readString());
        bookState03 = bookState.valueOf(in.readString());
        bookState04 = bookState.valueOf(in.readString());
        bookState05 = bookState.valueOf(in.readString());
        bookState06 = bookState.valueOf(in.readString());
    }

    public static final Creator<BookState> CREATOR = new Creator<BookState>() {
        @Override
        public BookState createFromParcel(Parcel in) {
            return new BookState(in);
        }

        @Override
        public BookState[] newArray(int size) {
            return new BookState[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookState01.name());
        dest.writeString(bookState02.name());
        dest.writeString(bookState03.name());
        dest.writeString(bookState04.name());
        dest.writeString(bookState05.name());
        dest.writeString(bookState06.name());
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