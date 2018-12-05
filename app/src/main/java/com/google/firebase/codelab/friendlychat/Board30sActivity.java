package com.google.firebase.codelab.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class Board30sActivity extends AppCompatActivity {


    String clientId = "qK6ocISgzi6FedNc8imk";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "kEPlQcl4eW";//애플리케이션 클라이언트 시크릿값";


    TextView textView;



    String keyword;
    boolean inItem = false, inTitle = false, inDescription = false, inDate = false, inLink = false;
    String title=null, description=null, date=null, link=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board30s);

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
                    Document document = Jsoup.connect("https://datalab.naver.com/keyword/realtimeList.naver").get();
                    Elements items = document.select("span.title");

                    ArrayList<String> keyword30 = new ArrayList<String>();
                    int i;
                    for(i = 60; i<80; i++ ){
                        keyword30.add(items.get(i).text());
                    }

                    int ran = (int)(Math.random()*20);

                    keyword = keyword30.get(ran);



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
