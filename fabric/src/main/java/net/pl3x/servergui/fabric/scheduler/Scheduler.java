package net.pl3x.servergui.fabric.scheduler;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.pl3x.servergui.api.scheduler.AbstractScheduler;
import net.pl3x.servergui.fabric.ServerGUI;

public class Scheduler extends AbstractScheduler {
    @Override
    public void register() {
        ClientTickEvents.START_CLIENT_TICK.register(client -> startTick());
        ClientTickEvents.END_CLIENT_TICK.register(client -> endTick());
    }

    @Override
    protected void startTick() {
        ServerGUI.client = Minecraft.getInstance();
        ServerGUI.screenWidth = ServerGUI.client.getWindow().getGuiScaledWidth();
        ServerGUI.screenHeight = ServerGUI.client.getWindow().getGuiScaledHeight();
    }
}
