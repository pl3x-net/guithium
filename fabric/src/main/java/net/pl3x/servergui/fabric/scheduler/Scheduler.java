package net.pl3x.servergui.fabric.scheduler;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.pl3x.servergui.api.scheduler.AbstractScheduler;
import net.pl3x.servergui.fabric.ServerGUIFabric;

public class Scheduler extends AbstractScheduler {
    @Override
    public void register() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> startTick());
        ClientTickEvents.END_CLIENT_TICK.register(client -> endTick());
    }

    @Override
    protected void startTick() {
        ServerGUIFabric.client = Minecraft.getInstance();
        ServerGUIFabric.screenWidth = ServerGUIFabric.client.getWindow().getGuiScaledWidth();
        ServerGUIFabric.screenHeight = ServerGUIFabric.client.getWindow().getGuiScaledHeight();
    }
}
