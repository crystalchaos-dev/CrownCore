/* CrownPlugins - CrownCore */
/* 21.03.2025 - 17:27 */

package de.obey.crown.core.command;

import de.obey.crown.core.data.plugin.Messanger;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@NonNull
public final class InfoCommands implements CommandExecutor {

    private final Messanger messanger;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (command.getName().equalsIgnoreCase("discord")) {
            messanger.sendMultiLineMessage(sender, "discord-message");

            if (sender instanceof Player player)
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.5f, 3f);

            return false;
        }

        if (command.getName().equalsIgnoreCase("store")) {
            messanger.sendMultiLineMessage(sender, "store-message");

            if (sender instanceof Player player)
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 0.5f, 3f);

            return false;
        }

        return false;
    }
}
