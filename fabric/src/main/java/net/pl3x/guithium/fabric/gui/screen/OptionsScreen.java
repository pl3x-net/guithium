package net.pl3x.guithium.fabric.gui.screen;

import net.minecraft.client.gui.GuiGraphics;
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
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        guiGraphics.drawCenteredString(this.font, OPTIONS_TITLE, this.centerX, 15, 0xFFFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics guiGraphics) {
        if (this.minecraft != null && this.minecraft.level != null) {
            guiGraphics.fillGradient(0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
        } else {
            this.renderDirtBackground(guiGraphics);
        }
    }
}
