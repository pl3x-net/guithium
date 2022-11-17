package net.pl3x.guithium.fabric.scheduler;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.api.scheduler.AbstractScheduler;

public class Scheduler extends AbstractScheduler {
    @Override
    public void register() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> startTick());
        ClientTickEvents.END_CLIENT_TICK.register(client -> endTick());
    }

    @Override
    protected void startTick() {
        Guithium.client = Minecraft.getInstance();
        Guithium.screenWidth = Guithium.client.getWindow().getGuiScaledWidth();
        Guithium.screenHeight = Guithium.client.getWindow().getGuiScaledHeight();
    }
}
