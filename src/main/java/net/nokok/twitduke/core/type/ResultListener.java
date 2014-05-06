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

/**
 * 成功か失敗かを知る必要がある非同期タスクの結果を受け取るリスナーです
 *
 * @param <E> エラー時に利用できるオブジェクトの型
 * @param <S> 成功時に利用できるオブジェクトの型
 */
public interface ResultListener<E, S> {

    /**
     * タスク実行中にエラーが発生した時に呼ばれます
     *
     * @param error エラー発生時に利用できるオブジェクト
     */
    void onError(AsyncTaskOnError<E> error);

    /**
     * タスク実行が成功した時に呼ばれます
     *
     * @param result 成功時に利用できるオブジェクト
     */
    void Success(AsyncTaskOnSuccess<S> result);
}
