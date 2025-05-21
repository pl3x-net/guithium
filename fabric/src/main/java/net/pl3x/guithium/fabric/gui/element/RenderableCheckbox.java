package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

public class RenderableCheckbox extends net.minecraft.client.gui.components.Checkbox implements RenderableWidget, Tickable {
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
    public Element getElement() {
        return this.checkbox;
    }

    @Override
    public void init(@NotNull Minecraft client) {
        // update contents
        setMessage(ComponentHelper.toVanilla(this.checkbox.getLabel()));
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(this.checkbox.getTooltip())));

        // update pos/size
        setX(this.checkbox.getPos().getX());
        setY(this.checkbox.getPos().getY());
        this.width = this.getAdjustedWidth(this.checkbox.getSize().getX(), this.message, client.font);
        this.height = this.getAdjustedHeight(client.font);

        // recalculate position on screen
        RenderableDuck self = (RenderableDuck) this;
        self.calcScreenPos(this.width, this.height);
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        RenderableDuck self = (RenderableDuck) this;
        self.rotate(gfx, self.getCenterX(), self.getCenterY(), this.checkbox.getRotation());
        self.scale(gfx, self.getCenterX(), self.getCenterY(), this.checkbox.getScale());
        //super.renderWidget(gfx, mouseX, mouseY, delta);

        ResourceLocation resourceLocation;
        if (selected()) {
            resourceLocation = this.isHoveredOrFocused() ? CHECKBOX_SELECTED_HIGHLIGHTED_SPRITE : CHECKBOX_SELECTED_SPRITE;
        } else {
            resourceLocation = this.isHoveredOrFocused() ? CHECKBOX_HIGHLIGHTED_SPRITE : CHECKBOX_SPRITE;
        }

        int size = getBoxSize(Minecraft.getInstance().font);
        gfx.blitSprite(RenderType::guiTextured, resourceLocation, this.getX(), this.getY(), size, size, ARGB.white(this.alpha));
        int textX = this.getX() + size + 4;
        int textY = this.getY() + size / 2 - this.textWidget.getHeight() / 2;
        this.textWidget.setPosition(textX, textY);
        this.textWidget.renderWidget(gfx, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        //
    }
}
