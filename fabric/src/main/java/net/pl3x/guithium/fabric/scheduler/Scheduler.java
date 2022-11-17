package net.pl3x.guithium.fabric.scheduler;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.pl3x.guithium.api.scheduler.AbstractScheduler;

public class Scheduler extends AbstractScheduler {
    @Override
    public void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> tick());
    }
}
