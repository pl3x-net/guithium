package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Slider;
import net.pl3x.guithium.api.network.packet.SliderChangePacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RenderableSlider extends RenderableWidget {
    public RenderableSlider(@NotNull RenderableScreen screen, @NotNull Slider slider) {
        super(screen, slider);
    }

    @Override
    @NotNull
    public Slider getElement() {
        return (Slider) super.getElement();
    }

    @Override
    @NotNull
    public net.minecraft.client.gui.components.AbstractSliderButton getWidget() {
        return (net.minecraft.client.gui.components.AbstractSliderButton) super.getWidget();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.of(30 + minecraft.font.width(getElement().getLabel()), 20);
        }

        double diff = getElement().getMax() - getElement().getMin();
        double value = (getElement().getValue() - getElement().getMin()) / diff;

        final List<FormattedCharSequence> tooltip = processTooltip(getElement().getTooltip());

        calcScreenPos(size.getX(), size.getY());

        setWidget(new AbstractSliderButton(
            (int) this.pos.getX(),
            (int) this.pos.getY(),
            (int) size.getX(),
            (int) size.getY(),
            calculateMessage(),
            value
        ) {
            @Override
            public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
                if (!this.visible) {
                    return;
                }
                poseStack.pushPose();

                rotate(poseStack, this.x, this.y, this.width, this.height, getElement().getRotation());
                this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                renderButton(poseStack, mouseX, mouseY, delta);

                poseStack.popPose();

                if (tooltip != null && this.isHovered && getTooltipDelay() > 10) {
                    getScreen().renderTooltip(poseStack, tooltip, mouseX, mouseY);
                }
            }

            @Override
            public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float delta) {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
                RenderSystem.setShaderColor(1, 1, 1, 1);

                int yOffset = getYImage(isHoveredOrFocused());
                blit(poseStack, this.x, this.y, 0, 46 + yOffset * 20, this.width / 2, this.height);
                blit(poseStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + yOffset * 20, this.width / 2, this.height);
                renderBg(poseStack, minecraft, mouseX, mouseY);
                drawCenteredString(poseStack, minecraft.font, getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, this.active ? 0xFFFFFFFF : 0xFFA0A0A0);
            }

            @Override
            protected void updateMessage() {
                setMessage(calculateMessage());
            }

            @Override
            protected void applyValue() {
                double diff = getElement().getMax() - getElement().getMin();
                int value = (int) ((diff * this.value) + getElement().getMin());

                if (value == getElement().getValue()) {
                    return;
                }

                getElement().setValue(value);
                Guithium.instance().getNetworkHandler().getConnection()
                    .send(new SliderChangePacket(getScreen().getScreen(), getElement(), value));
            }
        });
    }

    public Component calculateMessage() {
        String label = getElement().getLabel();
        if (label != null) {
            return Component.literal(label
                .replace("{value}", Integer.toString((int) getElement().getValue()))
                .replace("{min}", Integer.toString((int) getElement().getMin()))
                .replace("{max}", Integer.toString((int) getElement().getMax()))
            );
        }
        return Component.empty();
    }
}
