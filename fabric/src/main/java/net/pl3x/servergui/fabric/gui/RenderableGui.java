package net.pl3x.servergui.fabric.gui;

import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.api.gui.Gui;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.fabric.gui.element.RenderableElement;

import java.util.Iterator;

public class RenderableGui extends Gui {
    public RenderableGui(String id, Type type) {
        super(id, type);
    }

    public void render(MatrixStack matrix) {
        Iterator<Element> iter = getElements().iterator();
        while (iter.hasNext()) {
            Element element = iter.next();
            if (element == null) {
                // TODO - use better json adapters so this isn't needed
                iter.remove();
            } else if (element instanceof RenderableElement renderableElement) {
                renderableElement.render(matrix);
            }
        }
    }
}
