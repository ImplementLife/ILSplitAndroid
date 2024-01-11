package com.impllife.split.service.util;

import android.util.Log;

import java.util.concurrent.CompletableFuture;

/**
 * Use it like that:<br>
 * Async.{@link #runAsync(Runnable)}<br>
 * .{@link #than(Runnable)}<br>
 * .{@link #catchExcept(Runnable)}<br>
 * .{@link #run()};
 */
public class Async {
    private Runnable run;
    private Runnable runForThan;
    private Runnable runForCatch;
    private Async() {}

    public static Async runAsync(Runnable run) {
        Async async = new Async();
        async.run = run;
        return async;
    }

    public Async than(Runnable run) {
        runForThan = run;
        return this;
    }

    public Async catchExcept(Runnable run) {
        runForCatch = run;
        return this;
    }

    public void run() {
        CompletableFuture.runAsync(() -> {
            try {
                run.run();
            } catch (Throwable t) {
                Log.e("Async#run", "error in async exe", t);
                if (runForCatch != null) {
                    runForCatch.run();
                }
            }
            runForThan.run();
        });
    }
}
