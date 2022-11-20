package net.pl3x.guithium.fabric.gui.element;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Tickable;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class RenderableWidget extends RenderableElement implements Tickable {
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

    protected List<FormattedCharSequence> processTooltip(net.kyori.adventure.text.Component tooltip) {
        MutableComponent component = null;
        if (tooltip != null) {
            String json = GsonComponentSerializer.gson().serialize(tooltip);
            try {
                component = Component.Serializer.fromJson(json);
            } catch (Throwable t) {
                component = Component.translatable(json);
            }
        }
        return component == null ? null : Minecraft.getInstance().font.split(component, 200);
    }
}
