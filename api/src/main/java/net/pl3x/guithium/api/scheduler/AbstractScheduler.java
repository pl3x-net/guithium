package net.pl3x.guithium.api.scheduler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractScheduler {
    private final List<Task> tasks = new ArrayList<>();

    public abstract void register();

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

    public void cancelAll() {
        Iterator<Task> iter = this.tasks.iterator();
        while (iter.hasNext()) {
            iter.next().cancel();
            iter.remove();
        }
    }

    public void addTask(@NotNull Task task) {
        this.tasks.add(task);
    }

    public void addTask(int delay, @NotNull Runnable runnable) {
        addTask(delay, false, runnable);
    }

    public void addTask(int delay, boolean repeat, @NotNull Runnable runnable) {
        addTask(new Task(delay, repeat) {
            @Override
            public void run() {
                runnable.run();
            }
        });
    }
}
