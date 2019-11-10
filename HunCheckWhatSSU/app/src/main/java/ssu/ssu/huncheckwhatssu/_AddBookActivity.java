package ssu.ssu.huncheckwhatssu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    // 스크롤이 끝에 닿아서 책 로드하는 동안 이벤트 더이상 동작불가능하게 하기 위한 Trigger
    public static boolean process = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ex);

        recyclerView = findViewById(R.id.add_book_recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisiblePosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                Log.d("JS", "onScrolled: " + lastVisiblePosition);

                if (lastVisiblePosition == recyclerView.getAdapter().getItemCount()-1 && process == false) {
                    final String keyword = searchView.getQuery() + "";
                    process = true;
                    searchBook(keyword);
                }
            }
        });

        naverBookSearch = new NaverBookSearch();

        searchView = findViewById(R.id.book_add_search_text);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final String keyword = query;
                try {
                    recyclerView.removeAllViews();
                    naverBookSearch.resetSearchList();
                } catch (Exception e){}

                searchBook(keyword);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.d("book_js", "onQueryTextSubmit: " + newText);
                return false;
            }
        });
    }



    public void searchBook(final String keyword) {
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
                        adapter = new AddBookAdapter(searchedBookList, getApplicationContext());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                                recyclerView.setAdapter(adapter);
                                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(lastPosition);
                            }
                        });

                    }
                } else {
                    Log.d("book_js", "onQueryTextSubmit: API ERROR");
                }
            }
        }.start();
    }
}
