/* CrownPlugins - CrownCore */
/* 18.08.2024 - 00:20 */

package de.obey.crown.core.data.plugin;

import de.obey.crown.core.CrownCore;
import de.obey.crown.core.util.FileUtil;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;

@Getter
public class CrownConfig implements CrowPlugin {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    private final CrownCore crownCore = CrownCore.getInstance();
    @NonNull
    private final Plugin plugin;

    private Messanger messanger;

    private File messageFile, configFile;

    public CrownConfig(@NonNull Plugin plugin) {
        this.plugin = plugin;

        if (Bukkit.getPluginManager().getPlugin("CrownCore") == null) {
            Bukkit.getLogger().warning("[!] " + plugin.getName() + " depends on the CrownCore, please install the CrownCore!");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }

        createFiles();

        messanger = new Messanger(plugin);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            loadConfig();
            loadMessages();
        }, 5);
    }

    @Override
    public void createFiles() {
        plugin.getDataFolder().mkdir();
        messageFile = FileUtil.getGeneratedFile(plugin, "messages.yml", true);
        configFile = FileUtil.getGeneratedFile(plugin, "config.yml", true);
    }

    @Override
    public void loadMessages() {
        messanger.loadPluginPlaceholders(plugin);
        messanger.loadMessages();
    }

    public void loadConfig() {
    }

    public void saveConfig() {
    }
}
