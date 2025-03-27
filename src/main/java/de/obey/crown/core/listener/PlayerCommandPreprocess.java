/* CrownPlugins - CrownCore */
/* 27.03.2025 - 19:42 */

package de.obey.crown.core.listener;

import de.obey.crown.core.PluginConfig;
import de.obey.crown.core.data.plugin.Messanger;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@RequiredArgsConstructor
@NonNull
public final class PlayerCommandPreprocess implements Listener {

    private final PluginConfig pluginConfig;
    private final Messanger messanger;

    @EventHandler
    public void on(final PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();

        if (event.getMessage().equalsIgnoreCase("/discord")) {
            if (pluginConfig.isDiscordCommand()) {
                messanger.sendMultiLineMessage(player, "discord-message");
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1f, 3f);
                event.setCancelled(true);
                return;
            }
        }

        if (event.getMessage().equalsIgnoreCase("/store")) {
            if (pluginConfig.isStoreCommand()) {
                messanger.sendMultiLineMessage(player, "store-message");
                player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_CHIME, 1f, 3f);
                event.setCancelled(true);
                return;
            }
        }
    }

}
