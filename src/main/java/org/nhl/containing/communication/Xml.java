package org.nhl.containing.communication;

import org.w3c.dom.CharacterData;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import org.nhl.containing.communication.Message.Command;

/**
 * Parses provided XML files and returns Containers.
 */
public class Xml {

    /**
     * Tries to decode the incoming XML message and puts the data in an
     * arraylist which contains a message class with all the data
     *
     * @param xmlMessage The xml message you're willing to decode
     */
    public static ArrayList<Message> decodeXMLMessage(String xmlMessage) {
        ArrayList messageList = new ArrayList();
        if (xmlMessage != null) {
            try {

                DocumentBuilderFactory dbf =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xmlMessage));
                Document doc = db.parse(is);
                NodeList parentNode = doc.getElementsByTagName("Controller");
                for (int j = 0; j < parentNode.getLength(); j++) {

                    Element element = (Element) parentNode.item(j);
                    NodeList id = element.getElementsByTagName("id");
                    Element idLine = (Element) id.item(0);

                    NodeList attributes = doc.getElementsByTagName("LastMessage");
                    if (attributes.getLength() > 0) {
                        for (int i = 0; i < attributes.getLength(); i++) {
                            Message message = new Message();
                            element = (Element) attributes.item(i);
                            NodeList numberOfContainers = element.getElementsByTagName("numberOfContainers");
                            Element line = (Element) numberOfContainers.item(0);
                            message.setMaxValueContainers(Integer.parseInt(getCharacterDataFromElement(line)));
                            message.setCommand(Command.LastMessage);
                            message.setId(Integer.parseInt(getCharacterDataFromElement(idLine)));
                            messageList.add(message);
                        }
                    }
                    attributes = doc.getElementsByTagName("Create");
                    if (attributes.getLength() > 0) {
                        for (int i = 0; i < attributes.getLength(); i++) {

                            NodeList createAttributes = doc.getElementsByTagName("Transporter");
                            if (createAttributes.getLength() > 0) {
                                for (int k = 0; k < createAttributes.getLength(); k++) {
                                    Message message = new Message();

                                    element = (Element) createAttributes.item(k);
                                    NodeList nodeList = element.getElementsByTagName("type");
                                    Element line = (Element) nodeList.item(0);
                                    message.setTransportType(getCharacterDataFromElement(line));

                                    element = (Element) createAttributes.item(k);
                                    nodeList = element.getElementsByTagName("identifier");
                                    line = (Element) nodeList.item(0);
                                    message.setId(Integer.parseInt(getCharacterDataFromElement(line)));

                                    message.setCommand(Command.TransportType);
                                    messageList.add(message);

                                    attributes = doc.getElementsByTagName("Container");
                                    if (attributes.getLength() > 0) {
                                        for (int l = 0; l < attributes.getLength(); l++) {


                                            message = new Message();
                                            element = (Element) attributes.item(l);
                                            nodeList = element.getElementsByTagName("iso");
                                            line = (Element) nodeList.item(0);
                                            message.setContainerIso(getCharacterDataFromElement(line));

                                            nodeList = element.getElementsByTagName("owner");
                                            line = (Element) nodeList.item(0);
                                            message.setContainerOwner(getCharacterDataFromElement(line));

                                            nodeList = element.getElementsByTagName("xLoc");
                                            line = (Element) nodeList.item(0);
                                            message.setxLoc(Integer.parseInt(getCharacterDataFromElement(line)));

                                            nodeList = element.getElementsByTagName("yLoc");
                                            line = (Element) nodeList.item(0);
                                            message.setyLoc(Integer.parseInt(getCharacterDataFromElement(line)));

                                            nodeList = element.getElementsByTagName("zLoc");
                                            line = (Element) nodeList.item(0);
                                            message.setzLoc(Integer.parseInt(getCharacterDataFromElement(line)));

                                            message.setCommand(Command.Create);
                                            message.setId(Integer.parseInt(getCharacterDataFromElement(idLine)));
                                            messageList.add(message);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    attributes = doc.getElementsByTagName("Move");
                    if (attributes.getLength() > 0) {
                        for (int i = 0; i < attributes.getLength(); i++) {
                            Message message = new Message();
                            element = (Element) attributes.item(i);
                            NodeList nodeList = element.getElementsByTagName("objectName");
                            Element line = (Element) nodeList.item(0);
                            message.setObjectName(getCharacterDataFromElement(line));

                            nodeList = element.getElementsByTagName("destinationName");
                            line = (Element) nodeList.item(0);
                            message.setDestinationName(getCharacterDataFromElement(line));

                            nodeList = element.getElementsByTagName("speed");
                            line = (Element) nodeList.item(0);
                            message.setSpeed(getCharacterDataFromElement(line));

                            message.setCommand(Command.Move);
                            message.setId(Integer.parseInt(getCharacterDataFromElement(idLine)));
                            messageList.add(message);
                        }
                    }
                    attributes = doc.getElementsByTagName("Dispose");
                    if (attributes.getLength() > 0) {
                        for (int i = 0; i < attributes.getLength(); i++) {
                            Message message = new Message();
                            element = (Element) attributes.item(i);
                            NodeList nodeList = element.getElementsByTagName("objectName");
                            Element line = (Element) nodeList.item(0);
                            message.setObjectName(getCharacterDataFromElement(line));
                            message.setCommand(Command.Dispose);
                            message.setId(Integer.parseInt(getCharacterDataFromElement(idLine)));
                            messageList.add(message);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return messageList;
    }

    /**
     * Gets the characterdata from the specified element
     */
    private static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "?";
    }
}