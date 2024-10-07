package de.obey.crown.core;

import de.obey.crown.core.command.CoreCommand;
import de.obey.crown.core.command.LocationCommand;
import de.obey.crown.core.event.CoreStartEvent;
import de.obey.crown.core.handler.LocationHandler;
import de.obey.crown.core.listener.PlayerChat;
import de.obey.crown.core.listener.PlayerLogin;
import de.obey.crown.core.util.FileUtil;
import de.obey.crown.core.util.Teleporter;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public final class CrownCore extends JavaPlugin {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private boolean coreStarted = false, placeholderapi = false;

    private Config crownConfig;

    @Override
    public void onEnable() {
        // create core data folder
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
            placeholderapi = true;

        // generate core message file with default messages
        FileUtil.getGeneratedCoreFile("messages.yml", true);
        FileUtil.getGeneratedCoreFile("config.yml", true);

        crownConfig = new Config(this);
        load();


        final AtomicInteger counter = new AtomicInteger();
        Bukkit.getScheduler().runTaskTimer(this, (runnable) -> {

            if (counter.get() == 2) {
                crownConfig.getMessanger().loadCorePlaceholders();
                LocationHandler.loadLocations();
                Teleporter.initialize();
            }

            if (counter.get() == 5) {
                coreStarted = true;
                getServer().getPluginManager().callEvent(new CoreStartEvent());
                runnable.cancel();
                return;
            }

            counter.incrementAndGet();
        }, 20, 20);
    }

    @Override
    public void onDisable() {
        LocationHandler.saveLocations();
    }

    private void load() {
        loadListener();
        loadCommand();
    }

    private void loadCommand() {
        final LocationCommand locationCommand = new LocationCommand(crownConfig.getMessanger());
        getCommand("location").setExecutor(locationCommand);
        getCommand("location").setTabCompleter(locationCommand);

        final CoreCommand coreCommand = new CoreCommand(crownConfig.getMessanger(), crownConfig);
        getCommand("crowncore").setExecutor(coreCommand);
        getCommand("crowncore").setTabCompleter(coreCommand);
    }

    private void loadListener() {
        final PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerLogin(this), this);
        pluginManager.registerEvents(new PlayerChat(crownConfig), this);
    }

    public static CrownCore getInstance() {
        return getPlugin(CrownCore.class);
    }
}
