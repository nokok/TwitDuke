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
package net.nokok.twitduke.core.type;

import java.util.List;
import net.nokok.twitduke.components.Selectable;

/**
 * 任意の項目を選択出来る要素の集まりです
 *
 * @param <T> 要素の型
 */
public interface SelectableElements<T extends Selectable> {

    /**
     * 指定した位置にある要素を取得します
     *
     * @param index 要素を取得する位置
     *
     * @return 指定した位置にある要素
     */
    T get(ListIndex index);

    /**
     * 指定した位置にある要素を取得します
     *
     * @param index
     * @param indexes
     *
     * @return 指定した位置にある要素のリスト
     */
    List<T> get(ListIndex index, ListIndex... indexes);

    /**
     * 指定した範囲にある要素を取得します
     *
     * @param from 要素を取得する始点
     * @param to   要素を取得する終点
     *
     * @return 指定した範囲にある要素のリスト
     */
    List<T> get(ListIndex from, ListIndex to);

    /**
     * 指定した位置の要素を選択します
     *
     * @param index 選択する要素の位置
     *
     * @return 選択された要素
     */
    T select(ListIndex index);

    /**
     * 指定した位置の要素を選択します
     *
     * @param index
     * @param indexes
     *
     * @return 選択した要素のリスト
     */
    List<T> select(ListIndex index, ListIndex... indexes);

    /**
     * 指定した範囲の要素を選択します
     *
     * @param from 選択する要素の始点
     * @param to   選択する要素の終点
     *
     * @return 選択された要素
     */
    List<T> select(ListIndex from, ListIndex to);

    /**
     * 最後の要素を返します
     *
     * @return 最後の要素
     */
    int lastIndex();

    /**
     * 要素の数を返します
     *
     * @return 要素数
     */
    int size();

}
