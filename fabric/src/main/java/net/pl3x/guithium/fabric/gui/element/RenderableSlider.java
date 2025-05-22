package net.pl3x.guithium.fabric.gui.element;

import java.text.DecimalFormat;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.element.Slider;
import net.pl3x.guithium.api.network.packet.SliderChangePacket;
import net.pl3x.guithium.fabric.GuithiumMod;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import net.pl3x.guithium.fabric.util.Numbers;
import org.jetbrains.annotations.NotNull;

public class RenderableSlider extends AbstractSliderButton implements RenderableWidget {
    private final RenderableDuck self;
    private final Slider slider;

    private DecimalFormat decimalFormat;
    private double min;
    private double max;

    public RenderableSlider(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Slider slider) {
        super(0, 0, 0, 0, Component.empty(), 0);
        this.self = ((RenderableDuck) this).duck(client, screen);
        this.slider = slider;
    }

    @NotNull
    public Slider getElement() {
        return this.slider;
    }

    @Override
    public void init() {
        if (getElement().getDecimalFormat() != null) {
            this.decimalFormat = new DecimalFormat(getElement().getDecimalFormat());
        } else {
            this.decimalFormat = new DecimalFormat("0.0");
        }

        this.value = Numbers.unbox(getElement().getValue(), 0.0D);
        if (this.decimalFormat != null) {
            this.value = Double.parseDouble(this.decimalFormat.format(this.value));
        }
        this.value = Numbers.invLerp(this.value, this.min, this.max);

        // update contents
        this.min = Numbers.unbox(getElement().getMin(), 0.0D);
        this.max = Numbers.unbox(getElement().getMax(), 1.0D);
        this.value = Numbers.invLerp(Numbers.unbox(getElement().getValue(), 0.0D), this.min, this.max);
        updateMessage();

        // update pos/size
        setX((int) getElement().getPos().getX());
        setY((int) getElement().getPos().getY());
        setWidth((int) getElement().getSize().getX());
        setHeight((int) getElement().getSize().getY());

        // recalculate position on screen
        this.self.calcScreenPos(getWidth(), getHeight());
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        this.self.rotate(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getRotation());
        this.self.scale(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getScale());
        super.renderWidget(gfx, mouseX, mouseY, delta);
    }

    @Override
    protected void updateMessage() {
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(getElement().getTooltip())));
        net.kyori.adventure.text.Component label = getElement().getLabel();
        setMessage(label == null ? Component.empty() : ComponentHelper.toVanilla(
                GsonComponentSerializer.gson().serialize(label)
                        .replace("{val}", this.decimalFormat.format(this.value))
                        .replace("{min}", this.decimalFormat.format(this.min))
                        .replace("{max}", this.decimalFormat.format(this.max))
        ));
    }

    @Override
    protected void applyValue() {
        double diff = this.max - this.min;
        double value = (diff * this.value) + this.min;

        if (this.decimalFormat != null) {
            value = Double.parseDouble(this.decimalFormat.format(value));
        }

        if (value == Numbers.unbox(getElement().getValue(), 0.0D)) {
            return;
        }

        getElement().setValue(value);
        ((GuithiumMod) Guithium.api()).getNetworkHandler().getConnection().send(
                new SliderChangePacket(
                        this.self.getScreen().getScreen().getKey(),
                        getElement().getKey(),
                        value
                )
        );
    }
}
