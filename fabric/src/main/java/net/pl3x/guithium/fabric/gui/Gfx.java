package net.pl3x.guithium.fabric.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.pl3x.guithium.api.Guithium;
import org.jetbrains.annotations.NotNull;

public abstract class Gfx {
    private Gfx() {
        // Empty constructor to pacify javadoc lint
    }

    public static void wrap(@NotNull GuiGraphics gfx, @NotNull Runnable runnable) {
        gfx.pose().pushPose();
        try {
            runnable.run();
        } catch (Exception e) {
            Guithium.logger.error("Uncaught exception trying to render wrapped task", e);
        }
        gfx.pose().popPose();
    }
}
