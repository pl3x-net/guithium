package net.pl3x.servergui.fabric.gui.element;

import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.api.gui.element.Container;
import org.jetbrains.annotations.NotNull;

public class RenderableContainer extends RenderableElement {
    public RenderableContainer(Container container, RenderableElement parent) {
        super(container, parent);
    }

    @Override
    public Container getElement() {
        return (Container) super.getElement();
    }

    @Override
    public void render(@NotNull MatrixStack matrix, float delta) {
        //
    }
}
