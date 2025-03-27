/* CrownPlugins - CrownCore */
/* 17.08.2024 - 21:54 */

package de.obey.crown.core.listener;

import de.obey.crown.core.CrownCore;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

@RequiredArgsConstructor
@NonNull
public final class PlayerLogin implements Listener {

    private final CrownCore crownCore;

    @EventHandler
    public void on(final AsyncPlayerPreLoginEvent event) {
        if (!crownCore.isCoreStarted()) {
            event.setKickMessage("§c§oꜱᴇʀᴠᴇʀ ɪꜱ ꜱᴛᴀʀᴛɪɴɢ.");
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }
}
