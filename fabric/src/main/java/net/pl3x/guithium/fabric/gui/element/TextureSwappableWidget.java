package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.pl3x.guithium.api.gui.element.Element;
import org.jetbrains.annotations.NotNull;

public interface TextureSwappableWidget {
    int getX();

    int getY();

    float getAlpha();

    @NotNull
    Element getElement();

    @NotNull
    MultiLineTextWidget getTextWidget();

    @NotNull
    ResourceLocation getTexture();

    default void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        int size = Checkbox.getBoxSize(Minecraft.getInstance().font);
        gfx.blitSprite(RenderType::guiTextured, getTexture(), getX(), getY(), size, size, ARGB.white(getAlpha()));
        int textX = this.getX() + size + 4;
        int textY = this.getY() + size / 2 - getTextWidget().getHeight() / 2;
        getTextWidget().setPosition(textX, textY);
        getTextWidget().renderWidget(gfx, mouseX, mouseY, delta);
    }
}
