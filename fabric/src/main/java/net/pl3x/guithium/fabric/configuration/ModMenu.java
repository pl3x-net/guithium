package net.pl3x.guithium.fabric.configuration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.pl3x.guithium.fabric.gui.screen.OptionsScreen;
import org.jetbrains.annotations.NotNull;

public class ModMenu implements ModMenuApi {
    @NotNull
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return OptionsScreen::new;
    }
}
