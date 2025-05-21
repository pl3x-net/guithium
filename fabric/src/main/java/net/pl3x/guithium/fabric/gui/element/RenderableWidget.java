package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.gui.element.Checkbox;
import net.pl3x.guithium.api.gui.element.Circle;
import net.pl3x.guithium.api.gui.element.Element;
import net.pl3x.guithium.api.gui.element.Gradient;
import net.pl3x.guithium.api.gui.element.Radio;
import net.pl3x.guithium.api.gui.element.Slider;
import org.jetbrains.annotations.NotNull;

// Guithium's renderable widgets implements these
public interface RenderableWidget {
    @NotNull
    Element getElement();

    void init(@NotNull Minecraft client);

    @NotNull
    static AbstractWidget create(@NotNull Element element) {
        return switch (element.getClass().getSimpleName()) {
            case "Button" -> new RenderableButton((Button) element);
            case "Checkbox" -> new RenderableCheckbox((Checkbox) element);
            case "Circle" -> new RenderableCircle((Circle) element);
            case "Gradient" -> new RenderableGradient((Gradient) element);
            //case IMAGE -> new RenderableImage(screen, (Image) element);
            //case LINE -> new RenderableLine(screen, (Line) element);
            case "Radio" -> new RenderableRadio((Radio) element);
            case "Slider" -> new RenderableSlider((Slider) element);
            //case TEXT -> new RenderableText(screen, (Text) element);
            //case TEXTBOX -> new RenderableTextbox(screen, (Textbox) element);
            default -> null;
        };
    }
}
