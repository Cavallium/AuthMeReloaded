package fr.xephi.authme.initialization;

import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DataGlobalRegionScheduler extends DataScheduler implements GlobalRegionScheduler {
    private final GlobalRegionScheduler scheduler;

    public DataGlobalRegionScheduler(GlobalRegionScheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void execute(@NotNull Plugin plugin, @NotNull Runnable run) {
        scheduler.execute(plugin, new DataScheduledTask(run));
    }

    @Override
    public @NotNull ScheduledTask run(@NotNull Plugin plugin, @NotNull Consumer<ScheduledTask> task) {
        return scheduler.run(plugin, new DataScheduledTaskConsumer(task));
    }

    @Override
    public @NotNull ScheduledTask runDelayed(@NotNull Plugin plugin, @NotNull Consumer<ScheduledTask> task, long delayTicks) {
        return scheduler.runDelayed(plugin, new DataScheduledTaskConsumer(task), delayTicks);
    }

    @Override
    public @NotNull ScheduledTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Consumer<ScheduledTask> task, long initialDelayTicks, long periodTicks) {
        return scheduler.runAtFixedRate(plugin, new DataScheduledTaskConsumer(task), initialDelayTicks, periodTicks);
    }

    @Override
    public void cancelTasks(@NotNull Plugin plugin) {
        scheduler.cancelTasks(plugin);
    }
}
