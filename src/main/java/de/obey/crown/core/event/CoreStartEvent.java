/* CrownPlugins - CrownCore */
/* 18.08.2024 - 00:18 */

package de.obey.crown.core.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class CoreStartEvent extends Event {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
