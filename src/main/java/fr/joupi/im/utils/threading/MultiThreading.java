package fr.joupi.im.utils.threading;

import lombok.experimental.UtilityClass;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@UtilityClass
public class MultiThreading {

    public final ExecutorService pool = Executors.newFixedThreadPool(100, runnable -> {
        final AtomicInteger counter = new AtomicInteger(0);
        return new Thread(runnable, String.format("Thread %s", counter.incrementAndGet()));
    });

    public final ScheduledExecutorService runnablePool = Executors.newScheduledThreadPool(10, runnable -> {
        final AtomicInteger counter = new AtomicInteger(0);
        return new Thread(runnable, "Thread " + counter.incrementAndGet());
    });

    public ScheduledFuture<?> schedule(Runnable r, long initialDelay, long delay, TimeUnit unit) {
        return runnablePool.scheduleAtFixedRate(r, initialDelay, delay, unit);
    }

    public ScheduledFuture<?> schedule(Runnable r, long initialDelay, TimeUnit unit) {
        return runnablePool.schedule(r, initialDelay, unit);
    }

    public void runAsync(Runnable runnable) {
        pool.execute(runnable);
    }

    public int getTotal() {
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) pool;
        return threadPoolExecutor.getActiveCount();
    }

    public void stopTask() {
        pool.shutdown();
        runnablePool.shutdown();
    }

}
