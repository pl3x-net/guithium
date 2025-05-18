package net.pl3x.guithium.fabric.network;

import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.network.PacketListener;
import net.pl3x.guithium.api.network.packet.ButtonClickPacket;
import net.pl3x.guithium.api.network.packet.CheckboxTogglePacket;
import net.pl3x.guithium.api.network.packet.CloseScreenPacket;
import net.pl3x.guithium.api.network.packet.ElementPacket;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import net.pl3x.guithium.api.network.packet.OpenScreenPacket;
import net.pl3x.guithium.api.network.packet.RadioTogglePacket;
import net.pl3x.guithium.api.network.packet.SliderChangePacket;
import net.pl3x.guithium.api.network.packet.TexturesPacket;
import org.jetbrains.annotations.NotNull;

public class FabricPacketListener implements PacketListener {
    @Override
    public void handleButtonClick(@NotNull ButtonClickPacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported on client.");
    }

    @Override
    public void handleCheckboxToggle(@NotNull CheckboxTogglePacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported on client.");
    }

    @Override
    public void handleCloseScreen(@NotNull CloseScreenPacket packet) {
        // try to remove from HUD
    }

    @Override
    public void handleElement(@NotNull ElementPacket packet) {
        // Element element = packet.getElement();
    }

    @Override
    public void handleHello(@NotNull HelloPacket packet) {
        int protocol = packet.getProtocol();

        if (protocol == Guithium.PROTOCOL) {
            Guithium.logger.info("Server responded with correct protocol ({})", protocol);
        } else {
            Guithium.logger.info("Server responded with a different protocol ({})", protocol);
            /*Minecraft.getInstance().getToasts().addToast(
                new SystemToast(
                    SystemToast.SystemToastIds.TUTORIAL_HINT,
                    Component.literal("Guithium Error"),
                    Component.literal("Protocol mismatch. Disabling mod.")
                )
            );*/
        }
    }

    @Override
    public void handleOpenScreen(@NotNull OpenScreenPacket packet) {
        //Screen screen = packet.getScreen();
    }

    @Override
    public void handleRadioToggle(@NotNull RadioTogglePacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported on client.");
    }

    @Override
    public void handleSliderChange(@NotNull SliderChangePacket packet) {
        // server does not send this packet to the client
        throw new UnsupportedOperationException("Not supported on client.");
    }

    @Override
    public void handleTextures(@NotNull TexturesPacket packet) {
        packet.getTextures().forEach((key, texture) -> {
            // add texture to manager
            //Guithium.instance().getTextureManager().add(key, texture.getUrl());
        });
    }
}
