package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

public class RenderableText extends AbstractWidget implements RenderableWidget {
    private final Minecraft client;
    private final AbstractScreen screen;
    private final Text text;

    public RenderableText(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Text text) {
        super(0, 0, 0, 0, Component.empty());
        this.client = client;
        this.screen = screen;
        this.text = text;
        this.active = false;
    }

    @Override
    @NotNull
    public Text getElement() {
        return this.text;
    }

    @Override
    public void init() {
        if (getElement().getText() == null) {
            return;
        }

        // update contents
        setMessage(ComponentHelper.toVanilla(getElement().getText()));

        // update pos/size
        setX((int) getElement().getPos().getX());
        setY((int) getElement().getPos().getY());
        setWidth(this.client.font.width(getMessage()));
        setHeight(this.client.font.lineHeight);

        // recalculate position on screen
        RenderableDuck self = (RenderableDuck) this;
        self.calcScreenPos(getWidth(), getHeight());
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        if (getElement().getText() == null) {
            return;
        }

        RenderableDuck self = (RenderableDuck) this;
        self.rotate(gfx, self.getCenterX(), self.getCenterY(), getElement().getRotation());
        self.scale(gfx, self.getCenterX(), self.getCenterY(), getElement().getScale());

        gfx.drawString(
                this.client.font,
                getMessage(),
                getX(),
                getY(),
                0xFFFFFF,
                BooleanUtils.isTrue(getElement().hasShadow())
        );
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        // nothing to narrate
    }
}
