/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.type;

/**
 * スクリプト(プラグイン)が実行可能です
 *
 * @author noko < nokok.kz at gmail.com >
 */
public interface RunnableScript {

    /**
     * スクリプト(プラグイン)を実行します
     */
    void runScript();
}
