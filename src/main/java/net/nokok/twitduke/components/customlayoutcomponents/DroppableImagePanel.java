/*
 * The MIT License
 *
 * Copyright 2014 noko
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
package net.nokok.twitduke.components.customlayoutcomponents;

import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import net.nokok.twitduke.components.basic.TWLabel;
import net.nokok.twitduke.components.basic.TWPanel;
import net.nokok.twitduke.core.type.AsyncTaskOnSuccess;
import net.nokok.twitduke.core.type.ThrowableReceivable;

/**
 * パネルにドラッグアンドドロップされたファイルを受け取るパネルです。
 */
public class DroppableImagePanel extends TWPanel {

    private static final long serialVersionUID = 2843592085054940054L;

    private List<AsyncTaskOnSuccess<File>> resultReceivers;
    private List<ThrowableReceivable> errorReceivers;

    /**
     * 新しくパネルを生成します
     */
    public DroppableImagePanel() {
        setTransferHandler(new DropImageHandler());
        add(new TWLabel("この部分に画像をドラッグアンドドロップしてください"));
        setBorder(new LineBorder(Color.WHITE));
    }

    public void onSuccess(AsyncTaskOnSuccess<File> receiver) {
        this.resultReceivers.add(receiver);
    }

    public void onError(ThrowableReceivable receiver) {
        this.errorReceivers.add(receiver);
    }

    private class DropImageHandler extends TransferHandler {

        private static final long serialVersionUID = 4466664724477806797L;

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
        }

        @Override
        public boolean importData(TransferSupport support) {
            if ( !canImport(support) ) {
                return false;
            }
            Transferable transferable = support.getTransferable();
            try {
                @SuppressWarnings("unchecked")
                List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                files.stream().filter(p -> {
                    String path = p.getAbsolutePath();
                    return path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png");
                }).forEach(f -> resultReceivers.forEach(r -> r.onSuccess(f)));
            } catch (UnsupportedFlavorException | IOException ex) {
                errorReceivers.forEach(r -> r.onError(ex));
                return false;
            }
            return true;
        }
    }
}
