package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Line;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class RenderableLine extends AbstractWidget implements RenderableWidget {
    private final RenderableDuck self;

    private Line line;

    private int startColor;
    private int endColor;
    private int endPosX = 0;
    private int endPosY = 0;

    public RenderableLine(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Line line) {
        super(0, 0, 0, 0, Component.empty());
        this.self = ((RenderableDuck) this).duck(client, screen);
        this.line = line;
        this.active = false;
    }

    @Override
    @NotNull
    public Line getElement() {
        return this.line;
    }

    @Override
    public void updateElement(@NotNull Element element) {
        this.line = (Line) element;
        this.self.getScreen().refresh();
    }

    @Override
    public void init() {
        // update size
        setWidth((int) (getElement().getWidth() * this.self.getClient().getWindow().getGuiScale()));
        setHeight(0);

        // colors
        this.startColor = getElement().getStartColor();
        this.endColor = getElement().getEndColor();

        // start point
        setX((int) (this.self.getScreen().width * getElement().getAnchor().getX() + getElement().getPos().getX()));
        setY((int) (this.self.getScreen().height * getElement().getAnchor().getY() + getElement().getPos().getY()));

        // end point
        this.endPosX = (int) (this.self.getScreen().width * getElement().getEndAnchor().getX() + getElement().getEndPos().getX());
        this.endPosY = (int) (this.self.getScreen().height * getElement().getEndAnchor().getY() + getElement().getEndPos().getY());

        // center point
        this.self.setCenterX(getX() + (this.endPosX - getX()) / 2);
        this.self.setCenterY(getY() + (this.endPosY - getY()) / 2);
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        this.self.rotate(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getRotation());
        this.self.scale(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getScale());

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
