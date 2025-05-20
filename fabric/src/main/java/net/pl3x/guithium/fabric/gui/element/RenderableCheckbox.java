package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.pl3x.guithium.api.gui.element.Checkbox;
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

    @NotNull
    public Checkbox getElement() {
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
        super.renderWidget(gfx, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        //
    }
}
