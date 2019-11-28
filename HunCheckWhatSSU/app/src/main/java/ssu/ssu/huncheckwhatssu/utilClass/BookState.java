package ssu.ssu.huncheckwhatssu.utilClass;

import android.os.Parcel;
import android.os.Parcelable;

public class BookState implements Parcelable {
    public enum bookState {
        BEST,GOOD,BAD,WORST;
    }

    bookState bookState01;
    bookState bookState02;
    bookState bookState03;
    bookState bookState04;
    bookState bookState05;
    bookState bookState06;

    public BookState(){
        this.bookState01 = bookState.BEST;
        this.bookState02 = bookState.BEST;
        this.bookState03 = bookState.BEST;
        this.bookState04 = bookState.BEST;
        this.bookState05 = bookState.BEST;
        this.bookState06 = bookState.BEST;
    }

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