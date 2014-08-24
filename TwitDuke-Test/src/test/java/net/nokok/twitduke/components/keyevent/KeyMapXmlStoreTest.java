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

import com.google.common.io.Resources;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import javafx.scene.input.KeyCombination;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by wtnbsts on 2014/07/24.
 */
public class KeyMapXmlStoreTest {

    private XmlKeyMapStore store;
    private URL xmlPath
                = Resources.getResource(String.join(File.separator, "keyevent", "default_storetest.xml"));
    private String xmlValue;
    private ByteArrayInputStream xmlStream;

    @Before
    public void setUp() throws IOException {
        store = new XmlKeyMapStore();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(xmlPath.openStream()));
        String line;
        while ( (line = br.readLine()) != null ) {
            sb.append(line).append(System.lineSeparator());
        }
        xmlValue = sb.toString();
        xmlStream = new ByteArrayInputStream(xmlValue.getBytes());
    }

    @After
    public void tearDown() throws IOException {
        xmlStream.close();
    }

    @Test
    public void testLoad() throws Exception {
        assertNotNull(store.load(xmlStream));
    }

    @Test
    public void testSettingName() throws Exception {
        KeyMapSetting setting = store.load(xmlStream);
        assertEquals("default", setting.getSettingName().get());
    }

    @Test
    public void testCommandIds() throws Exception {
        KeyMapSetting setting = store.load(xmlStream);
        List<String> commandIds = setting.getCommandIds().get();
        assertTrue(commandIds.contains("paste"));
        assertTrue(commandIds.contains("cut up to line end."));
        assertEquals(7, commandIds.size());
    }

    @Test
    public void testCommandClassName() throws Exception {
        KeyMapSetting setting = store.load(xmlStream);
        assertEquals("net.nokok.twitduke.components.actions.Action_Paste",
                     setting.getCommandClassName("paste").get());
        assertEquals(
            "net.nokok.twitduke.components.actions.Action_CutUpToLineEnd",
            setting.getCommandClassName("cut up to line end.").get()
        );
        assertEquals("Action_Retweet", setting.getCommandClassName("retweet").get());
    }

    @Test
    public void testKeyBinds() throws Exception {
        KeyMapSetting setting = store.load(xmlStream);
        assertEquals(2, setting.getKeyBinds("paste").get().size());
        assertEquals(2, setting.getKeyBinds("cut up to line end.").get().size());
        assertEquals(0, setting.getKeyBinds("retweet").get().size());
    }

    @Test
    public void testKeyBind() throws Exception {
        KeyMapSetting setting = store.load(xmlStream);
        KeyBind paste0 = setting.getKeyBinds("paste").get().get(0);
        assertEquals(KeyCombination.keyCombination("meta+y"), paste0.getKeyStroke());
        assertEquals("AnyClassName", paste0.getSelector());

        KeyBind cut2 = setting.getKeyBinds("cut up to line end.").get().get(0);
        assertEquals(KeyCombination.keyCombination("meta+k"), cut2.getKeyStroke());
        assertEquals("#anyFxId", cut2.getSelector());
    }

    @Test
    public void testSave() throws Exception {
        KeyMapSetting setting = store.load(xmlStream);
        OutputStream out = new ByteArrayOutputStream();
        assertTrue(store.save(out, setting));
        assertEquals(
            xmlValue.replaceAll("\\n", "").replaceAll("\\s+", " "),
            out.toString().replaceAll("\\n", "").replaceAll("\\s+", " ")
        );
    }
}
