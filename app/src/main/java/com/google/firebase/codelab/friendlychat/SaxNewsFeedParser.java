package com.google.firebase.codelab.friendlychat;

import java.util.List;



import javax.xml.parsers.SAXParser;

import javax.xml.parsers.SAXParserFactory;



public class SaxNewsFeedParser extends BaseNewsFeedParser {



    protected SaxNewsFeedParser(String feedUrl){

        super(feedUrl);

    }



    public List<News> parse() {



        SAXParserFactory factory = SAXParserFactory.newInstance();



        RssHandler handler = new RssHandler();



        try {



            SAXParser parser = factory.newSAXParser();



            parser.parse(this.getInputStream(), handler);



        } catch (Exception e) {

            e.printStackTrace();

        }



        return handler.getNewsList();

    }





}
