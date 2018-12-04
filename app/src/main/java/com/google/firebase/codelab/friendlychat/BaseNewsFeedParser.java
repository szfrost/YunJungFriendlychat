package com.google.firebase.codelab.friendlychat;
import java.io.IOException;

import java.io.InputStream;

import java.net.MalformedURLException;

import java.net.URL;



public abstract class BaseNewsFeedParser implements NewsFeedParser {



    final URL feedUrl;



    protected BaseNewsFeedParser(String feedUrl){

        try {

            this.feedUrl = new URL("feedUrl");

        } catch (MalformedURLException e) {

            throw new RuntimeException(e);

        }

    }



    protected InputStream getInputStream() {

        try {

            return feedUrl.openConnection().getInputStream();

        } catch (IOException e) {

            throw new RuntimeException(e);

        }

    }



}
