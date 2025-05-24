package net.pl3x.guithium.fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.fabric.GuithiumMod;
import net.pl3x.guithium.fabric.gui.Gfx;
import net.pl3x.guithium.fabric.gui.HudManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
public class GuiMixin {
    @WrapMethod(method = "render")
    private void preRender(@NotNull GuiGraphics gfx, @NotNull DeltaTracker deltaTracker, @NotNull Operation<Void> original) {
        HudManager hud = ((GuithiumMod) Guithium.api()).getHudManager();
        float delta = deltaTracker.getGameTimeDeltaTicks();

        Gfx.wrap(gfx, () -> hud.preRender(gfx, delta));
        original.call(gfx, deltaTracker);
        Gfx.wrap(gfx, () -> hud.postRender(gfx, delta));
    }

    // list of render methods in mojang's Gui.class
    // we might want to ass toggle overrides for

    // renderCameraOverlays
    // renderCrosshair
    // renderHotbarAndDecorations
    // spectatorGui.renderHotbar
    // renderItemHotbar
    // renderJumpMeter
    // renderExperienceBar
    // renderPlayerHealth
    // renderVehicleHealth
    // renderSelectedItemName
    // spectatorGui.renderTooltip
    // renderExperienceLevel
    // renderEffects
    // ??? bossOverlay

    // renderScoreboardSidebar
    // renderOverlayMessage
    // renderTitle
    // renderChat
    // renderTabList
    // ??? subtitleOverlay

    // renderSleepOverlay
}
