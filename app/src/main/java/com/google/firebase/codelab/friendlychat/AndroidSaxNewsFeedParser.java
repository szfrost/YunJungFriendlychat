package com.google.firebase.codelab.friendlychat;
import java.util.ArrayList;

import java.util.List;



import android.sax.Element;

import android.sax.EndElementListener;

import android.sax.EndTextElementListener;

import android.sax.RootElement;

import android.util.Xml;



public class AndroidSaxNewsFeedParser extends BaseNewsFeedParser {



    static final String RSS = "rss";



    protected AndroidSaxNewsFeedParser(String feedUrl) {

        super(feedUrl);

    }



    public List<News> parse() {



        final News currentNews = new News();



        RootElement root = new RootElement(RSS);



        final List<News> newsList = new ArrayList<News>();



        Element channel = root.getChild(News.CHANNEL);



        Element item = channel.getChild(News.ITEM);

        item.setEndElementListener(new EndElementListener(){

            public void end() {

                newsList.add(currentNews.copy());

            }

        });

        item.getChild(News.TITLE).setEndTextElementListener(new EndTextElementListener(){

            public void end(String body) {

                currentNews.setTitle(body);

            }

        });

        item.getChild(News.LINK).setEndTextElementListener(new EndTextElementListener(){

            public void end(String body) {

                currentNews.setLink(body);

            }

        });

        item.getChild(News.DESCRIPTION).setEndTextElementListener(new EndTextElementListener(){

            public void end(String body) {

                currentNews.setDescription(body);

            }

        });

        item.getChild(News.PUB_DATE).setEndTextElementListener(new EndTextElementListener(){

            public void end(String body) {

                currentNews.setDate(body);

            }

        });



        try {

            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());

        } catch (Exception e) {

            e.printStackTrace();

        }



        return newsList;

    }



}
