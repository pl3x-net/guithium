package net.pl3x.servergui.fabric.gui.element;

import net.minecraft.client.util.math.MatrixStack;

import java.util.HashMap;
import java.util.Map;

public class RenderableElementManager {
    public final Map<String, RenderableElement> elements = new HashMap<>();

    public void addElement(RenderableElement element) {
        this.elements.put(element.getElement().getId(), element);
    }

    public RenderableElement getElement(String id) {
        return this.elements.get(id);
    }

    public void clear() {
        this.elements.clear();
    }

    public void render(MatrixStack matrix, float delta) {
        this.elements.forEach((id, element) -> {
            element.render(matrix, delta);
            element.getChildren().forEach(child ->
                child.render(matrix, delta)
            );
        });
    }
}
