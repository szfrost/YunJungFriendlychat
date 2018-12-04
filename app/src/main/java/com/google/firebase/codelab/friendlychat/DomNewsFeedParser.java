package com.google.firebase.codelab.friendlychat;

import java.util.ArrayList;

import java.util.List;



import javax.xml.parsers.DocumentBuilder;

import javax.xml.parsers.DocumentBuilderFactory;



import org.w3c.dom.Document;

import org.w3c.dom.Element;

import org.w3c.dom.Node;

import org.w3c.dom.NodeList;



public class DomNewsFeedParser extends BaseNewsFeedParser {



    public DomNewsFeedParser(String feedUrl) {

        super(feedUrl);

    }



    public List<News> parse() {



        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();



        List<News> newsList = new ArrayList<News>();



        try {



            DocumentBuilder builder = factory.newDocumentBuilder();

            Document dom = builder.parse(this.getInputStream());



            Element root = dom.getDocumentElement();

            NodeList items = root.getElementsByTagName(News.ITEM);



            for (int i=0;i<items.getLength();i++){



                News news = new News();



                Node item = items.item(i);



                NodeList properties = item.getChildNodes();



                for (int j=0;j<properties.getLength();j++){



                    Node property = properties.item(j);



                    String name = property.getNodeName();



                    if (name.equalsIgnoreCase(News.TITLE)){

                        // 특수 문자로 인해서 텍스트가 여러개로 나뉘는 경우가 발생하여, 하나로 합친다.

                        StringBuilder text = new StringBuilder();

                        NodeList chars = property.getChildNodes();

                        for (int k=0; k <chars.getLength(); k++){

                            text.append(chars.item(k).getNodeValue());

                        }

                        news.setTitle(text.toString());

                    } else if (name.equalsIgnoreCase(News.LINK)){

                        news.setLink(property.getFirstChild().getNodeValue());

                    } else if (name.equalsIgnoreCase(News.ORIGINAL_LINK)){

                        Node firstChild = property.getFirstChild();

                        String originalLink = "";

                        if (firstChild != null) {

                            originalLink = firstChild.getNodeValue();

                        }

                        news.setOriginalLink(originalLink);

                    } else if (name.equalsIgnoreCase(News.DESCRIPTION)){

                        news.setDescription(property.getFirstChild().getNodeValue());

                    } else if (name.equalsIgnoreCase(News.PUB_DATE)){

                        news.setDate(property.getFirstChild().getNodeValue());

                    }

                }



                newsList.add(news);

            }



        } catch (Exception e) {

            e.printStackTrace();

        }



        return newsList;

    }



}
