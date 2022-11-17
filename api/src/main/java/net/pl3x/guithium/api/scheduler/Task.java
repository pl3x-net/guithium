package net.pl3x.guithium.api.scheduler;

public abstract class Task implements Runnable {
    final int delay;
    final boolean repeat;

    boolean cancelled = false;
    int tick;

    public Task(int delay) {
        this(delay, false);
    }

    public Task(int delay, boolean repeat) {
        this.delay = delay;
        this.repeat = repeat;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean cancelled() {
        return this.cancelled;
    }
}
