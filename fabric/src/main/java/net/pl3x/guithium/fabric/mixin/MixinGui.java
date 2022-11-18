package net.pl3x.guithium.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Gui;
import net.pl3x.guithium.fabric.Guithium;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {
    @Inject(method = "renderHotbar", at = @At("HEAD"))
    private void renderHotbar(float delta, @NotNull PoseStack poseStack, @NotNull CallbackInfo ci) {
        try {
            Guithium.instance().getHudManager().render(poseStack, delta);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
