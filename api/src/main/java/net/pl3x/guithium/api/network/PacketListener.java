package net.pl3x.guithium.api.network;

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

public interface PacketListener {
    void handleButtonClick(@NotNull ButtonClickPacket packet);

    void handleCheckboxToggle(@NotNull CheckboxTogglePacket packet);

    void handleCloseScreen(@NotNull CloseScreenPacket packet);

    void handleElement(@NotNull ElementPacket packet);

    void handleHello(@NotNull HelloPacket packet);

    void handleOpenScreen(@NotNull OpenScreenPacket packet);

    void handleRadioToggle(@NotNull RadioTogglePacket packet);

    void handleSliderChange(@NotNull SliderChangePacket packet);

    void handleTextures(@NotNull TexturesPacket packet);
}
