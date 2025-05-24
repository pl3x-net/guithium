package net.pl3x.guithium.plugin.network;

import java.util.Collection;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.action.actions.player.PlayerJoinedAction;
import net.pl3x.guithium.api.action.actions.player.screen.ScreenClosedAction;
import net.pl3x.guithium.api.action.actions.player.screen.element.ElementClickedAction;
import net.pl3x.guithium.api.action.actions.player.screen.element.ElementValueChangedAction;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.gui.element.ClickableElement;
import net.pl3x.guithium.api.gui.element.ValueElement;
import net.pl3x.guithium.api.gui.texture.Texture;
import net.pl3x.guithium.api.network.PacketListener;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.ElementChangedValuePacket;
import net.pl3x.guithium.api.network.packet.ElementClickedPacket;
import net.pl3x.guithium.api.network.packet.ElementPacket;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import net.pl3x.guithium.api.network.packet.TexturesPacket;
import net.pl3x.guithium.plugin.player.PaperPlayer;
import org.jetbrains.annotations.NotNull;

public class PaperPacketListener implements PacketListener {
    private final PaperPlayer player;

    public PaperPacketListener(@NotNull PaperPlayer player) {
        this.player = player;
    }

    @Override
    public <T extends ClickableElement<T>> void handleElementClick(@NotNull ElementClickedPacket<T> packet) {
        // make sure it's the same screen
        Screen screen = this.player.getCurrentScreen();
        if (screen == null || !screen.getKey().equals(packet.getScreen())) {
            return;
        }

        // make sure the screen has the element
        T element = Unsafe.cast(screen.getElement(packet.getElement()));
        if (element == null) {
            return;
        }

        // inform other plugins the element was clicked
        ElementClickedAction<T> action = new ElementClickedAction<>(this.player, screen, element);
        Guithium.api().getActionRegistry().callAction(action);

        // some other plugin says to ignore it
        if (action.isCancelled()) {
            return;
        }

        // trigger the element's onClick action for other plugins
        ClickableElement.OnClick<T> onClick = element.onClick();
        if (onClick != null) {
            onClick.accept(screen, element, this.player);
        }
    }

    @Override
    public void handleCloseScreen(@NotNull CloseScreenPacket packet) {
        Screen screen = this.player.getCurrentScreen();
        if (screen == null || !screen.getKey().equals(packet.getScreenKey())) {
            return;
        }
        ScreenClosedAction action = new ScreenClosedAction(this.player, screen);
        Guithium.api().getActionRegistry().callAction(action);
        this.player.setCurrentScreen(null);
    }

    @Override
    public void handleElement(@NotNull ElementPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported on server.");
    }

    @Override
    public void handleHello(@NotNull HelloPacket packet) {
        int protocol = packet.getProtocol();

        Guithium.logger.info("{} is using Guithium with protocol {}", this.player.getName(), protocol);

        // set the player's client protocol
        this.player.setProtocol(protocol);

        // reply to the player with server's protocol
        this.player.getConnection().send(new HelloPacket(), true);

        // ensure the player has the correct guithium installed
        if (!this.player.hasGuithium()) {
            return;
        }

        // tell client about textures
        Collection<Texture> textures = Guithium.api().getTextureManager().getTextures();
        if (!textures.isEmpty()) {
            this.player.getConnection().send(new TexturesPacket(textures));
        }

        // tell other plugins about this hello
        PlayerJoinedAction action = new PlayerJoinedAction(this.player);
        Guithium.api().getActionRegistry().callAction(action);
    }

    @Override
    public void handleOpenScreen(@NotNull OpenScreenPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported on server.");
    }

    @Override
    public <T extends ValueElement<T, V>, V> void handleElementChangedValue(@NotNull ElementChangedValuePacket<V> packet) {
        // make sure it's the same screen
        Screen screen = this.player.getCurrentScreen();
        if (screen == null || !screen.getKey().equals(packet.getScreen())) {
            return;
        }

        // make sure the screen has the element
        T element = Unsafe.cast(screen.getElement(packet.getElement()));
        if (element == null) {
            return;
        }

        // inform other plugins the element's value was changed
        ElementValueChangedAction<T, V> action = new ElementValueChangedAction<>(this.player, screen, element, packet.getValue());
        Guithium.api().getActionRegistry().callAction(action);

        // some other plugin says to ignore it
        if (action.isCancelled()) {
            return;
        }

        // update the value in our copy of the element
        element.setValue(action.getValue());

        // if the action changed the value, tell the client
        if (packet.getValue() != action.getValue()) {
            element.send(this.player);
        }

        // trigger the element's onChange action for other plugins
        ValueElement.OnChange<T, V> onChange = element.onChange();
        if (onChange != null) {
            onChange.accept(screen, element, this.player, action.getValue());
        }
    }

    @Override
    public void handleTextures(@NotNull TexturesPacket packet) {
        // client does not send this packet to the server
        throw new UnsupportedOperationException("Not supported on server.");
    }
}
