package net.pl3x.guithium.fabric.gui.test;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class RectWidget extends AbstractWidget {
    private final int[] color = new int[4];

    public RectWidget(int width, int height, int[] color) {
        this(0, 0, width, height, color);
    }

    public RectWidget(int x, int y, int width, int height, int[] color) {
        super(x, y, width, height, Component.empty());
        this.color[0] = color.length > 0 ? color[0] : 0xff000000;
        this.color[1] = color.length > 1 ? color[1] : 0xff000000;
        this.color[2] = color.length > 2 ? color[2] : 0xff000000;
        this.color[3] = color.length > 3 ? color[3] : 0xff000000;
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        VertexConsumer buf = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.gui());
        Matrix4f m4f = gfx.pose().last().pose();
        buf.addVertex(m4f, this.getX(), this.getY(), 0).setColor(this.color[0]);
        buf.addVertex(m4f, this.getX(), this.getBottom(), 0).setColor(this.color[1]);
        buf.addVertex(m4f, this.getRight(), this.getBottom(), 0).setColor(this.color[2]);
        buf.addVertex(m4f, this.getRight(), this.getY(), 0).setColor(this.color[3]);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
    }
}
