package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

public class RenderableText extends AbstractWidget implements RenderableWidget {
    private final RenderableDuck self;

    private Text text;

    public RenderableText(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Text text) {
        super(0, 0, 0, 0, Component.empty());
        this.self = ((RenderableDuck) this).duck(client, screen);
        this.text = text;
        this.active = false;
    }

    @Override
    @NotNull
    public Text getElement() {
        return this.text;
    }

    @Override
    public void updateElement(@NotNull Element element) {
        this.text = (Text) element;
        this.self.getScreen().refresh();
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
        setWidth(this.self.getClient().font.width(getMessage()));
        setHeight(this.self.getClient().font.lineHeight);

        // recalculate position on screen
        this.self.calcScreenPos(getWidth(), getHeight());
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        if (getElement().getText() == null) {
            return;
        }

        this.self.rotate(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getRotation());
        this.self.scale(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getScale());

        gfx.drawString(
                this.self.getClient().font,
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
