package net.pl3x.guithium.fabric.mixin;

import net.minecraft.client.Minecraft;
import net.pl3x.guithium.fabric.Guithium;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "resizeDisplay", at = @At("TAIL"))
    public void resizeDisplay(CallbackInfo ci) {
        Guithium.instance().getHudManager().refresh();
    }
}
