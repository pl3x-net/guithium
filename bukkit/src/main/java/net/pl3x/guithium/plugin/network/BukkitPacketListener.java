package net.pl3x.guithium.plugin.network;

import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.network.PacketListener;
import net.pl3x.guithium.api.network.packet.ButtonClickPacket;
import net.pl3x.guithium.api.network.packet.CheckboxTogglePacket;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.ElementPacket;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import net.pl3x.guithium.api.network.packet.TexturesPacket;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.plugin.event.HelloEvent;
import net.pl3x.guithium.plugin.player.BukkitPlayer;
import org.jetbrains.annotations.NotNull;

public class BukkitPacketListener implements PacketListener {
    private final WrappedPlayer player;

    public BukkitPacketListener(@NotNull WrappedPlayer player) {
        this.player = player;
    }

    @Override
    public void handleButtonClick(@NotNull ButtonClickPacket packet) {
        Screen screen = this.player.getCurrentScreen();
        if (screen != null && screen.getKey().equals(packet.getScreen())) {
            if (screen.getElements().get(packet.getButton()) instanceof Button button) {
                Button.OnClick onClick = button.onClick();
                if (onClick != null) {
                    onClick.accept(screen, button, player);
                }
            }
        }
    }

    @Override
    public void handleCheckboxToggle(@NotNull CheckboxTogglePacket packet) {
        Screen screen = this.player.getCurrentScreen();
        if (screen != null && screen.getKey().equals(packet.getScreen())) {
            if (screen.getElements().get(packet.getCheckbox()) instanceof Checkbox checkbox) {
                Checkbox.OnClick onClick = checkbox.onClick();
                if (onClick != null) {
                    onClick.accept(screen, checkbox, player, packet.getChecked());
                }
            }
        }
    }

    @Override
    public void handleCloseScreen(@NotNull CloseScreenPacket packet) {
        Screen screen = this.player.getCurrentScreen();
        if (screen != null) {
            if (screen.getKey().equals(packet.getScreenKey())) {
                this.player.setCurrentScreen(null);
            }
        }
    }

    @Override
    public void handleElement(@NotNull ElementPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleHello(@NotNull HelloPacket packet) {
        int protocol = packet.getProtocol();

        System.out.println(this.player.getName() + " is using Guithium with protocol " + protocol);

        // reply to the player
        this.player.getConnection().send(new HelloPacket());

        // tell client about textures
        TexturesPacket texturesPacket = Guithium.api().getTextureManager().getPacket();
        if (texturesPacket != null) {
            this.player.getConnection().send(texturesPacket);
        }

        // tell other plugins about this hello
        new HelloEvent(((BukkitPlayer) this.player).unwrap()).callEvent();
    }

    @Override
    public void handleOpenScreen(@NotNull OpenScreenPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public void handleTextures(@NotNull TexturesPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported.");
    }
}
