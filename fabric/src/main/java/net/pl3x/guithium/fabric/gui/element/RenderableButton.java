package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.net.packet.ButtonClickPacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
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
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        Button button = getElement();

        if (button.getSize() == null) {
            return;
        }

        poseStack.pushPose();

        calcScreenPos(
            button.getSize().getX(),
            button.getSize().getY(),
            setupScaleAndZIndex(poseStack)
        );

        int x = (int) getScreenPos().getX();
        int y = (int) getScreenPos().getY();
        int width = (int) button.getSize().getX();
        int height = (int) button.getSize().getY();
        boolean disabled = false;

        this.hovered = mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, AbstractWidget.WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        int yOffset = disabled ? 0 : hovered ? 2 : 1;
        GuiComponent.blit(poseStack, x, y, 0, 0, 46 + yOffset * 20, width / 2, height, 256, 256);
        GuiComponent.blit(poseStack, x + width / 2, y, 0, 200 - width / 2F, 46 + yOffset * 20, width / 2, height, 256, 256);
        GuiComponent.drawCenteredString(poseStack, Guithium.client.font, button.getText(), x + width / 2, y + (height - 8) / 2, disabled ? 10526880 : 16777215);

        poseStack.popPose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.hovered) {
            Guithium.client.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            ButtonClickPacket packet = new ButtonClickPacket(getScreen().getScreen(), getElement());
            Guithium.instance().getNetworkHandler().getConnection().send(packet);
            return true;
        }
        return false;
    }
}
