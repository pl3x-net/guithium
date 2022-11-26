package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.network.packet.CheckboxTogglePacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

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
        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.of(30 + minecraft.font.width(getElement().getLabel()), 20);
        }

        calcScreenPos(size.getX(), size.getY());

        setWidget(createCheckbox(
            TEXTURE,
            size,
            getElement().getLabel(),
            getElement().isShowLabel(),
            getElement().isSelected(),
            processTooltip(getElement().getTooltip())
        ));
    }

    @Override
    protected void onPress(boolean selected) {
        // make sure the value is actually changed
        if (Boolean.TRUE.equals(getElement().isSelected()) == selected) {
            return;
        }

        // toggle this checkbox and tell the server
        getElement().setSelected(selected);
        Guithium.instance().getNetworkHandler().getConnection()
            .send(new CheckboxTogglePacket(getScreen().getScreen(), getElement(), selected));
    }
}
