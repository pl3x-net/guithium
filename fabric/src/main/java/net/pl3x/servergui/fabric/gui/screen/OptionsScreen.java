package net.pl3x.servergui.fabric.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.pl3x.servergui.api.ServerGUI;

public class OptionsScreen extends AbstractScreen {
    public static final Component OPTIONS_TITLE = Component.translatable(ServerGUI.MOD_ID + ".options.title");

    public OptionsScreen(Screen parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        renderBackground(poseStack);
        drawCenteredString(poseStack, this.font, OPTIONS_TITLE, this.centerX, 15, 0xFFFFFFFF);
        super.render(poseStack, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(PoseStack poseStack, int vOffset) {
        if (this.minecraft != null && this.minecraft.level != null) {
            this.fillGradient(poseStack, 0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
        } else {
            this.renderDirtBackground(vOffset);
        }
    }
}
