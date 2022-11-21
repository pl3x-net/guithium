package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.Point;
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
        Textbox textbox = getElement();

        Point size = textbox.getSize();
        if (size == null) {
            size = Point.of(120, 20);
        }

        size = Point.of(size.getX() - 2, size.getY() - 2);

        calcScreenPos(
            size.getX(),
            size.getY()
        );

        EditBox editbox = new EditBox(
            minecraft.font,
            (int) this.pos.getX() + 1,
            (int) this.pos.getY() + 1,
            (int) size.getX(),
            (int) size.getY(),
            Component.translatable(getElement().getSuggestion())
        );

        setWidget(editbox);

        editbox.setValue(textbox.getValue() == null ? "" : textbox.getValue());
        editbox.setSuggestion(textbox.getSuggestion());
        editbox.setBordered(textbox.isBordered() == null || Boolean.TRUE.equals(textbox.isBordered()));
        editbox.setCanLoseFocus(textbox.canLoseFocus() == null || Boolean.TRUE.equals(textbox.canLoseFocus()));
        editbox.setEditable(textbox.isEditable() == null || Boolean.TRUE.equals(textbox.isEditable()));
        if (textbox.getMaxLength() != null) {
            editbox.setMaxLength(textbox.getMaxLength());
        }
        if (textbox.getTextColor() != null) {
            editbox.setTextColor(textbox.getTextColor());
        }
        if (textbox.getTextColorUneditable() != null) {
            editbox.setTextColorUneditable(textbox.getTextColorUneditable());
        }
    }
}
