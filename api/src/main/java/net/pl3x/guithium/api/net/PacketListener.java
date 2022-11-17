package net.pl3x.guithium.api.net;

import net.pl3x.guithium.api.net.packet.ButtonClickPacket;
import net.pl3x.guithium.api.net.packet.CloseScreenPacket;
import net.pl3x.guithium.api.net.packet.ElementPacket;
import net.pl3x.guithium.api.net.packet.OpenScreenPacket;
import net.pl3x.guithium.api.net.packet.TexturesPacket;
import net.pl3x.guithium.api.net.packet.HelloPacket;

public interface PacketListener {
    void handleButtonClick(ButtonClickPacket packet);

    void handleCloseScreen(CloseScreenPacket packet);

    void handleElement(ElementPacket packet);

    void handleHello(HelloPacket packet);

    void handleOpenScreen(OpenScreenPacket packet);

    void handleTextures(TexturesPacket packet);
}
