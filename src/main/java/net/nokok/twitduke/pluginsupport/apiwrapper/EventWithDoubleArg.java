/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.pluginsupport.apiwrapper;

/**
 *
 * @author noko < nokok.kz at gmail.com >
 */
public interface EventWithDoubleArg<T1, T2> {

    public void onEvent(T1 arg1, T2 arg2);
}
