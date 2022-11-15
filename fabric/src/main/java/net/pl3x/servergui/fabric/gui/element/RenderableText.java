package net.pl3x.servergui.fabric.gui.element;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.api.gui.element.Text;
import net.pl3x.servergui.fabric.ServerGUIFabric;
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
    public void render(@NotNull MatrixStack matrix, int mouseX, int mouseY, float delta) {
        Text text = getElement();

        if (text.getText() == null || text.getText().isBlank()) {
            return;
        }

        matrix.push();

        calcScreenPos(
            ServerGUIFabric.client.textRenderer.getWidth(text.getText()),
            ServerGUIFabric.client.textRenderer.fontHeight,
            setupScaleAndZIndex(matrix)
        );

        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        ServerGUIFabric.client.textRenderer.draw(
            text.getText(),
            getScreenPos().getX(),
            getScreenPos().getY(),
            0xFFFFFF,
            Boolean.TRUE.equals(text.hasShadow()),
            matrix.peek().getPositionMatrix(),
            immediate,
            false,
            0,
            0xF000F0
        );
        immediate.draw();

        matrix.pop();
    }
}
