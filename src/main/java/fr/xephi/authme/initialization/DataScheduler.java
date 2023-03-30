package fr.xephi.authme.initialization;

import fr.xephi.authme.ConsoleLogger;
import fr.xephi.authme.output.ConsoleLoggerFactory;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class DataScheduler implements AutoCloseable {
    private final ConsoleLogger logger = ConsoleLoggerFactory.get(DataAsyncScheduler.class);
    private final AtomicInteger runningTasks = new AtomicInteger();

    public void close() {
        logger.info("Waiting for " + runningTasks.get() + " tasks to finish");

        //one minute + some time checking the running state
        int tries = 60;
        int tasks;
        while ((tasks = runningTasks.get()) > 0) {
            if (tries <= 0) {
                logger.info("Async tasks times out after to many tries " + tasks);
                break;
            }

            try {
                sleep();
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                break;
            }

            tries--;
        }
    }

    public void sleep() throws InterruptedException {
        Thread.sleep(300);
    }

    protected class DataScheduledTaskConsumer implements Consumer<ScheduledTask> {
        private final @NotNull Consumer<ScheduledTask> task;

        public DataScheduledTaskConsumer(@NotNull Consumer<ScheduledTask> task) {
            this.task = task;
        }

        @Override
        public void accept(ScheduledTask t) {
            runningTasks.incrementAndGet();
            try {
                task.accept(t);
            } finally {
                runningTasks.decrementAndGet();
            }
        }
    }

    protected class DataScheduledTask implements Runnable {
        private final @NotNull Runnable task;

        public DataScheduledTask(@NotNull Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            runningTasks.incrementAndGet();
            try {
                task.run();
            } finally {
                runningTasks.decrementAndGet();
            }
        }
    }
}
