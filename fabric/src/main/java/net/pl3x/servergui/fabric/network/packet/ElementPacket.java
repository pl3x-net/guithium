package net.pl3x.servergui.fabric.network.packet;

import com.google.common.io.ByteArrayDataInput;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.pl3x.servergui.api.ServerGUI;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.json.Gson;
import net.pl3x.servergui.fabric.ServerGUIFabric;
import net.pl3x.servergui.fabric.gui.element.RenderableElementManager;
import net.pl3x.servergui.fabric.gui.element.RenderableElement;

public class ElementPacket extends Packet {
    public static final Identifier CHANNEL = new Identifier(ServerGUI.MOD_ID, "element");

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
        ByteArrayDataInput in = in(buf.getWrittenBytes());
        String payload = in.readUTF();

        Element element = Gson.fromJson(payload, Element.class);

        System.out.println("---");
        System.out.println("Payload: " + payload);
        System.out.println("Element: " + element);
        System.out.println("Parent: " + element.getParent());

        RenderableElementManager manager = ServerGUIFabric.instance().getRenderableElementManager();

        // check if renderable element already exists
        RenderableElement renderable = manager.getElement(element.getId());
        if (renderable != null) {
            System.out.println("update existing");
            // get old parent
            RenderableElement oldParent = renderable.getParent();
            // check if parent changed
            if (oldParent != null && !oldParent.getElement().getId().equals(element.getParent())) {
                // remove from old parent
                oldParent.getChildren().remove(renderable);
                // check if new parent is set
                if (element.getParent() != null) {
                    // get new parent
                    RenderableElement newParent = manager.getElement(element.getParent());
                    // check if new parent exists
                    if (newParent != null) {
                        // add to new parent
                        newParent.getChildren().add(renderable);
                    }
                }
            } else if (oldParent == null && element.getParent() != null) {
                // get new parent
                RenderableElement newParent = manager.getElement(element.getParent());
                // check if new parent exists
                if (newParent != null) {
                    // add to new parent
                    newParent.getChildren().add(renderable);
                }
            }
            // update element
            renderable.setElement(element);
        } else {
            System.out.println("new element");
            // create new renderable element
            renderable = RenderableElement.createRenderableElement(element);
            // check if was successful
            if (renderable != null) {
                // add to the manager
                manager.addElement(renderable);
                // check if there is parent
                if (renderable.getParent() != null) {
                    // has parent, add to the parent
                    renderable.getParent().getChildren().add(renderable);
                }
            }
        }

        // temp debug output
        System.out.println("manager size: " + manager.elements.size());
        if (renderable != null) {
            System.out.println("renderable children: " + renderable.getChildren().size());
            if (renderable.getParent() != null) {
                System.out.println("parent's children: " + renderable.getParent().getChildren().size());
            } else {
                System.out.println("no parent set");
            }
        } else {
            System.out.println("renderable is null");
        }
    }
}
