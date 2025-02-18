/* CrownPlugins - CrownCore */
/* 06.10.2024 - 00:12 */

package de.obey.crown.core.command;

import de.obey.crown.core.Config;
import de.obey.crown.core.CrownCore;
import de.obey.crown.core.data.plugin.Messanger;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@NonNull
public final class CoreCommand implements CommandExecutor, TabCompleter {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    private final Messanger messanger;
    private final Config config;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 0) {
            messanger.sendNonConfigMessage(sender, "%prefix% Running %accent%CrownCore %white%version %accent%&o" + CrownCore.getInstance().getDescription().getVersion() + "%white% by %accent%@Obeeyyyy%white%.");
            return false;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (!messanger.hasPermission(sender, "command.core.reload"))
                    return false;

                config.loadMessages();
                config.loadConfig();
                messanger.loadCorePlaceholders();
                messanger.sendMessage(sender, "plugin-reloaded", new String[]{"plugin"}, CrownCore.getInstance().getName());

                return false;
            }

            if (args[0].equalsIgnoreCase("reloadmessages")) {
                if (!messanger.hasPermission(sender, "command.core.reload"))
                    return false;

                messanger.loadCorePlaceholders();
                config.loadMessages();
                messanger.sendMessage(sender, "messages-reloaded", new String[]{"plugin"}, CrownCore.getInstance().getName());

                return false;
            }

            if (args[0].equalsIgnoreCase("reloadconfig")) {
                if (!messanger.hasPermission(sender, "command.core.reload"))
                    return false;

                config.loadConfig();
                messanger.sendMessage(sender, "config-reloaded", new String[]{"plugin"}, CrownCore.getInstance().getName());

                return false;
            }
        }

        messanger.sendCommandSyntax(sender, "/core",
                "/core reload : Reloads config and messages.",
                "/core reloadmessages : Reloads messages.",
                "/core reloadconfig : Reloads config.");

        return false;
    }

    @Override
    public List<String> onTabComplete(final CommandSender sender, Command command, String s, String[] args) {

        final ArrayList<String> list = new ArrayList<>();

        if (!(sender instanceof Player player))
            return list;

        if (args.length == 1) {
            if (sender.hasPermission("command.core.reload")) {
                list.add("reload");
                list.add("reloadmessages");
                list.add("reloadconfig");
            }
        }

        final String argument = args[args.length - 1];
        if (!argument.isEmpty())
            list.removeIf(value -> !value.toLowerCase().startsWith(argument.toLowerCase()));

        Collections.sort(list);

        return list;
    }

}
