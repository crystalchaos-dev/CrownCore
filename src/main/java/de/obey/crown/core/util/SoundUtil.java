/* CrownPlugins - CrownCore */
/* 25.09.2024 - 19:10 */

package de.obey.crown.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@UtilityClass
public final class SoundUtil {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    public void playSoundToEveryone(final Location location, final Sound sound, final float volume, final float pitch) {
        for (final Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(location, sound, volume, pitch);
        }
    }

}
