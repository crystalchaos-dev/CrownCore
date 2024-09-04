package de.obey.crown.core.util.effects;
/*

    Author - Obey -> TraxFight
       16.07.2021 / 18:18

*/

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TeleportEffect {

    private final Particle particle;

    private final int count;
    private BukkitTask runnable;

    public TeleportEffect(final Particle particle, final int count) {

        this.particle = particle;
        this.count = count;
    }

    public void run(final Plugin plugin, final Player player, final int waitBetweenTick, Particle.DustOptions dustOptions) {
        if (runnable == null) {
            runnable = new BukkitRunnable() {
                int ticks = 0;
                int state = 0;
                double angle = 0;
                double yOffset1 = 0;
                double yOffset2 = 2.3;

                @Override
                public void run() {


                    if (player == null || !player.isOnline())
                        return;

                    if (ticks < waitBetweenTick) {
                        ticks++;
                        return;
                    }

                    if (dustOptions == null) {
                        player.getWorld().spawnParticle(particle, player.getLocation().clone().add(Math.cos(angle) * 1.2, yOffset1, Math.sin(angle) * 1.2), count);
                        player.getWorld().spawnParticle(particle, player.getLocation().clone().add(Math.cos(-angle) * 1.2, yOffset1, Math.sin(-angle) * 1.2), count);

                        player.getWorld().spawnParticle(particle, player.getLocation().clone().add(Math.cos(-angle) * 1.2, yOffset2, Math.sin(-angle) * 1.2), count);
                        player.getWorld().spawnParticle(particle, player.getLocation().clone().add(Math.cos(angle) * 1.2, yOffset2, Math.sin(angle) * 1.2), count);
                    } else {
                        player.getWorld().spawnParticle(particle, player.getLocation().clone().add(Math.cos(angle) * 1.2, yOffset1, Math.sin(angle) * 1.2), count, dustOptions);
                        player.getWorld().spawnParticle(particle, player.getLocation().clone().add(Math.cos(-angle) * 1.2, yOffset1, Math.sin(-angle) * 1.2), count, dustOptions);

                        player.getWorld().spawnParticle(particle, player.getLocation().clone().add(Math.cos(-angle) * 1.2, yOffset2, Math.sin(-angle) * 1.2), count, dustOptions);
                        player.getWorld().spawnParticle(particle, player.getLocation().clone().add(Math.cos(angle) * 1.2, yOffset2, Math.sin(angle) * 1.2), count, dustOptions);
                    }

                    if (yOffset1 > 2.3)
                        state = 1;

                    if (yOffset1 <= 0)
                        state = 0;

                    if (state == 0) {
                        yOffset1 += 0.2;
                        yOffset2 -= 0.2;
                    } else {
                        yOffset1 -= 0.2;
                        yOffset2 += 0.2;
                    }

                    angle += 0.25;
                    ticks = 0;
                }
            }.runTaskTimer(plugin, 0L, 1L);

        }
    }

    public void stop() {
        if (runnable == null)
            return;

        runnable.cancel();
    }
}
