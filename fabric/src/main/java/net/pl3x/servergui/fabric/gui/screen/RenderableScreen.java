package net.pl3x.servergui.fabric.gui.screen;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.vertex.PoseStack;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.api.gui.Screen;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.element.RenderableElement;
import net.pl3x.servergui.fabric.network.packet.ScreenPacket;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class RenderableScreen extends AbstractScreen {
    private final Screen screen;
    private final Map<Key, RenderableElement> elements = new HashMap<>();

    public RenderableScreen(@NotNull Screen screen) {
        super(ServerGUIFabric.client == null ? null : ServerGUIFabric.client.screen);

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
    public void render(PoseStack poseStack, int mouseX, int mouseY, float delta) {
        if (this.screen.getType() != Screen.Type.HUD) {
            Screen.Background background = this.screen.getBackground();
            if (background == null) {
                background = Screen.Background.GRADIENT;
            }
            switch (background) {
                case TEXTURE -> renderDirtBackground(0);
                case GRADIENT -> fillGradient(poseStack, 0, 0, this.width, this.height, 0xC0101010, 0xD0101010);
            }
        }
        this.screen.getElements().forEach((key, element) -> {
            RenderableElement renderableElement = this.elements.get(element.getKey());
            if (renderableElement != null) {
                renderableElement.render(poseStack, mouseX, mouseY, delta);
            }
        });

        super.render(poseStack, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        ScreenPacket.send(this.screen.getKey());
        super.onClose();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (RenderableElement element : getElements().values()) {
            if (element.mouseClicked(mouseX, mouseY, button)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE && shouldCloseOnEsc()) {
            onClose();
            return true;
        }
        return false;
    }
}
