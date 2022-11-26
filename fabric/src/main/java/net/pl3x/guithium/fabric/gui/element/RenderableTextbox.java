package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
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
        Textbox textbox = getElement();

        Vec2 size = textbox.getSize();
        if (size == null) {
            size = Vec2.of(120, 20);
        }

        size = Vec2.of(size.getX() - 2, size.getY() - 2);

        calcScreenPos(
            size.getX(),
            size.getY()
        );

        this.cX = (int) (this.pos.getX() + size.getX() / 2);
        this.cY = (int) (this.pos.getY() + size.getY() / 2);

        EditBox editbox = new EditBox(
            minecraft.font,
            (int) this.pos.getX() + 1,
            (int) this.pos.getY() + 1,
            (int) size.getX(),
            (int) size.getY(),
            Component.translatable(getElement().getSuggestion())
        ) {
            @Override
            public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
                if (!this.visible) {
                    return;
                }
                poseStack.pushPose();

                rotate(poseStack, this.x, this.y, this.width, this.height, getElement().getRotation());
                scale(poseStack, this.x, this.y, this.width, this.height, getElement().getScale());
                this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                renderButton(poseStack, mouseX, mouseY, delta);

                poseStack.popPose();
            }
        };

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
