package net.pl3x.guithium.api.scheduler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a tick scheduler.
 */
public abstract class AbstractScheduler {
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Register this tick scheduler.
     */
    public abstract void register();

    /**
     * Tick this scheduler.
     */
    protected void tick() {
        Iterator<Task> iter = this.tasks.iterator();
        while (iter.hasNext()) {
            Task task = iter.next();
            if (task.tick++ < task.delay) {
                continue;
            }
            if (task.cancelled()) {
                iter.remove();
                continue;
            }
            task.run();
            if (task.repeat) {
                task.tick = 0;
                continue;
            }
            iter.remove();
        }
    }

    /**
     * Cancel all scheduled tasks.
     */
    public void cancelAll() {
        Iterator<Task> iter = this.tasks.iterator();
        while (iter.hasNext()) {
            iter.next().cancel();
            iter.remove();
        }
    }

    /**
     * Add task to the scheduler.
     *
     * @param task Task to add
     */
    public void addTask(@NotNull Task task) {
        this.tasks.add(task);
    }

    /**
     * Add task to the scheduler.
     *
     * @param delay    Delay (in ticks) before task starts
     * @param runnable Task to add
     */
    public void addTask(int delay, @NotNull Runnable runnable) {
        addTask(delay, false, runnable);
    }

    /**
     * Add task to the scheduler.
     *
     * @param delay    Delay (in ticks) before task starts
     * @param repeat   Delay (in ticks) before task repeats
     * @param runnable Task to add
     */
    public void addTask(int delay, boolean repeat, @NotNull Runnable runnable) {
        addTask(new Task(delay, repeat) {
            @Override
            public void run() {
                runnable.run();
            }
        });
    }
}
