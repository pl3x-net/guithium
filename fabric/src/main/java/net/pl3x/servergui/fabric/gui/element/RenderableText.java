package net.pl3x.servergui.fabric.gui.element;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.api.gui.element.Point;
import net.pl3x.servergui.api.gui.element.Text;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RenderableText extends Text implements RenderableElement {
    public RenderableText(@NotNull String id, @NotNull String text, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, boolean shadow, float scale, double zIndex) {
        super(id, text, pos, anchor, offset, shadow, scale, zIndex);
    }

    @Override
    public void render(@NotNull MatrixStack matrix) {
        if (getText().isBlank()) {
            return;
        }

        matrix.push();

        setupScaleAndZIndex(matrix);

        double anchorX = Math.ceil(ServerGUIFabric.screenWidth * getAnchor().getX() / getScale());
        double anchorY = Math.ceil(ServerGUIFabric.screenHeight * getAnchor().getY() / getScale());

        int offsetX = (int) (ServerGUIFabric.client.textRenderer.getWidth(getText()) * getOffset().getX());
        int offsetY = (int) (ServerGUIFabric.client.textRenderer.fontHeight * getOffset().getY());

        int x = (int) (anchorX + getPos().getX() - offsetX);
        int y = (int) (anchorY + getPos().getY() - offsetY);

        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        ServerGUIFabric.client.textRenderer.draw(getText(), x, y, 0xFFFFFF, hasShadow(), matrix.peek().getPositionMatrix(), immediate, false, 0, 0xF000F0);
        immediate.draw();

        matrix.pop();
    }
}
