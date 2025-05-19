package net.pl3x.guithium.fabric.gui.test;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

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

        row.addChild(new RectWidget(100, 25, new int[]{0xffff0000, 0xff00ff00, 0xff0000ff, 0xffffff00}));

        row.addChild(Button.builder(CommonComponents.GUI_DONE, (btn) -> onClose()).width(200).build());

        layout.visitWidgets(this::addRenderableWidget);
        layout.arrangeElements();
    }
}
