package net.pl3x.guithium.fabric.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.pl3x.guithium.fabric.Guithium;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {
    @Inject(method = "renderHotbar", at = @At("HEAD"))
    private void renderHotbar(float delta, @NotNull GuiGraphics gfx, @NotNull CallbackInfo ci) {
        try {
            Guithium.instance().getHudManager().render(gfx, delta);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
    private void renderPlayerHealth(@NotNull GuiGraphics gfx, @NotNull CallbackInfo ci) {
        if (Guithium.instance().getHudManager().canShowStatsHud()) {
            return;
        }
        ci.cancel();
    }

    @Inject(method = "renderVehicleHealth", at = @At("HEAD"), cancellable = true)
    private void renderVehicleHealth(@NotNull GuiGraphics gfx, @NotNull CallbackInfo ci) {
        if (Guithium.instance().getHudManager().canShowStatsHud()) {
            return;
        }
        ci.cancel();
    }
}
