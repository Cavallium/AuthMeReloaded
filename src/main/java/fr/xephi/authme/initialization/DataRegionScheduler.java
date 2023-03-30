package fr.xephi.authme.initialization;

import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DataRegionScheduler extends DataScheduler implements RegionScheduler {
    private final RegionScheduler scheduler;

    public DataRegionScheduler(RegionScheduler scheduler) {
        this.scheduler = scheduler;
    }
    @Override
    public void execute(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Runnable run) {
        scheduler.execute(plugin, world, chunkX, chunkZ, new DataScheduledTask(run));
    }

    @Override
    public void execute(@NotNull Plugin plugin, @NotNull Location location, @NotNull Runnable run) {
        scheduler.execute(plugin, location, new DataScheduledTask(run));
    }

    @Override
    public @NotNull ScheduledTask run(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Consumer<ScheduledTask> task) {
        return scheduler.run(plugin, world, chunkX, chunkZ, new DataScheduledTaskConsumer(task));
    }

    @Override
    public @NotNull ScheduledTask run(@NotNull Plugin plugin, @NotNull Location location, @NotNull Consumer<ScheduledTask> task) {
        return scheduler.run(plugin, location, new DataScheduledTaskConsumer(task));
    }

    @Override
    public @NotNull ScheduledTask runDelayed(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Consumer<ScheduledTask> task, long delayTicks) {
        return scheduler.runDelayed(plugin, world, chunkX, chunkZ, new DataScheduledTaskConsumer(task), delayTicks);
    }

    @Override
    public @NotNull ScheduledTask runDelayed(@NotNull Plugin plugin, @NotNull Location location, @NotNull Consumer<ScheduledTask> task, long delayTicks) {
        return scheduler.runDelayed(plugin, location, new DataScheduledTaskConsumer(task), delayTicks);
    }

    @Override
    public @NotNull ScheduledTask runAtFixedRate(@NotNull Plugin plugin, @NotNull World world, int chunkX, int chunkZ, @NotNull Consumer<ScheduledTask> task, long initialDelayTicks, long periodTicks) {
        return scheduler.runAtFixedRate(plugin, world, chunkX, chunkZ, new DataScheduledTaskConsumer(task), initialDelayTicks, periodTicks);
    }

    @Override
    public @NotNull ScheduledTask runAtFixedRate(@NotNull Plugin plugin, @NotNull Location location, @NotNull Consumer<ScheduledTask> task, long initialDelayTicks, long periodTicks) {
        return scheduler.runAtFixedRate(plugin, location, new DataScheduledTaskConsumer(task), initialDelayTicks, periodTicks);
    }
}
