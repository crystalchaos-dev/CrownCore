/* CrownPlugins - CrownCore */
/* 27.08.2024 - 22:28 */

package de.obey.crown.core.listener;

import com.google.common.collect.Maps;
import de.obey.crown.core.PluginConfig;
import de.obey.crown.core.util.TextUtil;
import io.papermc.paper.event.player.AsyncChatEvent;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@NonNull
public final class PlayerChat implements Listener {

	private final PluginConfig pluginConfig;

	private final Map<UUID, Long> messageCooldowns = Maps.newConcurrentMap();
	private final Map<UUID, Long> commandCooldowns = Maps.newConcurrentMap();

	@EventHandler
//	public void on(final AsyncPlayerChatEvent event) {
	public void on(final AsyncChatEvent event) {
		if (pluginConfig.getMessageDelay() <= 0)
			return;

		final Player player = event.getPlayer();

		if (player.hasPermission("core.anti.messagedelay"))
			return;

		if (messageCooldowns.containsKey(player.getUniqueId())) {
			final long difference = System.currentTimeMillis() - messageCooldowns.get(player.getUniqueId());
			final long delay = pluginConfig.getMessageDelay();
			if (difference < delay) {
				pluginConfig.getMessanger().sendMessage(player, "message-cooldown", new String[]{"remaining"}, TextUtil.formatTimeString(delay - difference));
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.1f, 0.5f);
				event.setCancelled(true);
				return;
			}
		}

		messageCooldowns.put(player.getUniqueId(), System.currentTimeMillis());
	}

	@EventHandler
	public void on(final PlayerCommandPreprocessEvent event) {
		if (pluginConfig.getCommandDelay() <= 0)
			return;

		final Player player = event.getPlayer();

		if (player.hasPermission("core.anti.commanddelay"))
			return;

		if (commandCooldowns.containsKey(player.getUniqueId())) {
			final long difference = System.currentTimeMillis() - commandCooldowns.get(player.getUniqueId());
			final long delay = pluginConfig.getCommandDelay();
			if (difference < delay) {
				pluginConfig.getMessanger().sendMessage(player, "command-cooldown", new String[]{"remaining"}, TextUtil.formatTimeString(delay - difference));
				player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.1f, 0.5f);
				event.setCancelled(true);
				return;
			}
		}

		commandCooldowns.put(player.getUniqueId(), System.currentTimeMillis());
	}

}
