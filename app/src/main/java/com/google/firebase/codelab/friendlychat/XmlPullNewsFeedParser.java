package com.google.firebase.codelab.friendlychat;

import java.util.ArrayList;

import java.util.List;



import org.xmlpull.v1.XmlPullParser;



import android.util.Xml;



public class XmlPullNewsFeedParser extends BaseNewsFeedParser {



    protected XmlPullNewsFeedParser(String feedUrl) {

        super(feedUrl);

    }



    public List<News> parse() {



        List<News> NewsList = null;



        XmlPullParser parser = Xml.newPullParser();



        try {



            // auto-detect the encoding from the stream

            parser.setInput(this.getInputStream(), null);



            int eventType = parser.getEventType();



            News currentNews = null;



            boolean done = false;



            while (eventType != XmlPullParser.END_DOCUMENT && !done){



                String name = null;



                switch (eventType){



                    case XmlPullParser.START_DOCUMENT:



                        NewsList = new ArrayList<News>();

                        break;



                    case XmlPullParser.START_TAG:



                        name = parser.getName();



                        if (name.equalsIgnoreCase(News.ITEM)){

                            currentNews = new News();

                        } else if (currentNews != null){



                            if (name.equalsIgnoreCase(News.LINK)){

                                currentNews.setLink(parser.nextText());

                            } else if (name.equalsIgnoreCase(News.ORIGINAL_LINK)){

                                currentNews.setOriginalLink(parser.nextText());

                            } else if (name.equalsIgnoreCase(News.DESCRIPTION)){

                                currentNews.setDescription(parser.nextText());

                            } else if (name.equalsIgnoreCase(News.PUB_DATE)){

                                currentNews.setDate(parser.nextText());

                            } else if (name.equalsIgnoreCase(News.TITLE)){

                                currentNews.setTitle(parser.nextText());

                            }

                        }

                        break;



                    case XmlPullParser.END_TAG:



                        name = parser.getName();



                        if (name.equalsIgnoreCase(News.ITEM) && currentNews != null){

                            NewsList.add(currentNews);

                        } else if (name.equalsIgnoreCase(News.CHANNEL)){

                            done = true;

                        }

                        break;

                }



                eventType = parser.next();

            }



        } catch (Exception e) {

            e.printStackTrace();

        }



        return NewsList;

    }



}

