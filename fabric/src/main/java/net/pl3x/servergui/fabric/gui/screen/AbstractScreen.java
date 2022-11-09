package net.pl3x.servergui.fabric.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class AbstractScreen extends Screen {
    private final Screen parent;

    protected int centerX;

    public AbstractScreen(Screen parent) {
        super(Text.translatable("servergui.options.title"));
        this.parent = parent;
    }

    @Override
    public void init() {
        super.init();
        this.centerX = (int) (this.width / 2F);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
        renderBackground(matrixStack);
        drawCenteredText(matrixStack, this.textRenderer, this.title, this.centerX, 15, 0xFFFFFFFF);
        super.render(matrixStack, mouseX, mouseY, delta);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack, int vOffset) {
        if (this.client != null && this.client.world != null) {
            this.fillGradient(matrixStack, 0, 0, this.width, this.height, 0xF00F4863, 0xF0370038);
        } else {
            this.renderBackgroundTexture(vOffset);
        }
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(this.parent);
        }
    }

    public void openScreen(Screen screen) {
        if (this.client != null) {
            this.client.setScreen(screen);
        }
    }
}
