package ssu.ssu.huncheckwhatssu.utilClass;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class NaverBookSearch {
    StringBuffer response;
    String lastKeyword;
    ArrayList<Book> searchedBookList;
    final static String clientId = "PuUP6MmwANImiw4Je9zT";//애플리케이션 클라이언트 아이디값";
    final static String clientSecret = "ipkzdv9oUA";//애플리케이션 클라이언트 시크릿값 공용 키 만들어서 사용해야함;
    int responseCode = 0;
    String responseJson;

    public NaverBookSearch() {
        searchedBookList = new ArrayList<>();
    }

    public ArrayList<ssu.ssu.huncheckwhatssu.utilClass.Book> searchBook(String keyword) {
        lastKeyword = keyword;
        searchedBookList.clear();

        responseJson = getNaverSearch(lastKeyword);

        Log.d("book_js", "run: " + responseJson);
        if (responseJson == null) {
            Log.d("book_js", "run: API Error");
        } else {
            // GET한 책 정보 파싱
            try {
                JSONObject jsonObject = new JSONObject(responseJson);
                JSONArray jsonArray;
                if (jsonObject.getInt("total") != 0) {
                    jsonArray = jsonObject.getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item = jsonArray.getJSONObject(i);
                        searchedBookList.add(new Book(item.getString("isbn").split(" ")[0], item.getString("isbn").split(" ")[1], item.getString("title"),
                                item.getString("image"), item.getString("author"), item.getInt("price"),
                                item.getString("publisher"), item.getString("pubdate"), item.getString("description")));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("book_js", "run: API Error");
            }
        }

        return  this.searchedBookList;
    }

    private String getNaverSearch(String keyword) {
        try {
            String encodingKeyword = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + encodingKeyword;
            Log.d("book_js", "getNaverSearch: " + apiURL);
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

            responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
