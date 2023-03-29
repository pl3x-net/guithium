package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.text.DecimalFormat;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Slider;
import net.pl3x.guithium.api.network.packet.SliderChangePacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

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

        this.label = calculateMessage();
        this.tooltip = processTooltip(getElement().getTooltip());

        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.of(30 + minecraft.font.width(this.label), 20);
        }

        calcScreenPos(size.getX(), size.getY());

        getWidget().init(
                this.posX,
                this.posY,
                (int) size.getX(),
                (int) size.getY(),
                this.label,
                value
        );
    }

    public Component calculateMessage() {
        net.kyori.adventure.text.Component label = getElement().getLabel();
        if (label != null) {
            return processComponent(GsonComponentSerializer.gson().serialize(label)
                    .replace("{value}", this.decimalFormat.format(getElement().getValue()))
                    .replace("{min}", this.decimalFormat.format(this.min))
                    .replace("{max}", this.decimalFormat.format(this.max))
            );
        }
        return Component.empty();
    }

    public static class SliderButton extends AbstractSliderButton {
        private final RenderableSlider renderableSlider;

        public SliderButton(RenderableSlider renderableSlider) {
            super(0, 0, 0, 0, Component.empty(), 0);
            this.renderableSlider = renderableSlider;
        }

        public void init(int x, int y, int width, int height, Component label, double value) {
            this.setX(x);
            this.setY(y);
            this.width = width;
            this.height = height;
            this.value = value;
            setMessage(label);
        }

        @Override
        public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
            if (!this.visible) {
                return;
            }
            this.renderableSlider.rotate(poseStack, this.getX(), this.getY(), this.width, this.height, this.renderableSlider.getElement().getRotation());
            this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
            renderWidget(poseStack, mouseX, mouseY, delta);
        }

        @Override
        public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float delta) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
            RenderSystem.setShaderColor(1, 1, 1, 1);

            int yOffset = getTextureY(isActive(), isHoveredOrFocused());
            blitNineSliced(poseStack, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, yOffset);
            blitNineSliced(poseStack, this.getX() + (int) (this.value * (double) (this.width - 8)), this.getY(), 8, 20, 20, 4, 200, 20, 0, yOffset);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            renderScrollingString(poseStack, Minecraft.getInstance().font, 2, (this.active ? 0xFFFFFFFF : 0xFFA0A0A0) | Mth.ceil(this.alpha * 255.0F) << 24);
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
