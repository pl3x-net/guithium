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

public class PacketListener implements net.pl3x.guithium.api.net.PacketListener {
    @Override
    public void handleButtonClick(ButtonClickPacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleCloseScreen(CloseScreenPacket packet) {
        Screen screen = packet.getScreen();
        if (screen.getType() == Screen.Type.HUD) {
            Guithium.instance().getScreenManager().remove(screen.getKey());
        } else {
            if (Minecraft.getInstance().screen instanceof RenderableScreen renderableScreen) {
                if (renderableScreen.getScreen().equals(packet.getScreen())) {
                    renderableScreen.close();
                }
            }
        }
    }

    @Override
    public void handleElement(ElementPacket packet) {
        Element element = packet.getElement();

        if (Minecraft.getInstance().screen instanceof RenderableScreen currentScreen) {
            RenderableElement renderableElement = currentScreen.getElements().get(element.getKey());
            if (renderableElement != null) {
                renderableElement.setElement(element);
                return;
            }
        }

        for (RenderableScreen renderableScreen : Guithium.instance().getScreenManager().getAll().values()) {
            RenderableElement renderableElement = renderableScreen.getElements().get(element.getKey());
            if (renderableElement != null) {
                renderableElement.setElement(element);
            }
        }
    }

    @Override
    public void handleHello(HelloPacket packet) {
        int protocol = packet.getProtocol();
        if (protocol == net.pl3x.guithium.api.Guithium.PROTOCOL) {
            System.out.println("Server responded with correct protocol (" + protocol + ")");
        } else {
            System.out.println("Server responded with a different protocol (" + protocol + ")");
        }
    }

    @Override
    public void handleOpenScreen(OpenScreenPacket packet) {
        Screen screen = packet.getScreen();
        if (screen == null) {
            return;
        }

        RenderableScreen renderableScreen = new RenderableScreen(screen);

        if (screen.getType() == Screen.Type.HUD) {
            Guithium.instance().getScreenManager().add(renderableScreen);
        } else {
            renderableScreen.open();
        }
    }

    @Override
    public void handleTextures(TexturesPacket packet) {
        packet.getTextures().forEach((id, url) -> {
            // add texture to manager
            Guithium.instance().getTextureManager().add(id, url);
        });
    }
}
