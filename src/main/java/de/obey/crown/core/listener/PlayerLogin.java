/* CrownPlugins - CrownCore */
/* 17.08.2024 - 21:54 */

package de.obey.crown.core.listener;

import de.obey.crown.core.Init;
import de.obey.crown.core.handler.LocationHandler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

@RequiredArgsConstructor
@NonNull
public final class PlayerLogin implements Listener {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    private final Init core;

    @EventHandler
    public void on(final AsyncPlayerPreLoginEvent event) {
        if (!core.isCoreStarted()) {

            event.setKickMessage(core.getCrownConfig().getMessanger().getMessage("kick-server-starting"));
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler
    public void on(final PlayerSpawnLocationEvent event) {
        if (!core.isCoreStarted())
            return;

        if (core.getCrownConfig().isTeleportOnJoin())
            event.setSpawnLocation(LocationHandler.getLocation("spawn"));
    }

}
