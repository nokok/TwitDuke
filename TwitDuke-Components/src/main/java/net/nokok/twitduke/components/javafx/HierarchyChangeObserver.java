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
package net.nokok.twitduke.components.javafx;

import java.util.ArrayList;
import java.util.function.Consumer;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;

/**
 * JavaFXアプリケーションの階層構造が変化を関するクラス
 *
 * コールバックの登録を行える
 *
 * <code>
 * HierarchyChangeObserver observer = new HierarchyChangeObserver();
 * observer.addOnAddedCallback('ADDED_CALLBACK');
 * observer.addOnRemovedCallback('REMOVED_CALLBACK');
 * observer.mark(scene.getRoot());
 * </code>
 *
 * @author wtnbsts
 */
public class HierarchyChangeObserver implements ListChangeListener<Node> {

    private final AddEventCallbackList onAddedCallbackList = new AddEventCallbackList();
    private final RemoveEventCallbackList onRemovedCallbackLList = new RemoveEventCallbackList();

    public HierarchyChangeObserver() {
    }

    public void mark(final Parent parent) {
        ObservableList<Node> children = parent.getChildrenUnmodifiable();
        children.removeListener(this);
        children.addListener(this);
        children.parallelStream()
            .filter(child -> child instanceof Parent)
            .map(child -> (Parent) child)
            .forEachOrdered(this::mark);
    }

    public boolean addOnAddedCallback(Consumer<Node> callback) {
        return onAddedCallbackList.add(callback);
    }

    public boolean removeOnAddedCallback(Consumer<Node> callback) {
        return onAddedCallbackList.remove(callback);
    }

    public boolean addOnRemovedCallback(Consumer<Node> callback) {
        return onRemovedCallbackLList.add(callback);
    }

    public boolean removeOnRemovedCallback(Consumer<Node> callback) {
        return onRemovedCallbackLList.remove(callback);
    }

    @Override
    public void onChanged(final ListChangeListener.Change c) {
        while ( c.next() ) {
            c.getRemoved().parallelStream()
                .filter(child -> child instanceof Node)
                .forEach(child -> onRemovedCallbackLList.invoke((Node) child));

            c.getAddedSubList().parallelStream()
                .filter(child -> child instanceof Node)
                .forEach(child -> onAddedCallbackList.invoke((Node) child));

            c.getAddedSubList().parallelStream()
                .filter(child -> child instanceof Parent)
                .forEach(child -> this.mark((Parent) child));
        }
    }

    private class AddEventCallbackList extends CallbackList {
    }

    private class RemoveEventCallbackList extends CallbackList {
    }

    private class CallbackList extends ArrayList<Consumer<Node>> {

        protected void invoke(final Node obj) {
            super.parallelStream().forEach(callback -> callback.accept(obj));
        }
    }
}
