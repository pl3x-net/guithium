package net.pl3x.guithium.fabric.util;

import com.google.common.collect.Queues;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.jetbrains.annotations.NotNull;

// recreated from 1.21.4
public abstract class RenderQueue {
    private static final ConcurrentLinkedQueue<Runnable> recordingQueue = Queues.newConcurrentLinkedQueue();

    public static void recordRenderCall(@NotNull Runnable runnable) {
        recordingQueue.add(runnable);
    }

    // called from RenderSystem#flipFrame using mixin
    public static void process() {
        while (!recordingQueue.isEmpty()) {
            recordingQueue.poll().run();
        }
    }
}
