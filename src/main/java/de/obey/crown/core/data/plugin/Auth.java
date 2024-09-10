/* CrownPlugins - Template */

package de.obey.crown.core.data.plugin;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import de.obey.crown.core.Init;
import de.obey.crown.core.util.FileUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public final class Auth {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    private final Plugin plugin;

    private String statusAuthUrl = "", license = "";

    private long lastAuthServerCheck;

    @Getter
    @Setter
    private boolean cracked = true, disable = false, called = false;

    public Auth(final Plugin plugin, final CrownConfig config) {
        this.plugin = plugin;
        this.license = config.getLicenseKey();

        final File file = FileUtil.getFile(Init.getInstance().getDataFolder().getPath(), "config.yml");
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        lastAuthServerCheck = FileUtil.getLong(configuration, "lastAuthServerCheck", 0);
        statusAuthUrl = FileUtil.getString(configuration, "authServer", "");

        getAuthServer();
        check();
        runCheckRunnable();
    }

    private void runCheckRunnable() {
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            check();

            if (disable || !called) {
                Bukkit.getPluginManager().disablePlugin(plugin);
            }

        }, 20 * 60 * 60, 20 * 60 * 60);
    }

    private void getAuthServer() {


        if (System.currentTimeMillis() - lastAuthServerCheck < 1000 * 60 * 60 * 24) {
            statusAuthUrl = statusAuthUrl
                    .replace("%pluginname%", plugin.getName())
                    .replace("%key%", license);

            called = true;
            return;
        }

        try {
            final URL url = new URL("https://raw.githubusercontent.com/Obeeyyyy/secure/main/auth-server");
            final URLConnection connection = url.openConnection();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final StringBuilder builder = new StringBuilder();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            statusAuthUrl = builder.toString();

            if (!statusAuthUrl.equalsIgnoreCase(builder.toString())) {
                cracked = true;
            }

            called = true;

            lastAuthServerCheck = System.currentTimeMillis();

            final File file = FileUtil.getFile(Init.getInstance().getDataFolder().getPath(), "config.yml");
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            configuration.set("lastAuthServerCheck", lastAuthServerCheck);
            configuration.set("authServer", statusAuthUrl);

            FileUtil.saveConfigurationIntoFile(configuration, file);

            statusAuthUrl = statusAuthUrl
                    .replace("%pluginname%", plugin.getName())
                    .replace("%key%", license);

        } catch (final MalformedURLException ignored) {
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void check() {
        try {
            final URL url = new URL(statusAuthUrl);
            final URLConnection connection = url.openConnection();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            final StringBuilder builder = new StringBuilder();

            String responseData = "";
            while ((responseData = bufferedReader.readLine()) != null) {
                builder.append(responseData);
            }

            final JsonObject responseJson = new Gson().fromJson(builder.toString(), JsonObject.class);

            if (!responseJson.has("status")) {
                disablePlugin("invalid response");
                if (!called) {
                    Bukkit.getServer().shutdown();
                }
                return;
            }

            if (!responseJson.get("status").getAsString().equalsIgnoreCase("valid")) {
                if (responseJson.has("reason")) {
                    disablePlugin(responseJson.get("reason").getAsString());
                    if (!called) {
                        Bukkit.getServer().shutdown();
                    }
                    return;
                }

                if (responseJson.has("plugin")) {
                    if (!responseJson.get("plugin").getAsString().equalsIgnoreCase(plugin.getName())) {
                        disablePlugin("invalid license");

                        if (!called) {
                            Bukkit.getServer().shutdown();
                        }

                        return;
                    }

                    disablePlugin("invalid plugin");

                    return;
                }

                disablePlugin("invalid license");

                if (!called) {
                    Bukkit.getServer().shutdown();
                }

                return;
            }
        } catch (final IOException e) {
            Bukkit.getLogger().warning("Could not connect to auth server ...");
            Bukkit.getLogger().warning(" -> " + statusAuthUrl);
            disable = true;
        }
    }

    private void disablePlugin(final String reason) {
        Bukkit.getLogger().warning(plugin.getName() + " - your key '" + license + "' is invalid!");
        Bukkit.getLogger().warning(plugin.getName() + " - reason: '" + reason + "'");
        Bukkit.getLogger().warning(plugin.getName() + " - https://dsc.gg/crownplugins");
        callDisable();
    }

    public void callDisable() {
        disable = true;
        called = true;
    }
}
