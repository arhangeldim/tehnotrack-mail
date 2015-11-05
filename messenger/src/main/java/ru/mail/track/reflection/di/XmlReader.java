package ru.mail.track.reflection.di;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 *
 */
public class XmlReader {

    private static Logger log = LoggerFactory.getLogger(XmlReader.class);

    private String xmlPath;

    public XmlReader(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public Document read() throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new File(xmlPath));
    }

    public void parseDocument(Document doc) throws Exception {

        // корневой элемент только один
        Element root = doc.getDocumentElement();

        // children vs childs
        NodeList children = root.getChildNodes();
        printNodeList(children);

    }

    private void printAttributes(Node node) {
        NamedNodeMap map = node.getAttributes();
        for (int i = 0; i < map.getLength(); i++) {
            Node n = map.item(i);
            log.info("ATTR [{}={}]", n.getNodeName(), n.getNodeValue());
        }
    }

    public void printNodeList(NodeList list) throws Exception {
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if ("#text".equals(node.getNodeName())) {
                continue;
            }
            log.info("NODE [{}]", node.getNodeName());
            printAttributes(node);
            printNodeList(node.getChildNodes());
        }
    }

    public static void main(String[] args) throws Exception {
        XmlReader reader = new XmlReader("sample.xml");
        Document doc = reader.read();
        reader.parseDocument(doc);
    }
}
