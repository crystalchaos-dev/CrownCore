/* CrownPlugins - CrownPrestiege */
/* 09.07.2024 - 06:18 */

package de.obey.crown.core.util;

import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

@UtilityClass
@Getter
public final class VaultHook {

    public Economy economy;

    static {
        if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
            setupEconomy();
        }
    }

    public double get(final OfflinePlayer player) {
        if (economy == null || !economy.isEnabled()) {
            return 0;
        }
        return economy.getBalance(player);
    }

    public boolean has(final OfflinePlayer player, final double amount) {
        if (economy == null || !economy.isEnabled()) {
            return false;
        }
        return economy.has(player, amount);
    }

    public void add(final OfflinePlayer player, final double amount) {
        if (economy == null || !economy.isEnabled()) {
            return;
        }
        economy.depositPlayer(player, amount);
    }

    public void remove(final OfflinePlayer player, final double amount) {
        if (economy == null || !economy.isEnabled()) {
            return;
        }
        economy.withdrawPlayer(player, amount);
    }

    private void setupEconomy() {
        final RegisteredServiceProvider<Economy> rsp = Bukkit.getServicesManager().getRegistration(Economy.class);

        if (rsp != null)
            economy = rsp.getProvider();
    }
}