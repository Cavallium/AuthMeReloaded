package fr.xephi.authme.initialization;

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class DataAsyncScheduler extends DataScheduler implements AsyncScheduler {
    private final AsyncScheduler scheduler;

    public DataAsyncScheduler(AsyncScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public @NotNull ScheduledTask runNow(@NotNull Plugin plugin, @NotNull Consumer<ScheduledTask> task) {
        return scheduler.runNow(plugin, new DataScheduledTaskConsumer(task));
    }

    @Override
    public @NotNull ScheduledTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<ScheduledTask> task, long delay, @NotNull TimeUnit unit) {
        return scheduler.runDelayed(plugin, new DataScheduledTaskConsumer(task), delay, unit);
    }

    @Override
    public @NotNull ScheduledTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<ScheduledTask> task, long initialDelay, long period, @NotNull TimeUnit unit) {
        return scheduler.runAtFixedRate(plugin, new DataScheduledTaskConsumer(task), initialDelay, period, unit);
    }

    @Override
    public void cancelTasks(@NotNull Plugin plugin) {
        scheduler.cancelTasks(plugin);
    }

}
