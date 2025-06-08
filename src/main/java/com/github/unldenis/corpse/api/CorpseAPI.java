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

import com.github.unldenis.corpse.corpse.*;
import com.github.unldenis.corpse.manager.*;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import org.apache.commons.lang.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

public class CorpseAPI {

  private static CorpseAPI instance;

  public CorpseAPI() {
    throw new IllegalArgumentException();
  }

  private CorpseAPI(Object dummy) {
  }

  /**
   * Class method that allows you to use the API.
   *
   * @return an instance of this class
   */
  @NotNull
  public static synchronized CorpseAPI getInstance() {
    if (instance == null) {
      instance = new CorpseAPI(null);
    }
    return instance;
  }

  /**
   * Method that creates a corpse in the player's position and with its skin and inventory
   *
   * @return a new Corpse object
   */
  public Corpse spawnCorpse(@NotNull Player player) {
    Validate.notNull(player, "Player cannot be null");
    return new Corpse(player);
  }

  /**
   * Method that creates a corpse in the given place and with the skin, name and inventory of the
   * player
   *
   * @param location The location where to spawn the corpse
   * @return a new Corpse object
   */
  public Corpse spawnCorpse(@NotNull Player player, @NotNull Location location) {
    Validate.notNull(player, "Player cannot be null");
    Validate.notNull(location, "Spawn location cannot be null");
    return new Corpse(location, player, null);
  }

  /**
   * Method that creates a corpse in the given place and with the skin and name of the
   * offlinePlayer
   *
   * @param location      The location where to spawn the corpse
   * @return a new Corpse object
   */
  public Corpse spawnCorpse(@NotNull OfflinePlayer offlinePlayer, @NotNull Location location) {
    Validate.notNull(offlinePlayer, "OfflinePlayer cannot be null");
    Validate.notNull(location, "Spawn location cannot be null");
    return new Corpse(location, offlinePlayer, null);
  }

  /**
   * Method that creates a corpse in the given place and with the skin and name of the player with a
   * custom inventory.
   *
   * @param location   The location where to spawn the corpse
   * @param helmet     The helmet to put on the corpse
   * @param chestPlate The chestPlate to put on the corpse
   * @param leggings   The leggings to put on the corpse
   * @param boots      The boots to put on the corpse
   * @return a new Corpse object
   */
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
    return new Corpse(location, SpigotReflectionUtil.getUserProfile(player),
        new ItemStack[]{boots, leggings, chestPlate, helmet}, player.getName());
  }

  /**
   * Method that creates a corpse in the given place and with the skin and name of the offlinePlayer
   * with a custom inventory.
   *
   * @param location      The location where to spawn the corpse
   * @param helmet        The helmet to put on the corpse
   * @param chestPlate    The chestPlate to put on the corpse
   * @param leggings      The leggings to put on the corpse
   * @param boots         The boots to put on the corpse
   * @return a new Corpse object
   */
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
    return new Corpse(location, offlinePlayer,
        new ItemStack[]{boots, leggings, chestPlate, helmet});
  }

  /**
   * Method that removes a corpse
   *
   * @param corpse The corpse to be removed
   */
  public void removeCorpse(@NotNull Corpse corpse) {
    Validate.notNull(corpse, "Corpse cannot be null");
    CorpsePool.getInstance().remove(corpse.getId());
  }
}
