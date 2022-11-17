package net.pl3x.servergui.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.pl3x.servergui.api.gui.element.Text;
import net.pl3x.servergui.fabric.ServerGUI;
import net.pl3x.servergui.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableText extends RenderableElement {
    public RenderableText(Text text, RenderableScreen screen) {
        super(text, screen);
    }

    @Override
    public Text getElement() {
        return (Text) super.getElement();
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        Text text = getElement();

        if (text.getText() == null || text.getText().isBlank()) {
            return;
        }

        poseStack.pushPose();

        calcScreenPos(
            ServerGUI.client.font.width(text.getText()),
            ServerGUI.client.font.lineHeight,
            setupScaleAndZIndex(poseStack)
        );

        MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        ServerGUI.client.font.drawInBatch(
            text.getText(),
            getScreenPos().getX(),
            getScreenPos().getY(),
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
