package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.MutableComponent;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableText extends RenderableElement {
    private MutableComponent text;

    public RenderableText(@NotNull RenderableScreen screen, @NotNull Text text) {
        super(screen, text);
    }

    @Override
    @NotNull
    public Text getElement() {
        return (Text) super.getElement();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        if (getElement().getText() == null) {
            return;
        }

        this.text = processComponent(getElement().getText());

        int textWidth = Minecraft.getInstance().font.width(this.text);
        int textHeight = Minecraft.getInstance().font.lineHeight;

        calcScreenPos(textWidth, textHeight);

        this.centerX = this.posX + textWidth / 2;
        this.centerY = this.posY + textHeight / 2;
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        if (this.text == null) {
            return;
        }

        rotate(poseStack, this.centerX, this.centerY, getElement().getRotation());
        scale(poseStack, this.scaleX, this.scaleY, getElement().getScale());

        MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().font.drawInBatch(
                this.text,
                this.posX,
                this.posY,
                0xFFFFFF,
                Boolean.TRUE.equals(getElement().hasShadow()),
                poseStack.last().pose(),
                immediate,
                Font.DisplayMode.NORMAL,
                0,
                0xF000F0
        );
        immediate.endBatch();
    }
}
