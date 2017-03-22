package com.example.rekhansh.podcastapp;

/**
 * Created by Rekhansh on 3/8/2017.
 */

import android.util.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class PodcastUtil {
    static public class PodcastSAXParser extends DefaultHandler {
        static ArrayList<Podcasts> podcastsList;
        Podcasts podcast;
        StringBuilder xmlInnerText;

        public static ArrayList<Podcasts> getPodcastsList() {
            return podcastsList;
        }

        static public ArrayList<Podcasts> parsePodcast(InputStream in) throws IOException, SAXException {
            PodcastSAXParser parser = new PodcastSAXParser();
            Xml.parse(in, Xml.Encoding.UTF_8, parser);
            return PodcastSAXParser.getPodcastsList();
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            xmlInnerText = new StringBuilder();
            podcastsList = new ArrayList<Podcasts>();
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

            if (localName.equals("item")) {
                podcast = new Podcasts();
                podcast.setEpisodeTitle("Test");
            } else if (localName.equals("image") && podcast != null) {
                podcast.setImgURL(attributes.getValue("href"));
            } else if (localName.equals("enclosure")) {
                podcast.setAudioURL(attributes.getValue("url"));
            } else if (localName.equals("description") && podcast != null) {
                podcast.setDesc(xmlInnerText.toString().trim());
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (localName.equals("item")) {
                podcastsList.add(podcast);
            } else if (localName.equals("title") && podcast != null) {
                podcast.setEpisodeTitle(xmlInnerText.toString().trim());
            } else if (localName.equals("pubDate")) {
                podcast.setPostedOn(xmlInnerText.toString().trim());
            } else if (localName.equals("duration") && podcast != null) {
                if (!xmlInnerText.toString().trim().isEmpty()) {
                    podcast.setDuration(Integer.parseInt(xmlInnerText.toString().trim()));
                }
            }
            xmlInnerText.setLength(0);
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            xmlInnerText.append(ch, start, length);
        }
    }


}
