package net.pl3x.guithium.fabric.gui.element;

import java.text.DecimalFormat;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Slider;
import net.pl3x.guithium.api.network.packet.ElementChangedValuePacket;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import net.pl3x.guithium.fabric.util.Numbers;
import org.jetbrains.annotations.NotNull;

public class RenderableSlider extends AbstractSliderButton implements RenderableWidget {
    private final RenderableDuck self;

    private Slider slider;

    private DecimalFormat decimalFormat;
    private double lerpedValue;
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
    public void updateElement(@NotNull Element element) {
        this.slider = (Slider) element;
        this.self.getScreen().refresh();
    }

    @Override
    public void init() {
        if (getElement().getDecimalFormat() != null) {
            this.decimalFormat = new DecimalFormat(getElement().getDecimalFormat());
        } else {
            this.decimalFormat = new DecimalFormat("0.0");
        }

        // update contents
        this.min = Numbers.unbox(getElement().getMin(), 0.0D);
        this.max = Numbers.unbox(getElement().getMax(), 1.0D);
        double value = Numbers.unbox(getElement().getValue(), 0.0D);

        // let slider handle clamping and formatting
        setValue(Mth.inverseLerp(value, this.min, this.max));

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
                        .replace("{value}", this.decimalFormat.format(this.lerpedValue))
                        .replace("{min}", this.decimalFormat.format(this.min))
                        .replace("{max}", this.decimalFormat.format(this.max))
        ));
    }

    @Override
    protected void applyValue() {
        this.lerpedValue = Mth.lerp(this.value, this.min, this.max);

        if (this.decimalFormat != null) {
            // cut precision down
            this.lerpedValue = Double.parseDouble(this.decimalFormat.format(this.lerpedValue));
        }

        if (this.lerpedValue == Numbers.unbox(getElement().getValue(), 0.0D)) {
            // value has not changed
            return;
        }

        getElement().setValue(this.lerpedValue);
        conn().send(new ElementChangedValuePacket<>(
                this.self.getScreen().getScreen(),
                getElement(),
                this.lerpedValue
        ));
    }
}
