package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Textbox;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableTextbox extends RenderableWidget {
    public RenderableTextbox(@NotNull RenderableScreen screen, @NotNull Textbox textbox) {
        super(screen, textbox);
    }

    @Override
    @NotNull
    public Textbox getElement() {
        return (Textbox) super.getElement();
    }

    @Override
    @NotNull
    public EditBox getWidget() {
        return (EditBox) super.getWidget();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.of(120, 20);
        }

        size = Vec2.of(size.getX() - 2, size.getY() - 2);

        calcScreenPos(
                size.getX(),
                size.getY()
        );

        this.centerX = (int) (this.posX + size.getX() / 2);
        this.centerX = (int) (this.posY + size.getY() / 2);

        EditBox editbox = new EditBox(
                minecraft.font,
                this.posX + 1,
                this.posY + 1,
                (int) size.getX(),
                (int) size.getY(),
                Component.translatable(getElement().getSuggestion())
        ) {
            @Override
            public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
                if (!this.visible) {
                    return;
                }
                rotate(guiGraphics, this.getX(), this.getY(), this.width, this.height, getElement().getRotation());
                scale(guiGraphics, this.getX(), this.getY(), this.width, this.height, getElement().getScale());
                this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
                renderWidget(guiGraphics, mouseX, mouseY, delta);
            }
            @Override
            public void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
                // do nothing
            }
        };

        setWidget(editbox);

        editbox.setValue(getElement().getValue() == null ? "" : getElement().getValue());
        editbox.setSuggestion(getElement().getSuggestion());
        editbox.setBordered(getElement().isBordered() == null || Boolean.TRUE.equals(getElement().isBordered()));
        editbox.setCanLoseFocus(getElement().canLoseFocus() == null || Boolean.TRUE.equals(getElement().canLoseFocus()));
        editbox.setEditable(getElement().isEditable() == null || Boolean.TRUE.equals(getElement().isEditable()));
        if (getElement().getMaxLength() != null) {
            editbox.setMaxLength(getElement().getMaxLength());
        }
        if (getElement().getTextColor() != null) {
            editbox.setTextColor(getElement().getTextColor());
        }
        if (getElement().getTextColorUneditable() != null) {
            editbox.setTextColorUneditable(getElement().getTextColorUneditable());
        }
    }
}
