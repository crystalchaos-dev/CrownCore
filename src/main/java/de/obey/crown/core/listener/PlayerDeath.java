/* CrownPlugins - CrownCore */
/* 21.08.2024 - 02:38 */

package de.obey.crown.core.listener;

import de.obey.crown.core.Config;
import de.obey.crown.core.Init;
import de.obey.crown.core.handler.LocationHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

@RequiredArgsConstructor
@NonNull
public final class PlayerDeath implements Listener {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    private final Config config;

    @EventHandler
    public void on(final PlayerDeathEvent event) {

        if (config.isInstantRespawn()) {
            Bukkit.getScheduler().runTaskLater(Init.getInstance(), () -> {
                event.getEntity().spigot().respawn();
            }, 2);
        }
    }

    @EventHandler
    public void on(final PlayerRespawnEvent event) {
        if (LocationHandler.getLocation("spawn") == null)
            return;
        
        event.setRespawnLocation(LocationHandler.getLocation("spawn"));
    }
}
