package net.pl3x.servergui.fabric.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class MixinInGameHud {
    @Inject(method = "renderHotbar", at = @At("HEAD"))
    private void renderHotbar(float delta, MatrixStack matrix, CallbackInfo ci) {
        try {
            ServerGUIFabric.instance().getHudManager().render(matrix, delta);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
