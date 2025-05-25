package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.gui.element.Circle;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Gradient;
import net.pl3x.guithium.api.gui.element.Image;
import net.pl3x.guithium.api.gui.element.Line;
import net.pl3x.guithium.api.gui.element.Radio;
import net.pl3x.guithium.api.gui.element.Slider;
import net.pl3x.guithium.api.gui.element.Text;
import net.pl3x.guithium.api.gui.element.Textbox;
import net.pl3x.guithium.api.network.Connection;
import net.pl3x.guithium.fabric.GuithiumMod;
import net.pl3x.guithium.fabric.gui.screen.AbstractScreen;
import org.jetbrains.annotations.NotNull;

// Guithium's renderable widgets implements these
public interface RenderableWidget {
    @NotNull
    Element getElement();

    void updateElement(@NotNull Element element);

    void init();

    @NotNull
    default Connection conn() {
        return ((GuithiumMod) Guithium.api()).getNetworkHandler().getConnection();
    }

    @NotNull
    static AbstractWidget create(@NotNull Minecraft client, @NotNull AbstractScreen screen, @NotNull Element element) {
        String type = element.getClass().getSimpleName();
        return switch (type) {
            case "Button" -> new RenderableButton(client, screen, (Button) element);
            case "Checkbox" -> new RenderableCheckbox(client, screen, (Checkbox) element);
            case "Circle" -> new RenderableCircle(client, screen, (Circle) element);
            case "Gradient" -> new RenderableGradient(client, screen, (Gradient) element);
            case "Image" -> new RenderableImage(client, screen, (Image) element);
            case "Line" -> new RenderableLine(client, screen, (Line) element);
            case "Radio" -> new RenderableRadio(client, screen, (Radio) element);
            case "Slider" -> new RenderableSlider(client, screen, (Slider) element);
            case "Text" -> new RenderableText(client, screen, (Text) element);
            case "Textbox" -> new RenderableTextbox(client, screen, (Textbox) element);
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }
}
