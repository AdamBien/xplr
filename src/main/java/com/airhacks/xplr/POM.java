package com.airhacks.xplr;

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author airhacks.com
 */
public final class POM {

    private String content;

    private String groupId;
    private String artifactId;
    private String packaging;
    private String version;

    public POM(String content) {
        this.content = content;
        parse();
    }

    void parse() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new IllegalStateException(ex);
        }
        try (StringReader reader = new StringReader(content)) {
            try {
                Document document = builder.parse(new InputSource(reader));
                Node project = document.getElementsByTagName("project").item(0);
                NodeList childNodes = project.getChildNodes();
                for (int i = 0; i < childNodes.getLength(); i++) {
                    Node item = childNodes.item(i);
                    if ("parent".equalsIgnoreCase(item.getNodeName())) {
                        this.groupId = findSubNode("groupId", item);
                        this.artifactId = findSubNode("artifactId", item);
                        this.packaging = findSubNode("packaging", item);
                        this.version = findSubNode("version", item);
                    }
                    if ("groupId".equals(item.getNodeName())) {
                        this.groupId = item.getTextContent();
                    }
                    if ("artifactId".equals(item.getNodeName())) {
                        this.artifactId = item.getTextContent();
                    }
                    if ("packaging".equals(item.getNodeName())) {
                        this.packaging = item.getTextContent();
                    }
                    if ("version".equals(item.getNodeName())) {
                        this.version = item.getTextContent();
                    }
                }
            } catch (SAXException | IOException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    String findSubNode(String nodeName, Node parent) {
        NodeList childNodes = parent.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (nodeName.equals(item.getNodeName())) {
                return item.getTextContent();
            }
        }
        return null;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public String getPackaging() {
        return packaging;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        String retVal = "<dependency>" + "\n";
        retVal += " <groupId>" + this.groupId + "</groupId>" + "\n";
        retVal += " <artifactId>" + this.artifactId + "</artifactId>" + "\n";
        retVal += " <version>" + this.version + "</version>" + "\n";
        if (packaging != null) {
            retVal += " <packaging>" + this.packaging + "</packaging>" + "\n";
        }
        retVal += "</dependency>";

        return retVal;
    }

}
