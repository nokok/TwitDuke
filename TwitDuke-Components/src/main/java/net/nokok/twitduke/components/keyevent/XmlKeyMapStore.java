/*
 * The MIT License
 *
 * Copyright 2014 satanabe1.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
 * {@link KeyMapStore}のXML実装
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

    /**
     * DOMを文字列として出力する
     *
     * @param dist     出力先ストリーム
     * @param document 出力元DOM
     *
     * @throws Exception DOM整形のエラーや、IOExceptionが発生するかもしれない
     */
    private static void save(final OutputStream dist, final Document document) throws Exception {
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.METHOD, TF_METHOD);
        transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, TF_INDENT);
        transformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", TF_ISIZE);
        transformer.transform(new DOMSource(document), new StreamResult(dist));
    }

    /**
     * 設定情報から、DOMを生成する
     *
     * @param setting 設定情報
     *
     * @return DOM化された設定情報
     *
     * @throws Exception DOM整形のエラーや、IOExceptionが発生するかもしれない
     */
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

    /**
     * DOMから、設定情報の名前を取得する(Default,Emacsとかが考えられる？？？)
     *
     * @param document 設定情報のDOM
     *
     * @return 設定情報名
     */
    private static String parseSettingName(final Document document) {
        return getAttribute(document.getFirstChild(), ATTR_SETTING_NAME);
    }

    /**
     * 設定情報のDOMから、コマンド名一覧を取得する
     *
     * @param document 設定情報のDOM
     *
     * @return コマンド名一覧
     */
    private static List<String> parseActionIds(final Document document) {
        Stream<Node> actions = stream(document.getFirstChild().getChildNodes());
        return actions.filter(XmlKeyMapStore::isElementNode)
                .map(node -> getAttribute(node, ATTR_ACTION_ID))
                .collect(Collectors.toList());
    }

    /**
     * コマンド名と紐付けられている、コマンドの実体のクラス名を取得する
     *
     * @param document 設定情報のDOM
     * @param id       コマンド名
     *
     * @return コマンドクラス名
     */
    private static String parsePluginName(final Document document, final String id) {
        return getAttribute(getActionNodeById(document, id), ATTR_ACTION_PLUGIN);
    }

    /**
     * 設定情報のDOMを解析して、コマンド名に対応するキーバインドの一覧を取得する
     *
     * @param document 設定情報のDOM
     * @param id       コマンド名
     *
     * @return コマンド名に紐付けられているキーバインド一覧
     *
     * @throws RuntimeException
     */
    private static List<KeyBind> createKeyBinds(final Document document, final String id) throws RuntimeException {
        Node actionNode = getActionNodeById(document, id);
        return stream(actionNode.getChildNodes())
                .filter(XmlKeyMapStore::isElementNode)
                .map(XmlKeyMapStore::createKeyBind)
                .collect(Collectors.toList());
    }

    /**
     * 設定情報のDOMノードから、キーバインド情報を生成する
     *
     * @param keyBindNode キーバインドを表すDOMノード
     *
     * @return キーバインド情報
     */
    private static KeyBind createKeyBind(final Node keyBindNode) {
        try {
            KeyCombination shortcutKey = KeyCombination.keyCombination(getAttribute(keyBindNode, ATTR_KBSC_KEYSTROKE));
            String targetSelector = getAttribute(keyBindNode, ATTR_KBSC_COMPONENT);
            return new KeyBind(shortcutKey, targetSelector);
        } catch (NumberFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 外部入力から、DOMを生成する
     *
     * @param source 設定情報源
     *
     * @return 設定情報を表すDOM
     *
     * @throws ParserConfigurationException
     * @throws IOException
     * @throws SAXException
     */
    private static Document buildDocument(final InputStream source)
            throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        return builder.parse(source);
    }

    /**
     * ノードについている属性を文字列で取得する
     *
     * @param node
     * @param attrName
     *
     * @return
     */
    private static String getAttribute(final Node node, final String attrName) {
        return node.getAttributes().getNamedItem(attrName).getTextContent();
    }

    /**
     * キーボードショートカットを表すノードを取得する
     *
     * @param document 設定情報のDOM
     * @param id
     *
     * @return
     */
    private static Node getActionNodeById(final Document document, final String id) {
        Stream<Node> actions = stream(document.getFirstChild().getChildNodes());
        return actions.filter(XmlKeyMapStore::isElementNode)
                .filter(node -> id.equals(getAttribute(node, ATTR_ACTION_ID)))
                .findFirst().get();
    }

    /**
     * {@link NodeList}からStreamを生成するする
     *
     * @param src
     *
     * @return
     */
    private static Stream<Node> stream(final NodeList src) {
        Stream.Builder<Node> builder = Stream.builder();
        for ( int i = 0; i < src.getLength(); ++i ) {
            builder.add(src.item(i));
        }
        return builder.build();
    }

    /**
     *
     * @param doc      設定情報のDOM
     * @param src
     * @param actionId
     *
     * @return
     */
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
