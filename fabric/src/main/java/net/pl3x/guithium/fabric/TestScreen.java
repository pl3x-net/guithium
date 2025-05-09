package net.pl3x.guithium.fabric;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class TestScreen extends Screen {
    public TestScreen() {
        super(Component.translatable("guithium.test.screen"));
    }

    protected void init() {
        super.init();

        GridLayout layout = new GridLayout();
        layout.defaultCellSetting().padding(4, 4, 4, 0);

        GridLayout.RowHelper row = layout.createRowHelper(1);

        row.addChild(new StringWidget(this.title, this.font));

        row.addChild(new StringWidget(Component.literal("test string 1"), this.font));
        row.addChild(new StringWidget(Component.literal("test string 2"), this.font));
        row.addChild(new StringWidget(Component.literal("test string 3"), this.font));

        row.addChild(Button.builder(CommonComponents.GUI_DONE, (btn) -> onClose()).width(200).build());

        layout.visitWidgets(this::addRenderableWidget);
        layout.arrangeElements();
    }

    @NotNull
    protected <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T guiEventListener) {
        return super.addRenderableWidget(guiEventListener);
    }

    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
    }

    public void onClose() {
        super.onClose();
    }
}
