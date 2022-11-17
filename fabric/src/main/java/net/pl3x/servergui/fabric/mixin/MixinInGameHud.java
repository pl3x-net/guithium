package net.pl3x.servergui.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.pl3x.servergui.fabric.ServerGUI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinInGameHud {
    @Inject(method = "renderHotbar", at = @At("HEAD"))
    private void renderHotbar(float delta, PoseStack poseStack, CallbackInfo ci) {
        try {
            ServerGUI.instance().getScreenManager().render(poseStack, delta);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
