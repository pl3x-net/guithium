package net.pl3x.guithium.fabric.gui.element;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.pl3x.guithium.api.gui.element.Button;
import net.pl3x.guithium.api.gui.element.Element;
import org.jetbrains.annotations.NotNull;

// Guithium's renderable widgets implements these
public interface RenderableWidget {
    @NotNull
    Element getElement();

    void init(@NotNull Minecraft client);

    @NotNull
    static AbstractWidget create(@NotNull Element element) {
        return switch (element.getType()) {
            case BUTTON -> new RenderableButton((Button) element);
            //case CHECKBOX -> new RenderableCheckbox(screen, (Checkbox) element);
            //case CIRCLE -> new RenderableCircle(screen, (Circle) element);
            //case GRADIENT -> new RenderableGradient(screen, (Gradient) element);
            //case IMAGE -> new RenderableImage(screen, (Image) element);
            //case LINE -> new RenderableLine(screen, (Line) element);
            //case RADIO -> new RenderableRadio(screen, (Radio) element);
            //case SLIDER -> new RenderableSlider(screen, (Slider) element);
            //case TEXT -> new RenderableText(screen, (Text) element);
            //case TEXTBOX -> new RenderableTextbox(screen, (Textbox) element);
            default -> null;
        };
    }
}
