/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.keyevent;

/**
 *
 * @author wtnbsts
 */
public class KeyMapStoreBuilder {

    public KeyMapStore build() {
        return new XmlKeyMapStore();
    }
}
