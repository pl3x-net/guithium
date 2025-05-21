package net.pl3x.guithium.fabric.gui.element;

import java.text.DecimalFormat;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.pl3x.guithium.api.gui.element.Slider;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RenderableSlider extends AbstractSliderButton implements RenderableWidget, Tickable {
    private final Slider slider;

    private DecimalFormat decimalFormat;
    private double min;
    private double max;

    public RenderableSlider(@NotNull Slider slider) {
        super(
                slider.getPos().getX(),
                slider.getPos().getY(),
                slider.getSize().getX(),
                slider.getSize().getY(),
                Component.empty(),
                0.0D
        );
        this.slider = slider;
        this.min = dbl(slider.getMin(), 0.0D);
        this.max = dbl(slider.getMax(), 1.0D);
        this.value = invLerp(dbl(slider.getValue(), 0.0D), this.min, this.max);
    }

    private static double dbl(@Nullable Double val, double def) {
        return val == null ? def : val;
    }

    private static double invLerp(double val, double min, double max) {
        return (Mth.clamp(val, min, max) - min) / (max - min);
    }

    @NotNull
    public Slider getElement() {
        return this.slider;
    }

    @Override
    public void init(@NotNull Minecraft client) {
        if (getElement().getDecimalFormat() != null) {
            this.decimalFormat = new DecimalFormat(getElement().getDecimalFormat());
        } else {
            this.decimalFormat = new DecimalFormat("0.0");
        }

        this.value = dbl(getElement().getValue(), 0.0D);
        if (this.decimalFormat != null) {
            this.value = Double.parseDouble(this.decimalFormat.format(this.value));
        }
        this.value = invLerp(this.value, this.min, this.max);

        // update contents
        this.min = dbl(slider.getMin(), 0.0D);
        this.max = dbl(slider.getMax(), 1.0D);
        this.value = invLerp(dbl(slider.getValue(), 0.0D), this.min, this.max);
        updateMessage();

        // update pos/size
        setX(getElement().getPos().getX());
        setY(getElement().getPos().getY());
        this.width = getElement().getSize().getX();
        this.height = getElement().getSize().getY();

        // recalculate position on screen
        RenderableDuck self = (RenderableDuck) this;
        self.calcScreenPos(this.width, this.height);
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        RenderableDuck self = (RenderableDuck) this;
        self.rotate(gfx, self.getCenterX(), self.getCenterY(), getElement().getRotation());
        self.scale(gfx, self.getCenterX(), self.getCenterY(), getElement().getScale());
        super.renderWidget(gfx, mouseX, mouseY, delta);
    }

    @Override
    protected void updateMessage() {
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(this.slider.getTooltip())));
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
        updateMessage();
    }

    @Override
    public void tick() {
        //
    }
}
