package com.google.firebase.codelab.friendlychat;
import java.util.ArrayList;

import java.util.List;



import org.xml.sax.Attributes;

import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;



public class RssHandler extends DefaultHandler {



    private List<News> newsList;

    private News currentNews;

    private StringBuilder builder;



    public List<News> getNewsList(){

        return this.newsList;

    }



    @Override

    public void characters(char[] ch, int start, int length)

            throws SAXException {

        super.characters(ch, start, length);

        builder.append(ch, start, length);

    }



    @Override

    public void endElement(String uri, String localName, String name)

            throws SAXException {

        super.endElement(uri, localName, name);



        if (this.currentNews != null){

            if (localName.equalsIgnoreCase(News.TITLE)){

                currentNews.setTitle(builder.toString());

            } else if (localName.equalsIgnoreCase(News.LINK)){

                currentNews.setLink(builder.toString());

            } else if (localName.equalsIgnoreCase(News.ORIGINAL_LINK)){

                currentNews.setOriginalLink(builder.toString());

            } else if (localName.equalsIgnoreCase(News.DESCRIPTION)){

                currentNews.setDescription(builder.toString());

            } else if (localName.equalsIgnoreCase(News.PUB_DATE)){

                currentNews.setDate(builder.toString());

            } else if (localName.equalsIgnoreCase(News.ITEM)){

                newsList.add(currentNews);

            }

        }

        builder.setLength(0);

    }



    @Override

    public void startDocument() throws SAXException {

        super.startDocument();

        newsList = new ArrayList<News>();

        builder = new StringBuilder();

    }



    @Override

    public void startElement(String uri, String localName, String name,

                             Attributes attributes) throws SAXException {

        super.startElement(uri, localName, name, attributes);

        if (localName.equalsIgnoreCase(News.ITEM)){

            this.currentNews = new News();

        }

    }

}
