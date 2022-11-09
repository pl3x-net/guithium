package net.pl3x.servergui.fabric.network.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.texture.TextureManager;

import java.lang.reflect.Type;
import java.util.Map;

public class TexturesPacket extends Packet {
    public static final Identifier CHANNEL = new Identifier(ServerGUIFabric.MOD_ID, "textures");

    private static final Type TYPE_TOKEN = new TypeToken<Map<String, String>>() {
    }.getType();

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        ByteArrayDataInput in = in(buf.getWrittenBytes());
        String json = in.readUTF();

        System.out.println(json);

        Map<String, String> textures = ServerGUIFabric.instance().gson().fromJson(json, TYPE_TOKEN);

        TextureManager textureManager = ServerGUIFabric.instance().getTextureManager();
        textures.forEach(textureManager::add);
    }
}
