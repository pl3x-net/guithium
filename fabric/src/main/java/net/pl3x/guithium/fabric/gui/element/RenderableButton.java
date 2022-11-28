package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.network.packet.ButtonClickPacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableButton extends RenderableWidget {
    public RenderableButton(@NotNull RenderableScreen screen, @NotNull Button button) {
        super(screen, button);
    }

    @Override
    @NotNull
    public Button getElement() {
        return (Button) super.getElement();
    }

    @Override
    @NotNull
    public net.minecraft.client.gui.components.Button getWidget() {
        return (net.minecraft.client.gui.components.Button) super.getWidget();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.of(30 + minecraft.font.width(getElement().getLabel()), 20);
        }

        this.tooltip = processTooltip(getElement().getTooltip());

        calcScreenPos(size.getX(), size.getY());

        setWidget(new net.minecraft.client.gui.components.Button(
            this.posX,
            this.posY,
            (int) size.getX(),
            (int) size.getY(),
            Component.translatable(getElement().getLabel()),
            (button) -> {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                ButtonClickPacket packet = new ButtonClickPacket(getScreen().getScreen(), getElement());
                Guithium.instance().getNetworkHandler().getConnection().send(packet);
            }
        ) {
            @Override
            public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
                if (!this.visible) {
                    return;
                }
                rotate(poseStack, this.x, this.y, this.width, this.height, getElement().getRotation());
                this.isHovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
                renderButton(poseStack, mouseX, mouseY, delta);
            }

            @Override
            public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float delta) {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
                RenderSystem.setShaderColor(1, 1, 1, 1);

                int yOffset = getYImage(isHoveredOrFocused());
                blit(poseStack, this.x, this.y, 0, 46 + yOffset * 20, this.width / 2, this.height);
                blit(poseStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + yOffset * 20, this.width / 2, this.height);
                renderBg(poseStack, minecraft, mouseX, mouseY);
                drawCenteredString(poseStack, minecraft.font, getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, this.active ? 0xFFFFFFFF : 0xFFA0A0A0);
            }
        });
    }
}
