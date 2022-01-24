/*
 * Corpse - Dead bodies in bukkit
 * Copyright (C) unldenis <https://github.com/unldenis>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.unldenis.corpse.api;

import com.comphenix.protocol.wrappers.*;
import com.github.unldenis.corpse.logic.*;
import com.github.unldenis.corpse.manager.*;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class CorpseAPI {

    private static CorpseAPI instance;

    private CorpseAPI() {}

    public Corpse spawnCorpse(@NotNull Player player) {
        Validate.notNull(player, "Player cannot be null");
        return new Corpse(player);
    }

    public Corpse spawnCorpse(@NotNull Player player, @NotNull Location location) {
        Validate.notNull(player, "Player cannot be null");
        Validate.notNull(location, "Spawn location cannot be null");
        return new Corpse(location, WrappedGameProfile.fromPlayer(player), null, player.getName());
    }


    public Corpse spawnCorpse(@NotNull OfflinePlayer offlinePlayer, @NotNull Location location) {
        Validate.notNull(offlinePlayer, "OfflinePlayer cannot be null");
        Validate.notNull(location, "Spawn location cannot be null");
        return new Corpse(location, offlinePlayer, null);
    }

    public Corpse spawnCorpse(
            @NotNull Player player,
            @NotNull Location location,
            @Nullable ItemStack helmet,
            @Nullable ItemStack chestPlate,
            @Nullable ItemStack leggings,
            @Nullable ItemStack boots
    ) {
        Validate.notNull(player, "Player cannot be null");
        Validate.notNull(location, "Spawn location cannot be null");
        return new Corpse(location, WrappedGameProfile.fromPlayer(player), new ItemStack[]{boots, leggings, chestPlate, helmet}, player.getName());
    }

    public Corpse spawnCorpse(
            @NotNull OfflinePlayer offlinePlayer,
            @NotNull Location location,
            @Nullable ItemStack helmet,
            @Nullable ItemStack chestPlate,
            @Nullable ItemStack leggings,
            @Nullable ItemStack boots
    ) {
        Validate.notNull(offlinePlayer, "OfflinePlayer cannot be null");
        Validate.notNull(location, "Spawn location cannot be null");
        return new Corpse(location, offlinePlayer, new ItemStack[]{boots, leggings, chestPlate, helmet});
    }

    public void removeCorpse(@NotNull Corpse corpse) {
        Validate.notNull(corpse, "Corpse cannot be null");
        CorpsePool.getInstance().remove(corpse.getId());
    }

    @NotNull
    public static synchronized CorpseAPI getInstance() {
        if(instance == null) {
            instance = new CorpseAPI();
        }
        return instance;
    }
}
