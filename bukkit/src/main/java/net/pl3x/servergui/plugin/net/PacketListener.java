package net.pl3x.servergui.plugin.net;

import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.api.gui.element.Button;
import net.pl3x.servergui.api.net.packet.ButtonClickPacket;
import net.pl3x.servergui.api.net.packet.CloseScreenPacket;
import net.pl3x.servergui.api.net.packet.ElementPacket;
import net.pl3x.servergui.api.net.packet.HelloPacket;
import net.pl3x.servergui.api.net.packet.OpenScreenPacket;
import net.pl3x.servergui.api.net.packet.TexturesPacket;
import net.pl3x.servergui.api.player.Player;
import net.pl3x.servergui.plugin.event.HelloEvent;

public class PacketListener implements net.pl3x.servergui.api.net.PacketListener {
    private final Player player;

    public PacketListener(Player player) {
        this.player = player;
    }

    @Override
    public void handleButtonClick(ButtonClickPacket packet) {
        String screenId = packet.getScreenId();
        String buttonId = packet.getButtonId();

        Screen screen = this.player.getCurrentScreen();
        if (screen == null || !screen.getKey().toString().equals(screenId)) {
            return;
        }

        if (screen.getElements().get(Key.of(buttonId)) instanceof Button button) {
            Button.TriConsumer<Screen, Button, Player> onClick = button.onClick();
            if (onClick != null) {
                onClick.accept(screen, button, player);
            }
        }
    }

    @Override
    public void handleCloseScreen(CloseScreenPacket packet) {
        Screen screen = this.player.getCurrentScreen();
        if (screen != null) {
            if (screen.equals(packet.getScreen())) {
                this.player.setCurrentScreen(null);
            }
        }
    }

    @Override
    public void handleElement(ElementPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleHello(HelloPacket packet) {
        int protocol = packet.getProtocol();

        System.out.println(this.player.getName() + " is using ServerGUI with protocol " + protocol);

        // reply to the player
        this.player.getConnection().send(new HelloPacket());

        // tell client about textures
        TexturesPacket texturesPacket = ServerGUI.api().getTextureManager().getPacket();
        if (texturesPacket != null) {
            this.player.getConnection().send(texturesPacket);
        }

        // tell other plugins about this hello
        new HelloEvent(((net.pl3x.servergui.plugin.player.Player) this.player).getBukkitPlayer()).callEvent();
    }

    @Override
    public void handleOpenScreen(OpenScreenPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleTextures(TexturesPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported.");
    }
}
