package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.network.Connection;
import net.pl3x.guithium.api.network.packet.CheckboxTogglePacket;
import net.pl3x.guithium.fabric.GuithiumMod;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

public class RenderableCheckbox extends net.minecraft.client.gui.components.Checkbox implements TextureSwappableWidget, RenderableWidget {
    private final Checkbox checkbox;

    public RenderableCheckbox(@NotNull Checkbox checkbox) {
        super(
                checkbox.getPos().getX(),
                checkbox.getPos().getY(),
                checkbox.getSize().getX(), // max width
                ComponentHelper.toVanilla(checkbox.getLabel()),
                Minecraft.getInstance().font,
                BooleanUtils.isTrue(checkbox.isSelected()),
                (chk, bl) -> {
                }
        );
        this.checkbox = checkbox;
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(checkbox.getTooltip())));
    }

    protected RenderableCheckbox(int i, int j, int k, @NotNull Component component, @NotNull Font font, boolean bl, @NotNull OnValueChange onValueChange) {
        super(i, j, k, component, font, bl, onValueChange);
        this.checkbox = null;
    }

    @NotNull
    public Checkbox getElement() {
        return this.checkbox;
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
    public void init(@NotNull Minecraft client) {
        // update contents
        setMessage(ComponentHelper.toVanilla(this.checkbox.getLabel()));
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(this.checkbox.getTooltip())));

        // update pos/size
        setX(getElement().getPos().getX());
        setY(getElement().getPos().getY());
        setWidth(getAdjustedWidth(getElement().getSize().getX(), this.message, client.font));
        setHeight(getAdjustedHeight(client.font));

        // recalculate position on screen
        RenderableDuck self = (RenderableDuck) this;
        self.calcScreenPos(getWidth(), getHeight());
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        TextureSwappableWidget.super.renderWidget(gfx, mouseX, mouseY, delta);
    }

    @Override
    public void onPress() {
        super.onPress();

        // make sure we're still on our screen
        AbstractScreen screen = (AbstractScreen) Minecraft.getInstance().screen;
        if (screen == null) {
            return;
        }

        // make sure the value is actually changed
        if (Boolean.TRUE.equals(getElement().isSelected()) == selected()) {
            return;
        }

        // toggle this checkbox
        getElement().setSelected(selected());

        // tell the server
        Connection conn = ((GuithiumMod) Guithium.api()).getNetworkHandler().getConnection();
        conn.send(new CheckboxTogglePacket(screen.getKey(), getElement().getKey(), selected()));
    }
}
