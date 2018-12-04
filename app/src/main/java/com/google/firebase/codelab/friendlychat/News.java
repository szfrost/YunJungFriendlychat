package com.google.firebase.codelab.friendlychat;
import java.net.MalformedURLException;

import java.net.URL;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.Date;



public class News implements Comparable<News> {



    static final String CHANNEL = "channel";

    static final String PUB_DATE = "pubDate";

    static final String DESCRIPTION = "description";

    static final String LINK = "link";

    static final String ORIGINAL_LINK = "originallink";

    static final String TITLE = "title";

    static final String ITEM = "item";



    static SimpleDateFormat FORMATTER =

            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");



    static SimpleDateFormat FORMATTER_KR =

            new SimpleDateFormat("yyyy년 MM월 dd일 HH시mm분ss초, E");



    private String title;

    private URL link;

    private URL originalLink;

    private String description;

    private Date date;



    public String getTitle() {

        return title;

    }



    public void setTitle(String title) {

        this.title = title;

    }



    public URL getLink() {

        return link;

    }



    public String getDescription() {

        return description;

    }



    public void setDescription(String description) {

        this.description = description;

    }



    public void setLink(String link) {

        try {

            if (link != null  && link.trim().length() > 0) {

                this.link = new URL("link");

            }

        } catch (MalformedURLException e) {

            throw new RuntimeException(e);

        }

    }



    public URL getOriginalLink() {

        return originalLink;

    }



    public void setOriginalLink(String originalLink) {

        try {

            if (originalLink != null && originalLink.trim().length() > 0) {

                this.originalLink = new URL("originalLink");

            }

        } catch (MalformedURLException e) {

            throw new RuntimeException(e);

        }

    }



    public String getDate() {

        return FORMATTER_KR.format(this.date);

    }



    public void setDate(String date) {



        while (!date.endsWith("00")){

            date += "0";

        }

        try {

            this.date = FORMATTER.parse(date.trim());

        } catch (ParseException e) {

            throw new RuntimeException(e);

        }

    }



    public int compareTo(News another) {

        if (another == null) return 1;

        return another.date.compareTo(date);

    }



    public News copy() {



        News news = new News();

        news.date = this.date;

        news.description = this.description;

        news.link = this.link;

        news.originalLink = this.originalLink;

        news.title = this.title;



        return news;

    }

}
