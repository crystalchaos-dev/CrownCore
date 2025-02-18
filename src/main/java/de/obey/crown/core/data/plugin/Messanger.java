/* CrownPlugins - CrownCore */
/* 17.08.2024 - 01:29 */

package de.obey.crown.core.data.plugin;

import com.google.common.collect.Maps;
import de.obey.crown.core.CrownCore;
import de.obey.crown.core.util.FileUtil;
import de.obey.crown.core.util.TextUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

@RequiredArgsConstructor
@Getter
@Setter
public final class Messanger {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    private final CrownCore crownCore = CrownCore.getInstance();
    private final boolean placeholderapi = crownCore.isPlaceholderapi();

    @NonNull
    private final Plugin plugin;

    private String prefix, whiteColor, accentColor;
    private final Map<String, String> messages = Maps.newConcurrentMap();
    private final Map<String, String> rawMessages = Maps.newConcurrentMap();
    private final Map<String, ArrayList<String>> multiLineMessages = Maps.newConcurrentMap();

    public String getMessage(final String key) {
        if (!messages.containsKey(key)) {
            final File file = FileUtil.getGeneratedFile(plugin, "messages.yml", true);
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            configuration.set("messages." + key, "");

            FileUtil.saveConfigurationIntoFile(configuration, file);
            return "";
        }

        if (messages.get(key).equalsIgnoreCase(""))
            return "";

        return TextUtil.translateCorePlaceholder(messages.get(key));
    }

    public String getRawMessage(final String key) {
        if (!rawMessages.containsKey(key)) {
            final File file = FileUtil.getGeneratedFile(plugin, "messages.yml", true);
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            configuration.set("messages." + key, "");

            FileUtil.saveConfigurationIntoFile(configuration, file);
            return "";
        }

        if (rawMessages.get(key).equalsIgnoreCase(""))
            return "";

        return TextUtil.translateCorePlaceholder(rawMessages.get(key));
    }

    public String getRawMessage(final String key, final String[] placeholders, final String... replacements) {
        if (!rawMessages.containsKey(key)) {
            final File file = FileUtil.getGeneratedFile(plugin, "messages.yml", true);
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String value = "";

            for (String placeholder : placeholders) {
                value = value + "%" + placeholder + "%";
            }

            configuration.set("messages." + key, value);

            FileUtil.saveConfigurationIntoFile(configuration, file);
            return value;
        }

        if (rawMessages.get(key).equalsIgnoreCase(""))
            return "";

        String message = rawMessages.get(key);

        int count = 0;
        for (final String placeholder : placeholders) {
            message = message.replace("%" + placeholder + "%", replacements[count]);
            count++;
        }

        return TextUtil.translateCorePlaceholder(message);
    }

    public String getMessage(final String key, final String[] placeholders, final String... replacements) {
        if (!messages.containsKey(key)) {
            final File file = FileUtil.getGeneratedFile(plugin, "messages.yml", true);
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String value = "";

            for (String placeholder : placeholders) {
                value = value + "%" + placeholder + "%";
            }

            configuration.set("messages." + key, value);

            FileUtil.saveConfigurationIntoFile(configuration, file);
            return value;
        }

        if (messages.get(key).equalsIgnoreCase(""))
            return "";

        String message = messages.get(key);

        int count = 0;
        for (final String placeholder : placeholders) {
            message = message.replace("%" + placeholder + "%", replacements[count]);
            count++;
        }

        return TextUtil.translateColors(message);
    }

    public String getMessageWithPlaceholderAPI(final Player player, final String key, final String[] placeholders, final String... replacements) {
        if (!messages.containsKey(key)) {
            final File file = FileUtil.getGeneratedFile(plugin, "messages.yml", true);
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String value = "";

            for (String placeholder : placeholders) {
                value = value + "%" + placeholder + "%";
            }

            configuration.set("messages." + key, value);

            FileUtil.saveConfigurationIntoFile(configuration, file);
            return value;
        }

        if (messages.get(key).equalsIgnoreCase(""))
            return "";

        String message = messages.get(key);

        if (placeholders.length > 0) {
            int count = 0;
            for (final String placeholder : placeholders) {
                message = message.replace("%" + placeholder + "%", replacements[count]);
                count++;
            }
        }

        if (placeholderapi)
            message = PlaceholderAPI.setPlaceholders(player, message);

        return TextUtil.translateColors(message);
    }

    public String getMessageWithPlaceholderAPI(final OfflinePlayer player, final String key, final String[] placeholders, final String... replacements) {
        if (!messages.containsKey(key)) {
            final File file = FileUtil.getGeneratedFile(plugin, "messages.yml", true);
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String value = "";

            for (String placeholder : placeholders) {
                value = value + "%" + placeholder + "%";
            }

            configuration.set("messages." + key, value);

            FileUtil.saveConfigurationIntoFile(configuration, file);
            return value;
        }

        if (messages.get(key).equalsIgnoreCase(""))
            return "";

        String message = messages.get(key);

        if (placeholders.length > 0) {
            int count = 0;
            for (final String placeholder : placeholders) {
                message = message.replace("%" + placeholder + "%", replacements[count]);
                count++;
            }
        }

        if (placeholderapi)
            message = PlaceholderAPI.setPlaceholders(player, message);

        return TextUtil.translateColors(message);
    }

    public String getMessageWithPlaceholderAPI(final OfflinePlayer player, final String key) {
        if (!messages.containsKey(key)) {
            final File file = FileUtil.getGeneratedFile(plugin, "messages.yml", true);
            final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

            String value = "";

            configuration.set("messages." + key, value);

            FileUtil.saveConfigurationIntoFile(configuration, file);
            return value;
        }

        if (messages.get(key).equalsIgnoreCase(""))
            return "";

        String message = messages.get(key);

        if (placeholderapi)
            message = PlaceholderAPI.setPlaceholders(player, message);

        return TextUtil.translateColors(message);
    }


    public void sendNonConfigMessage(final CommandSender sender, final String message) {

        String send = message;

        if (sender instanceof Player player)
            send = placeholderapi ? PlaceholderAPI.setPlaceholders(player, send) : send;

        sender.sendMessage(TextUtil.translateColors(send));
    }

    public void sendMessage(final CommandSender sender, final String key) {
        String message = getMessage(key);

        if (message.isEmpty())
            return;

        sender.sendMessage(TextUtil.translateCorePlaceholder(message));
    }

    public void sendMessage(final CommandSender sender, final String key, final String[] placeholders,
                            final String... replacements) {
        String message = getMessage(key, placeholders, replacements);

        if (message.isEmpty())
            return;

        sender.sendMessage(TextUtil.translateCorePlaceholder(message));
    }

    public void broadcastMessagewithPlaceholderAPI(final Player player, final String key,
                                                   final String[] placeholders, final String... replacements) {
        String message = placeholderapi ? PlaceholderAPI.setPlaceholders(player, getMessage(key)) : getMessage(key);
        if (message.isEmpty())
            return;

        int count = 0;
        for (final String placeholder : placeholders) {
            message = message.replace("%" + placeholder + "%", replacements[count]);
            count++;
        }

        Bukkit.broadcastMessage(TextUtil.translateColors(message));
    }

    public void broadcastMessage(final String key, final String[] placeholders, final String... replacements) {
        String message = TextUtil.translateCorePlaceholder(getMessage(key));

        if (message.isEmpty())
            return;

        int count = 0;
        for (final String placeholder : placeholders) {
            message = message.replace("%" + placeholder + "%", replacements[count]);
            count++;
        }

        Bukkit.broadcastMessage(message);
    }

    public void broadcastMessage(final String key) {
        String message = TextUtil.translateCorePlaceholder(getMessage(key));

        if (message.isEmpty())
            return;

        Bukkit.broadcastMessage(message);
    }

    public void loadCorePlaceholders() {
        final File file = FileUtil.getCreatedCoreFile("messages.yml", true);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        prefix = TextUtil.registerCorePlaceholder("%prefix%", FileUtil.getString(configuration, "prefix", "&5&lOBEY &8●&f"));
        whiteColor = TextUtil.registerCorePlaceholder("%white%", FileUtil.getString(configuration, "whiteColor", "&f"));
        accentColor = TextUtil.registerCorePlaceholder("%accent%", FileUtil.getString(configuration, "accentColor", "&5"));
    }

    public void loadPluginPlaceholders(final Plugin plugin) {
        final File pluginFile = FileUtil.getCreatedFile(plugin, "messages.yml", true);
        final YamlConfiguration pluginConfiguration = YamlConfiguration.loadConfiguration(pluginFile);

        if (pluginConfiguration.contains("prefix"))
            prefix = TextUtil.registerCorePlaceholder("%" + plugin.getName().toLowerCase() + "_prefix%", FileUtil.getString(pluginConfiguration, "prefix", "&5&lOBEY &8●&f"));

        if (pluginConfiguration.contains("whiteColor"))
            whiteColor = TextUtil.registerCorePlaceholder("%" + plugin.getName().toLowerCase() + "_white%", FileUtil.getString(pluginConfiguration, "whiteColor", "&f"));

        if (pluginConfiguration.contains("accentColor"))
            accentColor = TextUtil.registerCorePlaceholder("%" + plugin.getName().toLowerCase() + "_accent%", FileUtil.getString(pluginConfiguration, "accentColor", "&5"));

    }

    private void loadDefaultMessages() {
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(FileUtil.getCoreFile("messages.yml"));

        if (!configuration.contains("messages"))
            return;

        for (final String key : configuration.getConfigurationSection("messages").getKeys(false)) {
            final String value = configuration.getString("messages." + key);
            messages.put(key, TextUtil.translateColors(value));
            rawMessages.put(key, value);
        }
    }

    public void loadMessages() {
        final File file = FileUtil.getGeneratedFile(plugin, "messages.yml", true);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        loadDefaultMessages();
        loadMultiLineMessages(configuration);

        if (configuration.contains("messages")) {
            for (final String key : configuration.getConfigurationSection("messages").getKeys(false)) {
                final String value = configuration.getString("messages." + key);
                messages.put(key, TextUtil.translateColors(value));
                rawMessages.put(key, value);
            }
        }
    }

    public ArrayList<String> getMultiLineMessage(final String key) {
        if (!multiLineMessages.containsKey(key))
            return new ArrayList<>();

        final ArrayList<String> lines = multiLineMessages.get(key);
        final ArrayList<String> temp = new ArrayList<>();

        for (final String line : lines) {
            temp.add(TextUtil.translateColors(line));
        }

        return temp;
    }

    public ArrayList<String> getMultiLineMessage(final String key, final String[] placeholders, final String...
            replacements) {
        if (!multiLineMessages.containsKey(key))
            return new ArrayList<>();

        final ArrayList<String> lines = multiLineMessages.get(key);
        final ArrayList<String> temp = new ArrayList<>();

        for (String line : lines) {
            int count = 0;
            for (final String placeholder : placeholders) {
                line = line.replace("%" + placeholder + "%", replacements[count]);
                count++;
            }

            line = TextUtil.translateColors(line);
            temp.add(line);
        }

        return temp;
    }

    public void sendMultiLineMessage(final CommandSender sender, final String key) {
        final ArrayList<String> lines = getMultiLineMessage(key);
        if (lines.isEmpty())
            return;

        for (final String line : lines) {
            sender.sendMessage(PlaceholderAPI.setPlaceholders(null, line));
        }
    }

    public void broadcastMultiLineMessage(final String key) {
        final ArrayList<String> lines = getMultiLineMessage(key);
        if (lines.isEmpty())
            return;

        for (final String line : lines) {
            Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(null, line));
        }
    }

    public void sendMultiLineMessage(final CommandSender sender, final String key, final String[] placeholders,
                                     final String... replacements) {
        final ArrayList<String> lines = getMultiLineMessage(key);
        if (lines.isEmpty())
            return;

        final ArrayList<String> temp = new ArrayList<>();

        for (String line : lines) {
            int count = 0;
            for (final String placeholder : placeholders) {
                line = line.replace("%" + placeholder + "%", replacements[count]);
                count++;
            }

            temp.add(line);
        }

        for (final String translatedLine : temp) {
            sender.sendMessage(PlaceholderAPI.setPlaceholders(null, translatedLine));
        }
    }

    public void broadcastMultiLineMessage(final String key, final String[] placeholders, final String...
            replacements) {
        final ArrayList<String> lines = getMultiLineMessage(key);
        if (lines.isEmpty())
            return;

        final ArrayList<String> temp = new ArrayList<>();

        for (String line : lines) {
            int count = 0;
            for (final String placeholder : placeholders) {
                line = line.replace("%" + placeholder + "%", replacements[count]);
                count++;
            }

            temp.add(line);
        }

        for (final String translatedLine : temp) {
            Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(null, translatedLine));
        }
    }

    public void loadMultiLineMessages(final YamlConfiguration configuration) {
        if (!configuration.contains("multi-line-messages"))
            return;

        for (final String key : configuration.getConfigurationSection("multi-line-messages").getKeys(false)) {
            multiLineMessages.put(key, FileUtil.getStringArrayList(configuration, "multi-line-messages." + key, new ArrayList<>()));
        }
    }

    public void sendCommandSyntax(final CommandSender sender, final String command, final String... lines) {
        if (!messages.containsKey("command-syntax"))
            return;

        final String syntaxPrefix = getMessage("command-syntax-prefix");

        sender.sendMessage("");
        sendMessage(sender, "command-syntax", new String[]{"command"}, command);
        for (final String line : lines) {
            sender.sendMessage(syntaxPrefix + line);
        }
    }

    public boolean hasPermission(final CommandSender sender, final String permission) {
        return hasPermission(sender, permission, true);
    }

    public boolean hasPermission(final CommandSender sender, final String permission, final boolean send) {
        if (sender.hasPermission(permission))
            return true;

        if (send) {
            if (sender instanceof Player player)
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.2f, 1);

            sendMessage(sender, "no-permission", new String[]{"permission"}, permission);
        }

        return false;
    }

    public boolean isKnown(final CommandSender sender, final String name) {
        if (!name.matches("[a-zA-z0-9]{3,16}")) {
            sendMessage(sender, "player-invalid", new String[]{"name"}, name);
            return false;
        }

        final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name);

        if (offlinePlayer.isOnline())
            return true;

        if (!offlinePlayer.hasPlayedBefore()) {
            sendMessage(sender, "player-invalid", new String[]{"name"}, name);
            return false;
        }

        return true;
    }

    public boolean isOnline(final CommandSender sender, final String name) {

        if (!isKnown(sender, name))
            return false;

        final Player target = Bukkit.getPlayer(name);
        if (target == null || !target.isOnline()) {
            sendMessage(sender, "player-offline", new String[]{"name"}, name);
            return false;
        }

        return true;
    }

    public int isValidInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (final NumberFormatException exception) {
            return -2;
        }
    }

    public int isValidInt(final CommandSender sender, final String input) {
        final int number = isValidInt(input);

        if (number < 0) {
            sendMessage(sender, "invalid-number");
        }

        return number;
    }

    public int isValidIntAllowMinusOne(final CommandSender sender, final String input) {
        final int number = isValidInt(input);

        if (number < -1) {
            sendMessage(sender, "invalid-number");
        }

        return number;
    }

    public double isValidDouble(final String input) {
        try {
            return Double.parseDouble(input);
        } catch (final NumberFormatException exception) {
            return -1D;
        }
    }

    public double isValidDouble(final CommandSender sender, final String input) {
        final double number = isValidDouble(input);

        if (number < 0) {
            sendMessage(sender, "invalid-number");
        }

        return number;
    }

}
