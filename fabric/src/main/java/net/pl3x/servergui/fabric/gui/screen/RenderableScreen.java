package net.pl3x.servergui.fabric.gui.screen;

import com.google.common.base.Preconditions;
import net.minecraft.client.util.math.MatrixStack;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.element.RenderableElement;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class RenderableScreen extends AbstractScreen {
    private final Screen screen;
    public final Map<Key, RenderableElement> elements = new HashMap<>();

    public RenderableScreen(@NotNull Screen screen) {
        super(ServerGUIFabric.client == null ? null : ServerGUIFabric.client.currentScreen);

        Preconditions.checkNotNull(screen, "Screen cannot be null");
        this.screen = screen;

        screen.getElements().forEach((key, element) -> {
            RenderableElement renderableElement = RenderableElement.createRenderableElement(element, this);
            if (renderableElement != null) {
                this.elements.put(renderableElement.getElement().getKey(), renderableElement);
            }
        });
    }

    @NotNull
    public Screen getScreen() {
        return this.screen;
    }

    @NotNull
    public Map<Key, RenderableElement> getElements() {
        return this.elements;
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float delta) {
        if (this.screen.getType() != Screen.Type.HUD) {
            Screen.Background background = this.screen.getBackground();
            if (background == null) {
                background = Screen.Background.GRADIENT;
            }
            switch (background) {
                case TEXTURE -> this.renderBackgroundTexture(0);
                case GRADIENT -> this.fillGradient(matrix, 0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
            }
        }
        this.screen.getElements().forEach((key, element) -> {
            RenderableElement renderableElement = this.elements.get(element.getKey());
            if (renderableElement != null) {
                renderableElement.render(matrix, delta);
            }
        });
    }
}
