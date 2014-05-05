/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.core.type;

/**
 *
 * @author noko < nokok.kz at gmail.com >
 */
public class UncheckedScriptException extends RuntimeException {

    public UncheckedScriptException(Exception e) {
        super(e);
    }

    public UncheckedScriptException(String s) {
        super(s);
    }
}
