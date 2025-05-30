package net.pl3x.guithium.fabric.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.pl3x.guithium.fabric.util.RenderQueue;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderSystem.class)
public abstract class RenderSystemMixin {
    @Inject(
            method = "flipFrame",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/vertex/Tesselator;clear()V"
            )
    )
    private static void processRenderQueue(@NotNull CallbackInfo ci) {
        RenderQueue.process();
    }
}
