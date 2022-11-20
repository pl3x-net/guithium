package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.net.packet.CheckboxTogglePacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RenderableCheckbox extends RenderableWidget {
    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/checkbox.png");

    public RenderableCheckbox(@NotNull RenderableScreen screen, @NotNull Checkbox checkbox) {
        super(screen, checkbox);
    }

    @Override
    @NotNull
    public Checkbox getElement() {
        return (Checkbox) super.getElement();
    }

    @Override
    @NotNull
    public net.minecraft.client.gui.components.Checkbox getWidget() {
        return (net.minecraft.client.gui.components.Checkbox) super.getWidget();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        Point size = getElement().getSize();
        if (size == null) {
            size = Point.of(30 + minecraft.font.width(getElement().getText()), 20);
        }

        calcScreenPos(size.getX(), size.getY());

        final List<FormattedCharSequence> tooltip = processTooltip(getElement().getTooltip());
        Boolean defaultSelected = getElement().isDefaultSelected();
        Boolean showLabel = getElement().isShowLabel();

        setWidget(new net.minecraft.client.gui.components.Checkbox(
            (int) this.pos.getX(),
            (int) this.pos.getY(),
            (int) size.getX(),
            (int) size.getY(),
            Component.translatable(getElement().getText()),
            Boolean.TRUE.equals(defaultSelected),
            showLabel == null || showLabel
        ) {
            @Override
            public void renderButton(@NotNull PoseStack poseStack, int mouseX, int mouseY, float delta) {
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderTexture(0, TEXTURE);
                RenderSystem.setShaderColor(1, 1, 1, 1);

                blit(poseStack, this.x, this.y, isHoveredOrFocused() ? 20.0F : 0.0F, selected() ? 20.0F : 0.0F, 20, this.height, 64, 64);

                if (showLabel == null || showLabel) {
                    drawString(poseStack, minecraft.font, getMessage(), this.x + 24, this.y + (this.height - 8) / 2, 14737632);
                }

                if (tooltip != null && this.isHovered && getTooltipDelay() > 10) {
                    getScreen().renderTooltip(poseStack, tooltip, mouseX, mouseY);
                }

                RenderSystem.disableBlend();
            }

            @Override
            public void onPress() {
                super.onPress();
                CheckboxTogglePacket packet = new CheckboxTogglePacket(getScreen().getScreen(), getElement(), selected());
                Guithium.instance().getNetworkHandler().getConnection().send(packet);
            }
        });
    }
}
