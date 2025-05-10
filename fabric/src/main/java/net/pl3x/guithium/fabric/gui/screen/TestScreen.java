package net.pl3x.guithium.fabric.gui.screen;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.Size;
import net.pl3x.guithium.fabric.gui.element.RectWidget;

public class TestScreen extends Screen {
    public TestScreen() {
        super(Component.translatable("guithium.test.screen"));
    }

    protected void init() {
        super.init();

        Guithium.api().logger().warn(Size.of(1, 1).toString());
        Guithium.api().logger().warn(Size.of(1, 1).hashCode() + "");

        Guithium.api().logger().warn(Point.of(1, 1).toString());
        Guithium.api().logger().warn(Point.of(1, 1).hashCode() + "");

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
