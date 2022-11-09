package net.pl3x.servergui.api.gui;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GuiManager {
    private final Map<String, Gui> guis = new ConcurrentHashMap<>();

    public void add(Gui gui) {
        if (gui == null || gui.getId() == null) {
            return;
            //throw new RuntimeException("Could not load GUI");
        }
        this.guis.put(gui.getId(), gui);
    }

    public Gui get(String id) {
        return this.guis.get(id);
    }

    public void remove(String id) {
        this.guis.remove(id);
        // todo remove from all players
    }

    public Collection<Gui> getAll() {
        return Collections.unmodifiableCollection(this.guis.values());
    }
}
