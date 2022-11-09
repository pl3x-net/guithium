package net.pl3x.servergui.api.gui;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import net.pl3x.servergui.api.gui.element.Element;
import net.pl3x.servergui.api.json.JsonObjectWrapper;
import net.pl3x.servergui.api.json.JsonSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Gui implements JsonSerializable {
    private final String id;
    private final Type type;
    private final List<Element> elements = new ArrayList<>();

    public Gui(String id, Type type) {
        Preconditions.checkNotNull(id, "ID cannot be null");
        Preconditions.checkNotNull(type, "Type cannot be null");
        this.id = id;
        this.type = type;
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    @NotNull
    public Type getType() {
        return this.type;
    }

    public void addElement(Element element) {
        this.elements.add(element);
    }

    public Element getElement(String id) {
        for (Element element : getElements()) {
            if (element.getId().equals(id)) {
                return element;
            }
        }
        return null;
    }

    public List<Element> getElements() {
        return this.elements;
    }

    public void removeElement(Element element) {
        this.elements.remove(element);
    }

    public void removeElement(String id) {
        Element element = getElement(id);
        if (element != null) {
            this.elements.remove(element);
        }
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper();
        json.addProperty("id", getId());
        json.addProperty("type", getType());
        json.addProperty("elements", getElements());
        return json.getJsonObject();
    }

    public enum Type {
        HUD;

        private final String name;

        Type() {
            this.name = name().toLowerCase(Locale.ROOT);
        }

        public String toString() {
            return name;
        }
    }
}
