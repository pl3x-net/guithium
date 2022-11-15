package net.pl3x.servergui.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.pl3x.servergui.api.gui.element.Button;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.screen.RenderableScreen;
import net.pl3x.servergui.fabric.network.packet.ElementPacket;
import org.jetbrains.annotations.NotNull;

public class RenderableButton extends RenderableElement {
    private boolean hovered;

    public RenderableButton(Button button, RenderableScreen screen) {
        super(button, screen);
    }

    @Override
    public Button getElement() {
        return (Button) super.getElement();
    }

    @Override
    public void render(@NotNull MatrixStack matrix, int mouseX, int mouseY, float delta) {
        Button button = getElement();

        if (button.getSize() == null) {
            return;
        }

        matrix.push();

        calcScreenPos(
            button.getSize().getX(),
            button.getSize().getY(),
            setupScaleAndZIndex(matrix)
        );

        int x = (int) getScreenPos().getX();
        int y = (int) getScreenPos().getY();
        int width = (int) button.getSize().getX();
        int height = (int) button.getSize().getY();
        boolean disabled = false;

        this.hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, ClickableWidget.WIDGETS_TEXTURE);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        int yOffset = disabled ? 0 : hovered ? 2 : 1;
        DrawableHelper.drawTexture(matrix, x, y, 0, 0, 46 + yOffset * 20, width / 2, height, 256, 256);
        DrawableHelper.drawTexture(matrix, x + width / 2, y, 0, 200 - width / 2F, 46 + yOffset * 20, width / 2, height, 256, 256);
        DrawableHelper.drawCenteredText(matrix, ServerGUIFabric.client.textRenderer, button.getText(), x + width / 2, y + (height - 8) / 2, disabled ? 10526880 : 16777215);

        matrix.pop();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.hovered) {
            ServerGUIFabric.client.getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            System.out.println("Button clicked!");
            ElementPacket.send("button_click", getElement().getKey().toString());
            return true;
        }
        return false;
    }
}
