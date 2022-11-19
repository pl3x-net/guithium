package net.pl3x.guithium.fabric.net;

import net.minecraft.client.Minecraft;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.net.packet.ButtonClickPacket;
import net.pl3x.guithium.api.net.packet.CloseScreenPacket;
import net.pl3x.guithium.api.net.packet.ElementPacket;
import net.pl3x.guithium.api.net.packet.HelloPacket;
import net.pl3x.guithium.api.net.packet.OpenScreenPacket;
import net.pl3x.guithium.api.net.packet.TexturesPacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.element.RenderableElement;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class PacketListener implements net.pl3x.guithium.api.net.PacketListener {
    @Override
    public void handleButtonClick(@NotNull ButtonClickPacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleCloseScreen(@NotNull CloseScreenPacket packet) {
        Screen screen = packet.getScreen();
        if (screen.getType() == Screen.Type.HUD) {
            Guithium.instance().getHudManager().remove(screen.getKey());
        } else {
            if (Minecraft.getInstance().screen instanceof RenderableScreen renderableScreen) {
                if (renderableScreen.getScreen().getKey().equals(packet.getScreen().getKey())) {
                    renderableScreen.close();
                }
            }
        }
    }

    @Override
    public void handleElement(@NotNull ElementPacket packet) {
        Element element = packet.getElement();

        if (Minecraft.getInstance().screen instanceof RenderableScreen currentScreen) {
            RenderableElement renderableElement = currentScreen.getElements().get(element.getKey());
            if (renderableElement != null) {
                renderableElement.setElement(element);
                return;
            }
        }

        for (RenderableScreen renderableScreen : Guithium.instance().getHudManager().getAll().values()) {
            RenderableElement renderableElement = renderableScreen.getElements().get(element.getKey());
            if (renderableElement != null) {
                renderableElement.setElement(element);
            }
        }
    }

    @Override
    public void handleHello(@NotNull HelloPacket packet) {
        int protocol = packet.getProtocol();
        if (protocol == net.pl3x.guithium.api.Guithium.PROTOCOL) {
            System.out.println("Server responded with correct protocol (" + protocol + ")");
        } else {
            System.out.println("Server responded with a different protocol (" + protocol + ")");
        }
    }

    @Override
    public void handleOpenScreen(@NotNull OpenScreenPacket packet) {
        Screen screen = packet.getScreen();

        RenderableScreen renderableScreen = new RenderableScreen(screen);

        if (screen.getType() == Screen.Type.HUD) {
            Guithium.instance().getHudManager().add(renderableScreen);
        } else {
            renderableScreen.open();
        }
    }

    @Override
    public void handleTextures(@NotNull TexturesPacket packet) {
        packet.getTextures().forEach((id, url) -> {
            // add texture to manager
            Guithium.instance().getTextureManager().add(id, url);
        });
    }
}
