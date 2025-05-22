package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.element.Line;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class RenderableLine extends AbstractWidget implements RenderableWidget {
    private final Minecraft client;
    private final AbstractScreen screen;
    private final Line line;

    private int startColor;
    private int endColor;
    private int endPosX = 0;
    private int endPosY = 0;

    public RenderableLine(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Line line) {
        super(0, 0, 0, 0, Component.empty());
        this.client = client;
        this.screen = screen;
        this.line = line;
        this.active = false;
    }

    @Override
    @NotNull
    public Line getElement() {
        return this.line;
    }

    @Override
    public void init() {
        // update size
        setWidth((int) (getElement().getWidth() * this.client.getWindow().getGuiScale()));
        setHeight(0);

        // colors
        this.startColor = getElement().getStartColor();
        this.endColor = getElement().getEndColor();

        // start point
        setX((int) (this.screen.width * getElement().getAnchor().getX() + getElement().getPos().getX()));
        setY((int) (this.screen.height * getElement().getAnchor().getY() + getElement().getPos().getY()));

        // end point
        this.endPosX = (int) (this.screen.width * getElement().getEndAnchor().getX() + getElement().getEndPos().getX());
        this.endPosY = (int) (this.screen.height * getElement().getEndAnchor().getY() + getElement().getEndPos().getY());

        // center point
        RenderableDuck self = (RenderableDuck) this;
        self.setCenterX(getX() + (this.endPosX - getX()) / 2);
        self.setCenterY(getY() + (this.endPosY - getY()) / 2);
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        RenderableDuck self = (RenderableDuck) this;
        self.rotate(gfx, self.getCenterX(), self.getCenterY(), (float) mouseX);//getElement().getRotation());
        self.scale(gfx, self.getCenterX(), self.getCenterY(), getElement().getScale());

        PoseStack.Pose pose = gfx.pose().last();
        Matrix4f model = pose.pose();

        VertexConsumer buf = gfx.bufferSource.getBuffer(RenderType.debugLine(getWidth()));
        buf.addVertex(model, getX(), getY(), 0).setColor(this.startColor).setNormal(pose, 1, 0, 0);
        buf.addVertex(model, this.endPosX, this.endPosY, 0).setColor(this.endColor).setNormal(pose, 1, 0, 0);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        // nothing to narrate
    }
}
