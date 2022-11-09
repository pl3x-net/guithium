package net.pl3x.servergui.fabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.network.packet.GuiPacket;
import net.pl3x.servergui.fabric.network.packet.HelloPacket;
import net.pl3x.servergui.fabric.network.packet.TexturesPacket;

public class NetworkManager {
    public static void register() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!client.isInSingleplayer()) {
                ServerGUIFabric.instance().getScheduler().addTask(0, () -> new HelloPacket().send());
            }
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ServerGUIFabric.instance().getScheduler().cancelAll();
            ServerGUIFabric.instance().getTextureManager().clear();
        });

        ClientPlayNetworking.registerGlobalReceiver(GuiPacket.CHANNEL, GuiPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(HelloPacket.CHANNEL, HelloPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(TexturesPacket.CHANNEL, TexturesPacket::receive);
    }
}
