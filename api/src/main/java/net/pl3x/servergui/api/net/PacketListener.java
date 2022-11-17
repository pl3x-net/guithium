package net.pl3x.servergui.api.net;

import net.pl3x.servergui.api.net.packet.ButtonClickPacket;
import net.pl3x.servergui.api.net.packet.CloseScreenPacket;
import net.pl3x.servergui.api.net.packet.ElementPacket;
import net.pl3x.servergui.api.net.packet.HelloPacket;
import net.pl3x.servergui.api.net.packet.OpenScreenPacket;
import net.pl3x.servergui.api.net.packet.TexturesPacket;

public interface PacketListener {
    void handleButtonClick(ButtonClickPacket packet);

    void handleCloseScreen(CloseScreenPacket packet);

    void handleElement(ElementPacket packet);

    void handleHello(HelloPacket packet);

    void handleOpenScreen(OpenScreenPacket packet);

    void handleTextures(TexturesPacket packet);
}
