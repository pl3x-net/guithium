package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.net.packet.ButtonClickPacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RenderableButton extends RenderableWidget {
    public RenderableButton(@NotNull RenderableScreen screen, @NotNull Button button) {
        super(screen, button);
    }

    @Override
    @NotNull
    public Button getElement() {
        return (Button) super.getElement();
    }

    @Override
    @NotNull
    public net.minecraft.client.gui.components.Button getWidget() {
        return (net.minecraft.client.gui.components.Button) super.getWidget();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        Point size = getElement().getSize();
        if (size == null) {
            size = Point.of(30 + minecraft.font.width(getElement().getText()), 20);
        }

        calcScreenPos(size.getX(), size.getY());

        final List<FormattedCharSequence> tooltip = processTooltip(getElement().getTooltip());

        setWidget(new net.minecraft.client.gui.components.Button(
            (int) this.pos.getX(),
            (int) this.pos.getY(),
            (int) size.getX(),
            (int) size.getY(),
            Component.translatable(getElement().getText()),
            (button) -> {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                ButtonClickPacket packet = new ButtonClickPacket(getScreen().getScreen(), getElement());
                Guithium.instance().getNetworkHandler().getConnection().send(packet);
            },
            (button, poseStack, mouseX, mouseY) -> {
                if (tooltip != null && button.isHovered && getTooltipDelay() > 10) {
                    getScreen().renderTooltip(poseStack, tooltip, mouseX, mouseY);
                }
            }
        ));
    }
}
