package net.pl3x.servergui.fabric.gui.element;

import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.api.gui.element.Element;
import org.jetbrains.annotations.NotNull;

public interface RenderableElement extends Element {
    default void setupScaleAndZIndex(@NotNull MatrixStack matrix) {
        if (getScale() != 1.0D) {
            matrix.scale(getScale(), getScale(), getScale());
        }
        if (getZIndex() != 0.0D) {
            matrix.translate(0.0D, 0.0D, getZIndex());
        }
    }

    void render(@NotNull MatrixStack matrix);
}
