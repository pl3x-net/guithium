package net.pl3x.guithium.fabric.gui.element;

import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.jetbrains.annotations.NotNull;

public class RenderableButton extends net.minecraft.client.gui.components.Button implements RenderableWidget {
    private final Button button;

    public RenderableButton(@NotNull Button button) {
        super(
                button.getPos().getX(),
                button.getPos().getY(),
                button.getSize().getX(),
                button.getSize().getY(),
                ComponentHelper.toVanilla(button.getLabel()),
                btn -> {
                    Guithium.logger.warn("CLICK! ({})", button.getKey());
                },
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
        setX(getElement().getPos().getX());
        setY(getElement().getPos().getY());
        setWidth(getElement().getSize().getX());
        setHeight(getElement().getSize().getY());

        // recalculate position on screen
        RenderableDuck self = (RenderableDuck) this;
        self.calcScreenPos(getWidth(), getHeight());
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        this.testRotVar = (this.testRotVar + 10 * delta) % 360;

        RenderableDuck self = (RenderableDuck) this;
        self.rotate(gfx, self.getCenterX(), self.getCenterY(), /*getElement().getRotation()*/ this.testRotVar);
        self.scale(gfx, self.getCenterX(), self.getCenterY(), getElement().getScale());

        super.renderWidget(gfx, mouseX, mouseY, delta);
    }

    private float testRotVar = 0;
}
