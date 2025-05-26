package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.network.packet.ElementChangedValuePacket;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

public class RenderableCheckbox extends net.minecraft.client.gui.components.Checkbox implements TextureSwappableWidget, RenderableWidget {
    private final RenderableDuck self;

    private Checkbox checkbox;

    public RenderableCheckbox(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Checkbox checkbox) {
        super(
                0, 0, (int) checkbox.getSize().getX(),
                ComponentHelper.toVanilla(checkbox.getLabel()),
                client.font, false, null
        );
        this.self = ((RenderableDuck) this).duck(client, screen);
        this.checkbox = checkbox;
    }

    @NotNull
    public Checkbox getElement() {
        return this.checkbox;
    }

    @Override
    public void updateElement(@NotNull Element element) {
        this.checkbox = (Checkbox) element;
        this.self.getScreen().refresh();
    }

    @Override
    @NotNull
    public MultiLineTextWidget getTextWidget() {
        return this.textWidget;
    }

    @Override
    @NotNull
    public ResourceLocation getTexture() {
        if (selected()) {
            return isHoveredOrFocused() ? CHECKBOX_SELECTED_HIGHLIGHTED_SPRITE : CHECKBOX_SELECTED_SPRITE;
        } else {
            return isHoveredOrFocused() ? CHECKBOX_HIGHLIGHTED_SPRITE : CHECKBOX_SPRITE;
        }
    }

    @Override
    public float getAlpha() {
        return this.alpha;
    }

    @Override
    public void init() {
        // update contents
        setMessage(ComponentHelper.toVanilla(getElement().getLabel()));
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(getElement().getTooltip())));
        this.selected = BooleanUtils.isTrue(getElement().getValue());

        // update pos/size
        setX((int) getElement().getPos().getX());
        setY((int) getElement().getPos().getY());
        setWidth(getAdjustedWidth((int) getElement().getSize().getX(), getMessage(), this.self.getClient().font));
        setHeight(getAdjustedHeight(this.self.getClient().font));

        // recalculate position on screen
        this.self.calcScreenPos(getWidth(), getHeight());
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        TextureSwappableWidget.super.renderWidget(gfx, mouseX, mouseY, delta);
    }

    @Override
    public void onPress() {
        // toggle it
        this.selected = !selected();

        // make sure the value is actually changed
        if (getElement().getValue() == selected()) {
            return;
        }

        // toggle this checkbox
        getElement().setValue(selected());

        // tell the server
        conn().send(new ElementChangedValuePacket<>(
                this.self.getScreen().getScreen(),
                getElement(),
                selected()
        ));
    }
}
