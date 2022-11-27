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

import java.text.DecimalFormat;
import java.util.List;

public class RenderableSlider extends RenderableWidget {
    private DecimalFormat decimalFormat;
    private double min = 0D;
    private double max = 1D;

    public RenderableSlider(@NotNull RenderableScreen screen, @NotNull Slider slider) {
        super(screen, slider);
        setWidget(new SliderButton(this));
    }

    @Override
    @NotNull
    public Slider getElement() {
        return (Slider) super.getElement();
    }

    @Override
    @NotNull
    public SliderButton getWidget() {
        return (SliderButton) super.getWidget();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.of(30 + minecraft.font.width(getElement().getLabel()), 20);
        }

        if (getElement().getMin() != null) {
            this.min = getElement().getMin();
        }
        if (getElement().getMax() != null) {
            this.max = getElement().getMax();
        }

        if (getElement().getDecimalFormat() != null) {
            this.decimalFormat = new DecimalFormat(getElement().getDecimalFormat());
        }

        double diff = this.max - this.min;
        double value;
        if (this.decimalFormat != null) {
            value = Double.parseDouble(this.decimalFormat.format(getElement().getValue()));
        } else {
            value = getElement().getValue();
        }
        value = (value - this.min) / diff;

        calcScreenPos(size.getX(), size.getY());

        getWidget().init(
            (int) this.pos.getX(),
            (int) this.pos.getY(),
            (int) size.getX(),
            (int) size.getY(),
            calculateMessage(),
            value,
            processTooltip(getElement().getTooltip())
        );
    }

    public Component calculateMessage() {
        String label = getElement().getLabel();
        if (label != null) {
            return Component.literal(label
                .replace("{value}", this.decimalFormat.format(getElement().getValue()))
                .replace("{min}", this.decimalFormat.format(this.min))
                .replace("{max}", this.decimalFormat.format(this.max))
            );
        }
        return Component.empty();
    }

    public static class SliderButton extends AbstractSliderButton {
        private final RenderableSlider renderableSlider;
        private List<FormattedCharSequence> tooltip;

        public SliderButton(RenderableSlider renderableSlider) {
            super(0, 0, 0, 0, Component.empty(), 0);
            this.renderableSlider = renderableSlider;
        }

        public void init(int x, int y, int width, int height, Component label, double value, List<FormattedCharSequence> tooltip) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.value = value;
            setMessage(label);
            this.tooltip = tooltip;
        }

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
            if (!this.visible) {
                return;
            }
            poseStack.pushPose();

            this.renderableSlider.rotate(poseStack, this.x, this.y, this.width, this.height, this.renderableSlider.getElement().getRotation());
            this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            renderButton(poseStack, mouseX, mouseY, delta);

            poseStack.popPose();

            if (this.tooltip != null && this.isHovered && this.renderableSlider.getTooltipDelay() > 10) {
                this.renderableSlider.getScreen().renderTooltip(poseStack, this.tooltip, mouseX, mouseY);
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
            renderBg(poseStack, Minecraft.getInstance(), mouseX, mouseY);
            drawCenteredString(poseStack, Minecraft.getInstance().font, getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, this.active ? 0xFFFFFFFF : 0xFFA0A0A0);
        }

        @Override
        protected void updateMessage() {
            setMessage(this.renderableSlider.calculateMessage());
        }

        @Override
        protected void applyValue() {
            Slider element = this.renderableSlider.getElement();

            double diff = this.renderableSlider.max - this.renderableSlider.min;
            double value = (diff * this.value) + this.renderableSlider.min;

            if (this.renderableSlider.decimalFormat != null) {
                value = Double.parseDouble(this.renderableSlider.decimalFormat.format(value));
            }

            if (value == element.getValue()) {
                return;
            }

            element.setValue(value);
            Guithium.instance().getNetworkHandler().getConnection()
                .send(new SliderChangePacket(this.renderableSlider.getScreen().getScreen(), element, value));
        }
    }
}
