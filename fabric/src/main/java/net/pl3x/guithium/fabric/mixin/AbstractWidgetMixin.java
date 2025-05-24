package net.pl3x.guithium.fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.fabric.gui.element.RenderableDuck;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AbstractWidget.class)
public abstract class AbstractWidgetMixin implements RenderableDuck {
    @Unique
    private Minecraft client;
    @Unique
    private AbstractScreen screen;

    @Unique
    private int centerX;
    @Unique
    private int centerY;

    @Shadow
    private int x;
    @Shadow
    private int y;
    @Shadow
    protected int width;
    @Shadow
    protected int height;

    @Override
    @Unique
    @NotNull
    public Minecraft getClient() {
        return this.client;
    }

    @Override
    @Unique
    @NotNull
    public AbstractScreen getScreen() {
        return this.screen;
    }

    @Override
    @Unique
    @NotNull
    public RenderableDuck duck(@NotNull Minecraft client, @NotNull AbstractScreen screen) {
        this.client = client;
        this.screen = screen;
        return this;
    }

    @Override
    @Unique
    public int getCenterX() {
        return this.centerX;
    }

    @Override
    @Unique
    public void setCenterX(int x) {
        this.centerX = x;
    }

    @Override
    @Unique
    public int getCenterY() {
        return this.centerY;
    }

    @Override
    @Unique
    public void setCenterY(int y) {
        this.centerY = y;
    }

    @Override
    @Unique
    public void calcScreenPos(int sizeX, int sizeY) {
        float anchorX, anchorY;
        Vec2 anchor = getElement().getAnchor();
        if (anchor == Vec2.ZERO) {
            anchorX = 0;
            anchorY = 0;
        } else if (anchor == Vec2.ONE) {
            anchorX = this.screen.width;
            anchorY = this.screen.height;
        } else {
            anchorX = this.screen.width * anchor.getX();
            anchorY = this.screen.height * anchor.getY();
        }

        float offsetX, offsetY;
        Vec2 offset = getElement().getOffset();
        if (offset == Vec2.ZERO) {
            offsetX = 0;
            offsetY = 0;
        } else if (offset == Vec2.ONE) {
            offsetX = this.width;
            offsetY = this.height;
        } else {
            offsetX = sizeX * offset.getX();
            offsetY = sizeY * offset.getY();
        }

        Vec2 pos = getElement().getPos();
        this.x = (int) (anchorX + pos.getX() - offsetX);
        this.y = (int) (anchorY + pos.getY() - offsetY);

        setCenterX(this.x + this.width / 2);
        setCenterY(this.y + this.height / 2);
    }

    @WrapMethod(method = "render")
    public void render(@NotNull GuiGraphics gfx, int mouseX, int mouseY, float delta, @NotNull Operation<Void> original) {
        gfx.pose().pushPose();
        original.call(gfx, mouseX, mouseY, delta);
        gfx.pose().popPose();
    }
}
