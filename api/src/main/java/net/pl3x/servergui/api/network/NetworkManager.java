package net.pl3x.servergui.api.network;

import net.pl3x.servergui.api.gui.element.Element;

import java.util.UUID;

public interface NetworkManager {
    void send(UUID uuid, Element element);
}
