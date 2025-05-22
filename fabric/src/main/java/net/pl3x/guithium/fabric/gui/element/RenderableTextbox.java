package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.element.Textbox;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.util.ComponentHelper;
import net.pl3x.guithium.fabric.util.Numbers;
import org.apache.commons.lang3.BooleanUtils;
import org.jetbrains.annotations.NotNull;

public class RenderableTextbox extends EditBox implements RenderableWidget {
    private final RenderableDuck self;
    private final Textbox textbox;

    public RenderableTextbox(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Textbox textbox) {
        super(client.font, 0, 0, Component.empty());
        this.self = ((RenderableDuck) this).duck(client, screen);
        this.textbox = textbox;
    }

    @Override
    @NotNull
    public Textbox getElement() {
        return this.textbox;
    }

    @Override
    public void init() {
        setHint(ComponentHelper.toVanilla(getElement().getSuggestion()));
        setValue(getElement().getValue());
        setBordered(!BooleanUtils.isFalse(getElement().isBordered()));
        setCanLoseFocus(!BooleanUtils.isFalse(getElement().canLoseFocus()));
        setEditable(!BooleanUtils.isFalse(getElement().isEditable()));
        setMaxLength(Numbers.unbox(getElement().getMaxLength(), 32));
        setTextColor(Numbers.unbox(getElement().getTextColor(), 0xFFFFFF));
        setTextColorUneditable(Numbers.unbox(getElement().getUneditableTextColor(), 0x707070));

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
    public void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        // nothing to narrate
    }
}
