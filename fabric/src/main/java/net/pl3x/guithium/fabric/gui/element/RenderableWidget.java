package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Tickable;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class RenderableWidget extends RenderableElement implements Tickable {
    protected Component label;
    protected List<FormattedCharSequence> tooltip;
    private AbstractWidget widget;
    private int tooltipDelay;

    public RenderableWidget(@NotNull RenderableScreen screen, @NotNull Element element) {
        super(screen, element);
    }

    @NotNull
    public AbstractWidget getWidget() {
        return this.widget;
    }

    protected void setWidget(@NotNull AbstractWidget widget) {
        this.widget = widget;
    }

    public List<FormattedCharSequence> getTooltip() {
        return this.tooltip;
    }

    public int getTooltipDelay() {
        return this.tooltipDelay;
    }

    @Override
    public void tick() {
        if (getWidget().isHovered && getWidget().active) {
            this.tooltipDelay++;
        } else if (this.tooltipDelay > 0) {
            this.tooltipDelay = 0;
        }
    }

    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
        getWidget().render(poseStack, mouseX, mouseY, delta);
    }

    protected void onPress(boolean selected) {
    }

    protected AbstractWidget createCheckbox(@NotNull ResourceLocation texture, @NotNull Vec2 size, @Nullable Component label, @Nullable Boolean showLabel, @Nullable Boolean selected) {
        return new net.minecraft.client.gui.components.Checkbox(
            this.posX,
            this.posY,
            (int) size.getX(),
            (int) size.getY(),
            label,
            Boolean.TRUE.equals(selected),
            showLabel == null || Boolean.TRUE.equals(showLabel)
        ) {
            @Override
            public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
                if (!this.visible) {
                    return;
                }
                rotate(poseStack, this.getX(), this.getY(), this.width, this.height, getElement().getRotation());
                scale(poseStack, this.getX(), this.getY(), this.width, this.height, getElement().getScale());
                this.isHovered = mouseX >= this.getX() && mouseY >= this.getY() && mouseX < this.getX() + this.width && mouseY < this.getY() + this.height;
                renderButton(poseStack, mouseX, mouseY, delta);
            }

            @Override
            public void renderButton(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, texture);
                RenderSystem.setShaderColor(1, 1, 1, 1);

                blit(poseStack, this.getX(), this.getY(), isHoveredOrFocused() ? 20.0F : 0.0F, selected() ? 20.0F : 0.0F, 20, this.height, 64, 64);

                if (this.showLabel) {
                    drawString(poseStack, Minecraft.getInstance().font, getMessage(), this.getX() + 24, this.getY() + (this.height - 8) / 2, 0xFFE0E0E0);
                }
            }

            @Override
            public void onPress() {
                super.onPress();
                RenderableWidget.this.onPress(selected());
            }
        };
    }
}
