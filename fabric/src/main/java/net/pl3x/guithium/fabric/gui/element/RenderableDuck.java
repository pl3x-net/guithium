package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

// Vanilla's abstract widget mixin implements these
public interface RenderableDuck extends RenderableWidget {
    int getCenterX();

    void setCenterX(int x);

    int getCenterY();

    void setCenterY(int y);

    void calcScreenPos(int width, int height);

    default void rotate(@NotNull GuiGraphics gfx, int x, int y, int width, int height, @Nullable Float degrees) {
        if (degrees == null) {
            return;
        }
        rotate(gfx, (int) (x + width / 2F), (int) (y + height / 2F), degrees);
    }

    default void rotate(@NotNull GuiGraphics gfx, int x, int y, @Nullable Float degrees) {
        if (degrees == null) {
            return;
        }
        gfx.pose().translate(x, y, 0);
        gfx.pose().mulPose((new Quaternionf()).rotateZ(degrees * 0.017453292F));
        gfx.pose().translate(-x, -y, 0);
    }

    default void scale(@NotNull GuiGraphics gfx, int x, int y, int width, int height, @Nullable Float scale) {
        if (scale == null) {
            return;
        }
        scale(gfx, (int) (x + width / 2F), (int) (y + height / 2F), scale);
    }

    default void scale(@NotNull GuiGraphics gfx, int x, int y, @Nullable Float scale) {
        if (scale == null) {
            return;
        }
        gfx.pose().translate(x, y, 0);
        gfx.pose().scale(scale, scale, 0);
        gfx.pose().translate(-x, -y, 0);
    }
}
