package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.Vec4;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Image;
import net.pl3x.guithium.fabric.GuithiumMod;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import net.pl3x.guithium.fabric.gui.texture.FabricTexture;
import org.jetbrains.annotations.NotNull;

public class RenderableImage extends AbstractWidget implements RenderableWidget {
    private final RenderableDuck self;
    private final FabricTexture texture;

    private Image image;
    private float x0, y0, x1, y1;
    private float u0, v0, u1, v1;
    private int vertexColor;

    public RenderableImage(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Image image) {
        super(0, 0, 0, 0, Component.empty());
        this.self = ((RenderableDuck) this).duck(client, screen);
        this.image = image;
        this.active = false;

        this.texture = ((GuithiumMod) Guithium.api()).getTextureManager().getOrCreate(image);
    }

    @Override
    @NotNull
    public Image getElement() {
        return this.image;
    }

    @Override
    public void updateElement(@NotNull Element element) {
        this.image = (Image) element;
        this.self.getScreen().refresh();
    }

    @Override
    public void init() {
        // update pos/size
        setX((int) getElement().getPos().getX());
        setY((int) getElement().getPos().getY());
        setWidth((int) getElement().getSize().getX());
        setHeight((int) getElement().getSize().getY());

        // recalculate position on screen
        this.self.calcScreenPos(getWidth(), getHeight());

        this.x0 = getX();
        this.y0 = getY();
        this.x1 = getX() + getWidth();
        this.y1 = getY() + getHeight();

        Vec4 uv = getElement().getUV();
        Float tileMod = getElement().getTileModifier();
        if (uv != null) {
            this.u0 = uv.getX();
            this.v0 = uv.getY();
            this.u1 = uv.getZ();
            this.v1 = uv.getW();
            if (tileMod != null) {
                this.u1 /= tileMod;
                this.v1 /= tileMod;
            }
        } else {
            this.u0 = 0;
            this.v0 = 0;
            if (tileMod == null) {
                this.u1 = 1;
                this.v1 = 1;
            } else {
                this.u1 = this.x1 / tileMod;
                this.v1 = this.y1 / tileMod;
            }
        }

        this.vertexColor = getElement().getVertexColor() == null ? 0xFFFFFFFF : getElement().getVertexColor();
    }

    @Override
    protected void renderWidget(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta) {
        if (!this.texture.isLoaded()) {
            return;
        }

        this.self.rotate(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getRotation());
        this.self.scale(gfx, this.self.getCenterX(), this.self.getCenterY(), getElement().getScale());

        this.texture.render(gfx, this.x0, this.y0, this.x1, this.y1, this.u0, this.v0, this.u1, this.v1, this.vertexColor);
    }

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
        // nothing to narrate
    }
}
