/* CrownPlugins - CrownCore */
/* 27.08.2024 - 20:16 */

package de.obey.crown.core.data.player;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Getter
public abstract class CrownDataService<E> {

    final Map<UUID, E> cache;

    public CrownDataService() {
        this.cache = Maps.newConcurrentMap();
    }

    public E get(final UUID uuid) {
        if (cache.containsKey(uuid))
            return cache.get(uuid);

        return null;
    }

    public CompletableFuture<E> loadAsync(final UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {

            if (cache.containsKey(uuid))
                return cache.get(uuid);

            return load(uuid);
        });
    }

    public abstract E load(final UUID uuid);

    public CompletableFuture<E> saveAsync(final UUID uuid) {
        return CompletableFuture.supplyAsync(() -> save(uuid));
    }

    public abstract E save(final UUID uuid);

    public void saveAllCached() {
        if (cache.isEmpty())
            return;

        for (final UUID uuid : cache.keySet()) {
            save(uuid);
        }
    }

}
