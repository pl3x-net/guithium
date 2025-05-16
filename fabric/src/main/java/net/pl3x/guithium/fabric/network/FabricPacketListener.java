package net.pl3x.guithium.fabric.network;

import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.network.PacketListener;
import net.pl3x.guithium.api.network.packet.HelloPacket;
import org.jetbrains.annotations.NotNull;

public class FabricPacketListener implements PacketListener {
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
}
