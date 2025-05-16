package net.pl3x.guithium.api.scheduler;

/**
 * Represents a task that can be scheduled
 */
public abstract class Task implements Runnable {
    final int delay;
    final boolean repeat;

    boolean cancelled = false;
    int tick;

    /**
     * Create a new task.
     *
     * @param delay Delay (in ticks) before task runs
     */
    public Task(int delay) {
        this(delay, false);
    }

    /**
     * Create a new task.
     *
     * @param delay  Delay (in ticks) before task runs
     * @param repeat Whether task should start again after running
     */
    public Task(int delay, boolean repeat) {
        this.delay = delay;
        this.repeat = repeat;
    }

    /**
     * Cancel the task from running.
     */
    public void cancel() {
        this.cancelled = true;
    }

    /**
     * Check if task is cancelled.
     *
     * @return {@code true} is cancelled, otherwise {@code false}
     */
    public boolean cancelled() {
        return this.cancelled;
    }
}
