package net.nokok.twitduke.components.keyevent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.input.KeyCombination;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created by wtnbsts on 2014/07/24.
 */
public class XmlKeyMapStore implements KeyMapStore {

    private final static String DOCUMENT_VERSION = "1.0";
    private final static boolean DOCUMENT_STANDALONE = true;
    private final static boolean DOCUMENT_STRICT = true;

    private final static String TAG_ROOT = "keymap";
    private final static String TAG_ACTION = "action";
    private final static String TAG_KEYBOARD_SHORTCUT = "keyboard-shortcut";
    private final static String ATTR_SETTING_NAME = "name";
    private final static String ATTR_ACTION_ID = "id";
    private final static String ATTR_ACTION_PLUGIN = "plugin";
    private final static String ATTR_KBSC_COMPONENT = "selector";
    private final static String ATTR_KBSC_KEYSTROKE = "keystroke";

    private final static String TF_METHOD = "xml";
    private final static String TF_INDENT = "yes";
    private final static String TF_ISIZE = "4";

    private static void save(final OutputStream dist, final Document document) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, TF_METHOD);
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, TF_INDENT);
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", TF_ISIZE);
        transformer.transform(new DOMSource(document), new StreamResult(dist));
    }

    private static Document createSettingDocument(final KeyMapSetting setting) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.newDocument();
        Element root = doc.createElement(TAG_ROOT);
        root.setAttribute(ATTR_SETTING_NAME, setting.getSettingName().get());
        doc.appendChild(root);
        setting.getCommandIds().get()
                .stream()
                .sorted()
                .forEach(id -> root.appendChild(createActionElement(doc, setting, id)));
        return doc;
    }

    private static String parseSettingName(final Document document) {
        return getAttribute(document.getFirstChild(), ATTR_SETTING_NAME);
    }

    private static List<String> parseActionIds(final Document document) {
        Stream<Node> actions = stream(document.getFirstChild().getChildNodes());
        return actions.filter(XmlKeyMapStore::isElementNode)
                .map(node -> getAttribute(node, ATTR_ACTION_ID))
                .collect(Collectors.toList());
    }

    private static String parsePluginName(final Document document, final String id) {
        return getAttribute(getActionNodeById(document, id), ATTR_ACTION_PLUGIN);
    }

    private static List<KeyBind> createKeyBinds(final Document document, final String id) throws RuntimeException {
        Node actionNode = getActionNodeById(document, id);
        return stream(actionNode.getChildNodes())
                .filter(XmlKeyMapStore::isElementNode)
                .map(XmlKeyMapStore::createKeyBind)
                .collect(Collectors.toList());
    }

    private static KeyBind createKeyBind(final Node keyBindNode) {
        try {
            KeyCombination shortcutKey = KeyCombination.keyCombination(getAttribute(keyBindNode, ATTR_KBSC_KEYSTROKE));
            String targetSelector = getAttribute(keyBindNode, ATTR_KBSC_COMPONENT);
            return new KeyBind(shortcutKey, targetSelector);
        } catch (NumberFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static Document buildDocument(final InputStream source)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return builder.parse(source);
    }

    private static String getAttribute(final Node node, final String attrName) {
        return node.getAttributes().getNamedItem(attrName).getTextContent();
    }

    private static Node getActionNodeById(final Document document, final String id) {
        Stream<Node> actions = stream(document.getFirstChild().getChildNodes());
        return actions.filter(XmlKeyMapStore::isElementNode)
                .filter(node -> id.equals(getAttribute(node, ATTR_ACTION_ID)))
                .findFirst().get();
    }

    private static Stream<Node> stream(final NodeList src) {
        Stream.Builder<Node> builder = Stream.builder();
        for ( int i = 0; i < src.getLength(); ++i ) {
            builder.add(src.item(i));
        }
        return builder.build();
    }

    private static Element createActionElement(final Document doc, final KeyMapSetting src, final String actionId) {
        Element actionNode = doc.createElement(TAG_ACTION);
        actionNode.setAttribute(ATTR_ACTION_ID, actionId);
        actionNode.setAttribute(ATTR_ACTION_PLUGIN, src.getCommandClassName(actionId).get());
        src.getKeyBinds(actionId).get()
                .stream()
                .sorted()
                .forEach(bind -> {
                    Element bindNode = doc.createElement(TAG_KEYBOARD_SHORTCUT);
                    bindNode.setAttribute(ATTR_KBSC_COMPONENT, bind.getSelector());
                    bindNode.setAttribute(ATTR_KBSC_KEYSTROKE, bind.getKeyStroke().getName());
                    actionNode.appendChild(bindNode);
                });
        return actionNode;
    }

    private static boolean isElementNode(final Node node) {
        return Node.ELEMENT_NODE == node.getNodeType();
    }

    @Override
    public KeyMapSetting load(final InputStream source) throws Exception {
        Document doc = buildDocument(source);
        KeyMapSetting setting = new KeyMapSettingImpl(parseSettingName(doc));
        parseActionIds(doc).stream().forEach(id -> {
            setting.addCommand(id, parsePluginName(doc, id));
            setting.addKeyBinds(id, createKeyBinds(doc, id));
        });
        return setting;
    }

    @Override
    public boolean save(final OutputStream dist, final KeyMapSetting setting) throws Exception {
        Document doc = createSettingDocument(setting);
        doc.setXmlVersion(DOCUMENT_VERSION);
        doc.setXmlStandalone(DOCUMENT_STANDALONE);
        doc.setStrictErrorChecking(DOCUMENT_STRICT);
        save(dist, doc);
        return true;
    }
}
