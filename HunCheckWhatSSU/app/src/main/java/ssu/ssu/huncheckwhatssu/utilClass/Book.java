package ssu.ssu.huncheckwhatssu.utilClass;

import android.media.Image;

import java.util.Calendar;

public class Book {
    String isbn;
    String title;
    String image;
    String author;
    int price;
    String publisher;
    String pubdate;
    String description;
    BookState bookState;

    public Book(String isbn, String title, String image, String author, int price, String publisher, String pubdate, String description) {
        this.isbn = isbn;
        this.title = title;
        this.image = image;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
        this.pubdate = pubdate;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", publisher='" + publisher + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", description='" + description + '\'' +
                ", bookState=" + bookState +
                '}';
    }

    public void setBookState(BookState bookState) {
        this.bookState = bookState;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public long getPrice() {
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