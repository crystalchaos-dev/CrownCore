/* CrownPlugins - CrownCore */
/* 21.08.2024 - 01:33 */

package de.obey.crown.core.handler;

import com.google.common.collect.Maps;
import de.obey.crown.core.Init;
import de.obey.crown.core.util.FileUtil;
import de.obey.crown.core.util.TextUtil;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Map;

@UtilityClass
public final class LocationHandler {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    @Getter
    private final Map<String, Location> locations = Maps.newConcurrentMap();

    public Location getLocation(final String name) {
        return locations.get(name);
    }

    public void setLocation(final String name, final Location location) {
        locations.put(name, location);
    }

    public void deleteLocation(final String name) {
        locations.remove(name);
    }

    public void loadLocations() {
        final File file = FileUtil.getFile(Init.getInstance().getDataFolder().getPath() + "/", "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if (!configuration.contains("locations"))
            return;

        for (final String name : configuration.getConfigurationSection("locations").getKeys(false)) {
            locations.put(name, TextUtil.parseStringToLocation(configuration.getString("locations." + name)));
        }

    }

    public void saveLocations() {
        final File file = FileUtil.getFile(Init.getInstance().getDataFolder().getPath() + "/", "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        if (locations.isEmpty())
            return;

        configuration.set("locations", null);

        for (final String name : locations.keySet()) {
            configuration.set("locations." + name, TextUtil.parseLocationToString(locations.get(name)));
        }

        FileUtil.saveConfigurationIntoFile(configuration, file);
    }
}
