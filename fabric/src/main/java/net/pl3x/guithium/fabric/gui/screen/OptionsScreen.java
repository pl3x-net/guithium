package net.pl3x.guithium.fabric.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.Guithium;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class OptionsScreen extends AbstractScreen {
    public static final Component OPTIONS_TITLE = Component.translatable(Guithium.MOD_ID + ".options.title");

    public OptionsScreen(@Nullable Screen parent) {
        super(parent);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, OPTIONS_TITLE, this.centerX, 15, 0xFFFFFFFF);
        super.render(poseStack, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(@NotNull PoseStack poseStack) {
        if (this.minecraft != null && this.minecraft.level != null) {
            fillGradient(poseStack, 0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
        } else {
            this.renderDirtBackground(poseStack);
        }
    }
}
