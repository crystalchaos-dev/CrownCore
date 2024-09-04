/* CrownPlugins - CrownCore */
/* 29.08.2024 - 20:41 */

package de.obey.crown.core.util;

import lombok.Getter;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

@Getter
public final class CrownHolder implements InventoryHolder {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    private final String data;

    public CrownHolder(String data) {
        this.data = data;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
