package net.pl3x.guithium.api.scheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the tick based task scheduler.
 */
public abstract class AbstractScheduler {
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Create a new scheduler.
     */
    public AbstractScheduler() {
        // Empty constructor to pacify javadoc lint
    }

    /**
     * Register the scheduler so it starts ticking.
     */
    public abstract void register();

    /**
     * Tick the scheduler.
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
     * Add non-repeating task to the scheduler to run on the next tick.
     *
     * @param runnable Task to add
     */
    public void addTask(@NotNull Runnable runnable) {
        addTask(0, runnable);
    }

    /**
     * Add non-repeating task to the scheduler with delay.
     *
     * @param delay    Delay (in ticks) before task runs
     * @param runnable Task to add
     */
    public void addTask(int delay, @NotNull Runnable runnable) {
        addTask(delay, false, runnable);
    }

    /**
     * Add task to the scheduler.
     *
     * @param delay    Delay (in ticks) before task runs
     * @param repeat   Whether task should start again after running
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
