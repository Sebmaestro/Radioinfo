package model;


import org.xml.sax.SAXException;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private URL radioUrl;
    private List<Channel> channelList;
    private List<Program> programList;

    public Parser() {
        channelList = new ArrayList<>();

    }

    public List<Channel> parseChannels(String url) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new URL(url).openStream());
            NodeList channelList = doc.getElementsByTagName("channel");

            for (int i = 0; i < channelList.getLength(); i++) {
                //Create object with name and id
                this.channelList.add(new Channel(channelList.item(i).getAttributes().getNamedItem("name").getNodeValue(),
                        channelList.item(i).getAttributes().getNamedItem("id").getNodeValue()));

                NodeList childs = channelList.item(i).getChildNodes();

                //Add description and picture to object
                for (int j = 0; j < childs.getLength(); j++) {
                    if (childs.item(j).getNodeName().equals("tagline")) {
                        this.channelList.get(i).setDescription(childs.item(j).getTextContent());
                    }
                    if (childs.item(j).getNodeName().equals("image")) {
                        this.channelList.get(i).setPicture(childs.item(j).getTextContent());
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return channelList;
    }

    public List<Program> parseTableau(String url) {
        programList = new ArrayList<>();
        //System.out.println(url);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new URL(url).openStream());
            NodeList scheduledEpisodesList = doc.getElementsByTagName("scheduledepisode");

            for (int i = 0; i < scheduledEpisodesList.getLength(); i++) {
                NodeList childs = scheduledEpisodesList.item(i).getChildNodes();

                for (int j = 0; j < childs.getLength(); j++) {
                    if (childs.item(j).getNodeName().equals("title")) {
                        programList.add(new Program(childs.item(j).getTextContent()));
                        //System.out.println(childs.item(j).getTextContent());
                        //Sätt in i ny klass
                    }
                    if (childs.item(j).getNodeName().equals("description")) {
                        programList.get(i).setDescription(childs.item(j).getTextContent());
                        //System.out.println(childs.item(j).getTextContent());
                    }
                    if (childs.item(j).getNodeName().equals("starttimeutc")) {
                        programList.get(i).setStartTime(childs.item(j).getTextContent());
                        //System.out.println(childs.item(j).getTextContent());
                    }
                    if (childs.item(j).getNodeName().equals("endtimeutc")) {
                        programList.get(i).setEndTime(childs.item(j).getTextContent());
                        //System.out.println(childs.item(j).getTextContent());
                    }
                    if (childs.item(j).getNodeName().equals("imageurl")) {
                        programList.get(i).setImg(childs.item(j).getTextContent());
                        //System.out.println(childs.item(j).getTextContent());
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return programList;
    }
}
