package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableText extends RenderableElement {
    public RenderableText(Text text, RenderableScreen screen) {
        super(text, screen);
    }

    @Override
    @NotNull
    public Text getElement() {
        return (Text) super.getElement();
    }

    @Override
    public void init(Minecraft minecraft, int width, int height) {
        String text = getElement().getText();
        if (text == null || text.isBlank()) {
            return;
        }

        super.init(minecraft, width, height);

        calcScreenPos(
            Minecraft.getInstance().font.width(text),
            Minecraft.getInstance().font.lineHeight
        );
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        Text text = getElement();

        if (text.getText() == null || text.getText().isBlank()) {
            return;
        }

        poseStack.pushPose();

        MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        Minecraft.getInstance().font.drawInBatch(
            text.getText(),
            this.pos.getX(),
            this.pos.getY(),
            0xFFFFFF,
            Boolean.TRUE.equals(text.hasShadow()),
            poseStack.last().pose(),
            immediate,
            false,
            0,
            0xF000F0
        );
        immediate.endBatch();

        poseStack.popPose();
    }
}
