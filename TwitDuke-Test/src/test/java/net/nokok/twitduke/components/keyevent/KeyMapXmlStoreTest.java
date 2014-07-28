package net.nokok.twitduke.components.keyevent;

import com.google.common.io.Resources;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by a13612 on 2014/07/24.
 */
public class KeyMapXmlStoreTest {

    private KeyMapXmlStore store;
    private URL xmlPath =
            Resources.getResource(String.join(File.separator, "keyevent", "default_storetest.xml"));
    private String xmlValue;
    private ByteArrayInputStream xmlStream;

    @Before
    public void setUp() throws IOException {
        store = new KeyMapXmlStore();

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(xmlPath.openStream())
        );
        String line;
        while ((line = br.readLine()) != null) {
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
        IKeyMapSetting setting = store.load(xmlStream);
        assertEquals("default", setting.getSettingName());
    }

    @Test
    public void testCommandIds() throws Exception {
        IKeyMapSetting setting = store.load(xmlStream);
        List<String> commandIds = setting.getCommandIds();
        assertTrue(commandIds.contains("paste"));
        assertTrue(commandIds.contains("cut up to line end."));
        assertEquals(3, commandIds.size());
    }

    @Test
    public void testCommandClassName() throws Exception {
        IKeyMapSetting setting = store.load(xmlStream);
        assertEquals("Action_Paste", setting.getCommandClassName("paste"));
        assertEquals(
                "Action_CutUpToLineEnd",
                setting.getCommandClassName("cut up to line end.")
        );
        assertEquals("Action_Retweet", setting.getCommandClassName("retweet"));
    }

    @Test
    public void testKeyBinds() throws Exception {
        IKeyMapSetting setting = store.load(xmlStream);
        assertEquals(4, setting.getKeyBinds("paste").size());
        assertEquals(3, setting.getKeyBinds("cut up to line end.").size());
        assertEquals(0, setting.getKeyBinds("retweet").size());
    }

    @Test
    public void testKeyBind() throws Exception {
        IKeyMapSetting setting = store.load(xmlStream);
        KeyBind paste0 = setting.getKeyBinds("paste").get(0);
        assertEquals("ctrl Y", paste0.getKeyStroke());
        assertEquals("JTextArea", paste0.getTargetComponentName());
        assertEquals(JComponent.WHEN_FOCUSED, paste0.getTargetComponentCondition());

        KeyBind cut2 = setting.getKeyBinds("cut up to line end.").get(2);
        assertEquals("meta K", cut2.getKeyStroke());
        assertEquals("WTextArea", cut2.getTargetComponentName());
        assertEquals(
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT,
                cut2.getTargetComponentCondition()
        );
    }

    @Test
    public void testSave() throws Exception {
        IKeyMapSetting setting = store.load(xmlStream);
        OutputStream out = new ByteArrayOutputStream();
        assertTrue(store.save(out, setting));
        assertEquals(
                xmlValue.replaceAll("\\n", ""),
                out.toString().replaceAll("\\n", "")
        );
    }
}
