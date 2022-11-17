package net.pl3x.servergui.fabric.net;

import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.net.packet.ButtonClickPacket;
import net.pl3x.servergui.api.net.packet.CloseScreenPacket;
import net.pl3x.servergui.api.net.packet.ElementPacket;
import net.pl3x.servergui.api.net.packet.HelloPacket;
import net.pl3x.servergui.api.net.packet.OpenScreenPacket;
import net.pl3x.servergui.api.net.packet.TexturesPacket;
import net.pl3x.servergui.fabric.ServerGUI;
import net.pl3x.servergui.fabric.gui.element.RenderableElement;
import net.pl3x.servergui.fabric.gui.screen.RenderableScreen;

public class PacketListener implements net.pl3x.servergui.api.net.PacketListener {
    @Override
    public void handleButtonClick(ButtonClickPacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleCloseScreen(CloseScreenPacket packet) {
        // client does not send this packet to the server
        if (ServerGUI.client.screen instanceof RenderableScreen screen) {
            if (screen.getScreen().equals(packet.getScreen())) {
                screen.close();
            }
        }
    }

    @Override
    public void handleElement(ElementPacket packet) {
        Element element = packet.getElement();

        if (ServerGUI.client.screen instanceof RenderableScreen currentScreen) {
            RenderableElement renderableElement = currentScreen.getElements().get(element.getKey());
            if (renderableElement != null) {
                renderableElement.setElement(element);
                return;
            }
        }

        for (RenderableScreen renderableScreen : ServerGUI.instance().getScreenManager().getAll().values()) {
            RenderableElement renderableElement = renderableScreen.getElements().get(element.getKey());
            if (renderableElement != null) {
                renderableElement.setElement(element);
            }
        }
    }

    @Override
    public void handleHello(HelloPacket packet) {
        int protocol = packet.getProtocol();
        if (protocol == net.pl3x.servergui.api.ServerGUI.PROTOCOL) {
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
            ServerGUI.instance().getScreenManager().add(renderableScreen);
        } else {
            renderableScreen.open();
        }
    }

    @Override
    public void handleTextures(TexturesPacket packet) {
        packet.getTextures().forEach((id, url) -> {
            // add texture to manager
            ServerGUI.instance().getTextureManager().add(id, url);
        });
    }
}
