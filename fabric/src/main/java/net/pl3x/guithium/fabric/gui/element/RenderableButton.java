package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.FormattedCharSequence;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.net.packet.ButtonClickPacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RenderableButton extends RenderableElement {
    public RenderableButton(Button button, RenderableScreen screen) {
        super(button, screen);
    }

    @Override
    @NotNull
    public Button getElement() {
        return (Button) super.getElement();
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        Point size = getElement().getSize();
        if (size == null) {
            return;
        }

        calcScreenPos(size.getX(), size.getY());

        MutableComponent component = null;
        if (getElement().getTooltip() != null) {
            String json = GsonComponentSerializer.gson().serialize(getElement().getTooltip());
            try {
                component = Component.Serializer.fromJson(json);
            } catch (Throwable t) {
                component = Component.translatable(json);
            }
        }
        final List<FormattedCharSequence> tooltip = component == null ? null : Minecraft.getInstance().font.split(component, 200);

        this.renderableWidget = new net.minecraft.client.gui.components.Button(
            (int) this.pos.getX(),
            (int) this.pos.getY(),
            (int) size.getX(),
            (int) size.getY(),
            Component.translatable(getElement().getText()),
            (button) -> {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                ButtonClickPacket packet = new ButtonClickPacket(this.screen.getScreen(), getElement());
                Guithium.instance().getNetworkHandler().getConnection().send(packet);
            },
            (button, poseStack, x, y) -> {
                if (tooltip != null) {
                    this.screen.renderTooltip(poseStack, tooltip, x, y);
                }
            }
        );
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        if (this.renderableWidget != null) {
            this.renderableWidget.render(poseStack, mouseX, mouseY, delta);
        }
    }
}
