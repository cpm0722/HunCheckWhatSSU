package ssu.ssu.huncheckwhatssu.utilClass;

import android.media.Image;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Book {
    String isbn10;
    String isbn13;
    String title;
    String image;
    String author;
    int price;
    String publisher;
    String pubDate;
    String description;
    BookState bookState;

    public Book(){}

    public Book(String isbn10, String isbn13, String title, String image, String author, int price, String publisher, String pubDate, String description) {
        this.isbn10 = isbn10;
        this.isbn13 = isbn13;
        this.title = title;
        this.image = image;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
        this.pubDate = pubDate;
        this.description = description;
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
                ", pubDate='" + pubDate + '\'' +
                ", description='" + description + '\'' +
                ", bookState=" + bookState +
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

    public void toMap(Map<String, Object> result){
        result.put("isbn10", this.isbn10);
        result.put("isbn13", this.isbn13);
        result.put("title", this.title);
        result.put("author", this.author);
        result.put("price", this.price);
        result.put("publisher", this.publisher);
        result.put("pubDate", this.pubDate);
        result.put("description", this.description);
        result.put("bookState", this.bookState);
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