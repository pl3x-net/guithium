package net.pl3x.servergui.fabric.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.fabric.ServerGUIFabric;

import java.lang.reflect.Type;
import java.util.Map;

public class TexturesPacket extends Packet {
    public static final ResourceLocation CHANNEL = new ResourceLocation(ServerGUI.MOD_ID, "textures");

    private static final Type TYPE_TOKEN = new TypeToken<Map<String, String>>() {
    }.getType();

    public static void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender sender) {
        ByteArrayDataInput in = in(buf.accessByteBufWithCorrectSize());
        String json = in.readUTF();

        Map<String, String> textures = Gson.fromJson(json, TYPE_TOKEN);
        textures.forEach((key, url) -> ServerGUIFabric.instance().getTextureManager().add(Key.of(key), url));
    }
}
