package net.pl3x.servergui.fabric.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.network.packet.ElementPacket;
import net.pl3x.servergui.fabric.network.packet.HelloPacket;
import net.pl3x.servergui.fabric.network.packet.ScreenPacket;
import net.pl3x.servergui.fabric.network.packet.TexturesPacket;

public class NetworkManager {
    public static void register() {
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (!client.isInSingleplayer()) {
                // send hello on first client tick
                ServerGUIFabric.instance().getScheduler()
                    .addTask(0, () -> new HelloPacket().send());
            }
        });
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            ServerGUIFabric.instance().getScheduler().cancelAll();
            ServerGUIFabric.instance().getTextureManager().clear();
            ServerGUIFabric.instance().getScreenManager().clear();
        });

        ClientPlayNetworking.registerGlobalReceiver(ElementPacket.CHANNEL, ElementPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(HelloPacket.CHANNEL, HelloPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ScreenPacket.CHANNEL, ScreenPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(TexturesPacket.CHANNEL, TexturesPacket::receive);
    }
}
