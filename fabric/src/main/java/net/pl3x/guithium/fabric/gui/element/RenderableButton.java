package net.pl3x.guithium.fabric.gui.element;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.jetbrains.annotations.NotNull;

public class RenderableButton extends net.minecraft.client.gui.components.Button implements RenderableWidget, Tickable {
    private final Button button;

    public RenderableButton(@NotNull Button button) {
        super(
                button.getPos().getX(),
                button.getPos().getY(),
                button.getSize().getX(),
                button.getSize().getY(),
                ComponentHelper.toVanilla(button.getLabel()),
                null,
                Supplier::get
        );
        this.button = button;
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(button.getTooltip())));
    }

    @NotNull
    public Button getElement() {
        return this.button;
    }

    @Override
    public void init(@NotNull Minecraft client) {
        // update contents
        setMessage(ComponentHelper.toVanilla(this.button.getLabel()));
        setTooltip(Tooltip.create(ComponentHelper.toVanilla(this.button.getTooltip())));

        // update pos/size
        setX(this.button.getPos().getX());
        setY(this.button.getPos().getY());
        this.width = this.button.getSize().getX();
        this.height = this.button.getSize().getY();

        // recalculate position on screen
        RenderableDuck self = (RenderableDuck) this;
        self.calcScreenPos(this.width, this.height);
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        RenderableDuck self = (RenderableDuck) this;
        self.rotate(gfx, self.getCenterX(), self.getCenterY(), this.button.getRotation());
        self.scale(gfx, self.getCenterX(), self.getCenterY(), this.button.getScale());
        super.renderWidget(gfx, mouseX, mouseY, delta);
    }

    @Override
    public void tick() {
        //
    }
}
