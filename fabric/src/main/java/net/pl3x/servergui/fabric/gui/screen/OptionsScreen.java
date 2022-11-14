package net.pl3x.servergui.fabric.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.pl3x.servergui.api.ServerGUI;

public class OptionsScreen extends AbstractScreen {
    public static final Text OPTIONS_TITLE = Text.translatable(ServerGUI.MOD_ID + ".options.title");

    public OptionsScreen(Screen parent) {
        super(parent);
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        renderBackground(matrix);
        drawCenteredText(matrix, this.textRenderer, OPTIONS_TITLE, this.centerX, 15, 0xFFFFFFFF);
        super.render(matrix, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(MatrixStack matrix, int vOffset) {
        if (this.client != null && this.client.world != null) {
            this.fillGradient(matrix, 0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
        } else {
            this.renderBackgroundTexture(vOffset);
        }
    }
}
