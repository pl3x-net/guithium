package net.pl3x.guithium.fabric.gui.screen;

import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.fabric.GuithiumMod;
import org.jetbrains.annotations.NotNull;

public class RenderableScreen extends AbstractScreen {
    public RenderableScreen(@NotNull GuithiumMod mod, @NotNull Screen screen) {
        super(mod, screen);
    }

    @Override
    public void onClose() {
        CloseScreenPacket packet = new CloseScreenPacket(this.screen.getKey());
        this.mod.getNetworkHandler().getConnection().send(packet);
        super.onClose();
    }
}
