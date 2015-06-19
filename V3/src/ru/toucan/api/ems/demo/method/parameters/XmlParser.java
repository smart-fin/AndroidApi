package ru.toucan.api.ems.demo.method.parameters;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import ru.toucan.api.ems.demo.utils.Utils;

/**
 * Created by Nastya on 15.04.2015.
 */
public class XmlParser {

    static final String SETTINGS = "Settings";
    static final String PAGE = "Page";
    static final String GROUP = "Group";
    static final String PARAMETER = "Parameter";

    static final String NAME = "Name";
    static final String CAPTION = "Caption";
    static final String TYPE_VALUE = "TypeValue";
    static final String DEFAULT_VALUE = "DefaultValue";

    public static Page get(String data) throws Exception {
        Page page = new Page();

        XmlPullParserFactory parserFactory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = parserFactory.newPullParser();
        parser.setInput(new StringReader(data));

        String tag = parser.getName();
        int parserEvent = parser.nextTag();
        while (!(SETTINGS.equals(tag) && parserEvent != XmlPullParser.END_DOCUMENT)) {
            switch (parserEvent) {
                case XmlPullParser.START_TAG:
                    if (PAGE.equals(tag)) {
                        page = parsePage(parser);
                    }
                default:
                    parserEvent = parser.nextTag();
                    tag = parser.getName();
            }
        }
        return page;
    }

    private static Group parseGroup(XmlPullParser parser) throws Exception {
        Group group = new Group();
        group.parameters = new ArrayList<>();

        String tag = parser.getName();
        int parserEvent = parser.getEventType();
        while ( !(GROUP.equals(tag) && parser.getEventType() == XmlPullParser.END_TAG) ) {
            switch (parserEvent) {
                case XmlPullParser.START_TAG:
                    if (GROUP.equals(tag)) {
                        group.caption = parser.getAttributeValue(null, CAPTION);
                    }
                    if (PARAMETER.equals(tag)) {
                        group.parameters.add(parseParameter(parser));
                    }
                default:
                    parserEvent = parser.nextTag();
                    tag = parser.getName();
            }
        }
        return group;
    }

    private static Page parsePage(XmlPullParser parser) throws Exception{
        Page page = new Page();
        page.groups = new ArrayList<>();

        String tag = parser.getName();
        int parserEvent = parser.getEventType();
        while ( !(PAGE.equals(tag) && parser.getEventType() == XmlPullParser.END_TAG) ) {
            switch (parserEvent) {
                case XmlPullParser.START_TAG:
                    if (PAGE.equals(tag)) {
                        page.caption = parser.getAttributeValue(null, CAPTION);
                    }
                    if (GROUP.equals(tag)) {
                        page.groups.add(parseGroup(parser));
                    }
                default:
                    parserEvent = parser.nextTag();
                    tag = parser.getName();
            }
        }
        return page;
    }

    private static Parameter parseParameter(XmlPullParser parser) throws Exception{
        Parameter parameter = new Parameter();
        parameter.name = parser.getAttributeValue(null, NAME);
        parameter.caption = parser.getAttributeValue(null, CAPTION);
        parameter.typeValue = parser.getAttributeValue(null, TYPE_VALUE);
        parameter.defaultValue = parser.getAttributeValue(null, DEFAULT_VALUE);
        return parameter;
    }

    public static String update(String xmlData, LinearLayout layout) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new ByteArrayInputStream(xmlData.getBytes()));

            Node group = doc.getElementsByTagName(GROUP).item(0);
            NodeList list = group.getChildNodes();

            for (int i = 0; i < list.getLength(); i++) {

                Node node = list.item(i);
                String name = node.getNodeName();
                if (name.equals(PARAMETER)) {
                    Log.d(Utils.TAG, name);

                    NamedNodeMap attr = node.getAttributes();
                    Node nameItem = attr.getNamedItem(NAME);
                    Node defaultValueItem = attr.getNamedItem(DEFAULT_VALUE);

                    View view = layout.findViewWithTag(nameItem.getTextContent());
                    if (view instanceof EditText) {
                        EditText et = (EditText)view;
                        defaultValueItem.setTextContent(et.getText().toString());
                    }
                }
            }
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform( new DOMSource(doc), result);
            return writer.toString();
        } catch (Exception e) {
        }

        return null;
    }
}
