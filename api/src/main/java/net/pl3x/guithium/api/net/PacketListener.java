package net.pl3x.guithium.api.net;

import net.pl3x.guithium.api.net.packet.ButtonClickPacket;
import net.pl3x.guithium.api.net.packet.CloseScreenPacket;
import net.pl3x.guithium.api.net.packet.ElementPacket;
import net.pl3x.guithium.api.net.packet.HelloPacket;
import net.pl3x.guithium.api.net.packet.OpenScreenPacket;
import net.pl3x.guithium.api.net.packet.TexturesPacket;
import org.jetbrains.annotations.NotNull;

public interface PacketListener {
    void handleButtonClick(@NotNull ButtonClickPacket packet);

    void handleCloseScreen(@NotNull CloseScreenPacket packet);

    void handleElement(@NotNull ElementPacket packet);

    void handleHello(@NotNull HelloPacket packet);

    void handleOpenScreen(@NotNull OpenScreenPacket packet);

    void handleTextures(@NotNull TexturesPacket packet);
}
