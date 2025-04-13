package de.obey.crown.core;

import com.xyrisdev.library.scheduler.XScheduler;
import com.xyrisdev.library.scheduler.scheduling.schedulers.TaskScheduler;
import de.obey.crown.core.command.CoreCommand;
import de.obey.crown.core.command.LocationCommand;
import de.obey.crown.core.event.CoreStartEvent;
import de.obey.crown.core.handler.LocationHandler;
import de.obey.crown.core.listener.PlayerChat;
import de.obey.crown.core.listener.PlayerCommandPreprocess;
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

    private PluginConfig pluginConfig;

    // Folia support - being
    private static TaskScheduler scheduler;
    // Folia support - end

    @Override
    public void onEnable() {
        // Folia support - being
        scheduler = XScheduler.of(this);
        // Folia support - end

        // create core data folder
        if (!getDataFolder().exists())
            getDataFolder().mkdir();

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
            placeholderapi = true;

        // generate core message file with default messages
        FileUtil.getGeneratedCoreFile("messages.yml", true);
        FileUtil.getGeneratedCoreFile("config.yml", true);

        pluginConfig = new PluginConfig(this);
        new Placeholders().register();

//        What the fuck is this??
//        final AtomicInteger counter = new AtomicInteger();
//        Bukkit.getScheduler().runTaskTimer(this, (runnable) -> {
//
//            if (counter.get() == 2) {
//                pluginConfig.getMessanger().loadCorePlaceholders();
//                LocationHandler.loadLocations();
//                Teleporter.initialize();
//            }
//
//            if (counter.get() == 5) {
//                coreStarted = true;
//                getServer().getPluginManager().callEvent(new CoreStartEvent());
//                load();
//                runnable.cancel();
//                return;
//            }
//
//            counter.incrementAndGet();
//        }, 20, 20);


        pluginConfig.getMessanger().loadCorePlaceholders();
        LocationHandler.loadLocations();
        Teleporter.initialize();

        coreStarted = true;
        Bukkit.getPluginManager().callEvent(new CoreStartEvent());
        load();
    }

    @Override
    public void onDisable() {
        LocationHandler.saveLocations();
    }

    public void load() {
        loadListener();
        loadCommand();
    }

    private void loadCommand() {
        final LocationCommand locationCommand = new LocationCommand(pluginConfig.getMessanger());
        getCommand("location").setExecutor(locationCommand);
        getCommand("location").setTabCompleter(locationCommand);

        final CoreCommand coreCommand = new CoreCommand(pluginConfig.getMessanger(), pluginConfig);
        getCommand("crowncore").setExecutor(coreCommand);
        getCommand("crowncore").setTabCompleter(coreCommand);
    }

    private void loadListener() {
        final PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new PlayerLogin(this), this);
        pluginManager.registerEvents(new PlayerChat(pluginConfig), this);
        pluginManager.registerEvents(new PlayerCommandPreprocess(pluginConfig, pluginConfig.getMessanger()), this);
    }

    public static CrownCore getInstance() {
        return getPlugin(CrownCore.class);
    }

    // Folia support - begin
    public static TaskScheduler scheduler() {
        return scheduler;
    }
    // Folia support - end
}
