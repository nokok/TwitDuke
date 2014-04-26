/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.api;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author noko <nokok.kz at gmail.com>
 */
public class SystemInfoImplTest {

    /**
     * Test of license method, of class SystemInfoImpl.
     */
    @Test
    public void testLicense() {
        SystemInfo systemInfo = new SystemInfoImpl();
        assertEquals(systemInfo.license(), "The MIT License (MIT)\n"
                                           + "\n"
                                           + "Copyright (c) 2014 noko\n"
                                           + "\n"
                                           + "Permission is hereby granted, free of charge, to any person obtaining a copy\n"
                                           + "of this software and associated documentation files (the \"Software\"), to deal\n"
                                           + "in the Software without restriction, including without limitation the rights\n"
                                           + "to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n"
                                           + "copies of the Software, and to permit persons to whom the Software is\n"
                                           + "furnished to do so, subject to the following conditions:\n"
                                           + "\n"
                                           + "The above copyright notice and this permission notice shall be included in\n"
                                           + "all copies or substantial portions of the Software.\n"
                                           + "\n"
                                           + "THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR\n"
                                           + "IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,\n"
                                           + "FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE\n"
                                           + "AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER\n"
                                           + "LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,\n"
                                           + "OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN\n"
                                           + "THE SOFTWARE.");
    }

    /**
     * Test of version method, of class SystemInfoImpl.
     */
    @Test
    public void testVersion() {
        SystemInfo info = new SystemInfoImpl();
        assertEquals(info.version(), "0.2");
    }
}