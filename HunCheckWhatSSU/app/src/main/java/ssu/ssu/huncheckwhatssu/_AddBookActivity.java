package ssu.ssu.huncheckwhatssu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import ssu.ssu.huncheckwhatssu.utilClass.Book;
import ssu.ssu.huncheckwhatssu.utilClass.NaverBookSearch;

public class _AddBookActivity extends AppCompatActivity {

    SearchView searchView;
    NaverBookSearch naverBookSearch;
    ArrayList<Book> searchedBookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ex);

        naverBookSearch = new NaverBookSearch();

        searchView = findViewById(R.id.book_add_search_text);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final String keyword = query;

                new Thread() {
                    @Override
                    public void run() {
                        searchedBookList = naverBookSearch.searchBook(keyword);
                        Log.d("book_js", "onQueryTextSubmit: " + keyword);

                        if (searchedBookList != null) {
                            if (searchedBookList.size() == 0) {
                                Log.d("book_js", "onQueryTextSubmit: nothing");
                            } else {
                                for (int i = 0; i < searchedBookList.size(); i++) {
                                    Log.d("book_js_search", searchedBookList.get(i).toString());
                                }
                            }
                        } else {
                            Log.d("book_js", "onQueryTextSubmit: API ERROR");
                        }
                    }
                }.start();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.d("book_js", "onQueryTextSubmit: " + newText);
                return false;
            }
        });
    }




}
