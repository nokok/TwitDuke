/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.nokok.twitduke.components.basic;

import java.util.concurrent.ForkJoinPool;

/**
 *
 * @author noko
 */
enum UIAsyncTaskPool {

    INSTANCE;

    private final ForkJoinPool pool = new ForkJoinPool();

    public void runTask(Runnable runnable) {
        pool.execute(runnable);
    }
}
