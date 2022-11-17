package net.pl3x.guithium.fabric.gui.screen;

import com.google.common.base.Preconditions;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.pl3x.guithium.fabric.Guithium;
import net.pl3x.guithium.fabric.gui.element.RenderableElement;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.net.packet.CloseScreenPacket;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;

public class RenderableScreen extends AbstractScreen {
    private final Screen screen;
    private final Map<Key, RenderableElement> elements = new HashMap<>();

    public RenderableScreen(@NotNull Screen screen) {
        super(Guithium.client == null ? null : Guithium.client.screen);

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
        Guithium.instance().getNetworkHandler().getConnection().send(new CloseScreenPacket(this.screen));
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
            close();
            return true;
        }
        return false;
    }

    public void close() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::close);
            return;
        }
        onClose();
    }

    public void open() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::open);
            return;
        }
        Guithium.client.setScreen(this);
    }
}
