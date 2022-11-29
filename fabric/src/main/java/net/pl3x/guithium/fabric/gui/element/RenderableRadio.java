package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.gui.element.Radio;
import net.pl3x.guithium.api.network.Connection;
import net.pl3x.guithium.api.network.packet.RadioTogglePacket;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.screen.RenderableScreen;
import org.jetbrains.annotations.NotNull;

public class RenderableRadio extends RenderableWidget {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Guithium.MOD_ID, "textures/gui/radio.png");

    public RenderableRadio(@NotNull RenderableScreen screen, @NotNull Radio radio) {
        super(screen, radio);
    }

    @Override
    @NotNull
    public Radio getElement() {
        return (Radio) super.getElement();
    }

    @Override
    @NotNull
    public net.minecraft.client.gui.components.Checkbox getWidget() {
        return (net.minecraft.client.gui.components.Checkbox) super.getWidget();
    }

    @Override
    public void init(@NotNull Minecraft minecraft, int width, int height) {
        this.label = processComponent(getElement().getLabel());
        this.tooltip = processTooltip(getElement().getTooltip());

        Vec2 size = getElement().getSize();
        if (size == null) {
            size = Vec2.of(30 + minecraft.font.width(this.label), 20);
        }

        calcScreenPos(size.getX(), size.getY());

        setWidget(createCheckbox(
            TEXTURE,
            size,
            this.label,
            getElement().isShowLabel(),
            getElement().isSelected()
        ));
    }

    @Override
    protected void onPress(boolean selected) {
        // make sure the value is actually changed
        if (Boolean.TRUE.equals(getElement().isSelected()) == selected) {
            return;
        }

        // toggle this radio and tell the server
        getElement().setSelected(selected);
        Connection conn = Guithium.instance().getNetworkHandler().getConnection();
        conn.send(new RadioTogglePacket(getScreen().getScreen(), getElement(), selected));

        // if this radio was toggled off, we're done.
        if (!selected) {
            return;
        }

        // check if this radio is in a group
        Key group = getElement().getGroup();
        if (group == null) {
            return;
        }

        // toggle off other radios in the same group
        getScreen().getElements().forEach((key, element) -> {
            // ignore self
            if (element == this) {
                return;
            }

            // ensure is radio
            if (!(element instanceof RenderableRadio other)) {
                return;
            }

            // ensure group is the same
            Radio otherRadio = other.getElement();
            if (!group.equals(otherRadio.getGroup())) {
                return;
            }

            // only toggle it off if it's currently on
            if (!Boolean.TRUE.equals(otherRadio.isSelected())) {
                return;
            }

            // finally turn off the other radio and tell the server
            other.getWidget().selected = false;
            otherRadio.setSelected(false);
            conn.send(new RadioTogglePacket(getScreen().getScreen(), otherRadio, false));
        });
    }
}
