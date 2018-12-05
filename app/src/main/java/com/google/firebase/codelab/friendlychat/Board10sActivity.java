package com.google.firebase.codelab.friendlychat;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board10sActivity extends AppCompatActivity {

    String clientId = "qK6ocISgzi6FedNc8imk";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "kEPlQcl4eW";//애플리케이션 클라이언트 시크릿값";


    TextView textView;



    String keyword;
    boolean inItem = false, inTitle = false, inDescription = false, inDate = false, inLink = false;
    String title=null, description=null, date=null, link=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board10s);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        // Capture the layout's TextView and set the string as its text
        textView = findViewById(R.id.textView);

        getNews();
    }






    public void getNews(){
        new Thread(){
            public void run(){
                try{
                    //주소에 있는 링크에 있는 태그 속 내용 가져옴. 그중에서 검색어인 텍스트만 가져옴
                    Document document = Jsoup.connect("https://datalab.naver.com/keyword/realtimeList.naver").get();
                    Elements items = document.select("span.title");  //items 에는 전체연령대, 10대,20대,30대,40대,50대이상의 실시간 급상승검색어
                                                                                 //1위에서 20위까지가 순차적으로 들어있음(태그포함)
                    ArrayList<String> keyword10 = new ArrayList<String>();      //10대의 실시간 급상승검색어 1위~20위를 저장할 ArrayList

                    int i;
                    for(i = 20; i<40; i++ ){
                        keyword10.add(items.get(i).text());               //index 0~19는 전체연령대의 실시간급상승 검색어 1위~20위
                                                                          //index 20~39는 10대의 실시간급상승 검색어 1위~20위 ArrayList에 순차적으로 추가.
                    }

                    int ran = (int)(Math.random()*20);                  //keyword10에 들어있는 20개의 검색어 키워드 중 1개를 임의로 선택해 반환.
                    keyword = keyword10.get(ran);


                    String searchWord = URLEncoder.encode(keyword, "UTF-8");
                    String apiURL = "https://openapi.naver.com/v1/search/news.xml?query="+searchWord+"&display=3&start=1&sort=sim";
                    URL url = new URL(apiURL);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setRequestProperty("X-Naver-Client-Id", clientId);
                    con.setRequestProperty("X-Naver-Client-Secret", clientSecret);


                    int responseCode = con.getResponseCode();

                    if(responseCode==200) {

                        XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                        XmlPullParser parser = parserCreator.newPullParser();

                        parser.setInput(con.getInputStream(), null);

                        int parserEvent = parser.getEventType();


                            while (parserEvent != XmlPullParser.END_DOCUMENT) {

                                switch (parserEvent) {
                                    case XmlPullParser.START_TAG:
                                        if (parser.getName().equals("item")) {
                                            inItem = true;
                                        }
                                        if (parser.getName().equals("title")) {
                                            inTitle = true;
                                        }
                                        if (parser.getName().equals("description")) {
                                            inDescription = true;
                                        }
                                        if (parser.getName().equals("pubDate")) {
                                            inDate = true;
                                        }
                                        if (parser.getName().equals("link")) {
                                            inLink = true;
                                        }
                                        break;

                                    case XmlPullParser.TEXT:
                                        if (inTitle) {
                                            title = parser.getText();
                                            inTitle = false;
                                        }
                                        if (inDescription) {
                                            description = parser.getText();
                                            inDescription = false;
                                        }
                                        if (inDate) {
                                            date = parser.getText();
                                            inDate = false;
                                        }
                                        if (inLink) {
                                            link = parser.getText();
                                            inLink = false;
                                        }
                                        break;

                                    case XmlPullParser.END_TAG:
                                        if (parser.getName().equals("item")) {
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    textView.append("제목: " + title + "\n내용: " + description + "\n날짜: " + date + "\n링크: " + link + "\n\n");

                                                    inItem = false;
                                                }
                                            });

                                        }
                                        break;
                                }


                                parserEvent = parser.next();
                            }



                    /*
                    int responseCode = con.getResponseCode();
                    BufferedReader br;

                    if (responseCode == 200) {
                        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String inputLine;
                        final StringBuffer response = new StringBuffer();
                        while ((inputLine = br.readLine()) != null)

                        {
                            response.append(inputLine);

                        }

                        runOnUiThread(new Runnable() {
                            public void run() {

                                textView.setText(response.toString());
                            }
                        });

                        br.close();

                    } else{
                        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }*/
                    }
                }catch(Exception e){
                    System.out.println(e + "   @@@@@@@@@@@@@@@@@#################################");
                }
            }
       }.start();
    }
}
