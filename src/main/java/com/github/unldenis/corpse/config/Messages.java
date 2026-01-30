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

package com.github.unldenis.corpse.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Messages {

  private final FileConfiguration config;

  // Parsed once at load
  private final String noPermission;
  private final String playersOnly;
  private final String corpseCreated;
  private final String spawncorpseUsage;
  private final String corpsesDeleted;
  private final String radiusNotNumber;
  private final String removecorpseUsage;
  private final String corpseAlreadyLooted;
  private final String lootInventoryTitle;

  public Messages(@NotNull DataManager messagesData) {
    this.config = messagesData.getConfig();
    this.noPermission = color(config.getString("no-permission", "&cYou don't have permission to use this command."));
    this.playersOnly = color(config.getString("players-only", "&cOnly players can run this command."));
    this.corpseCreated = color(config.getString("corpse-created", "&aCorpse created"));
    this.spawncorpseUsage = color(config.getString("spawncorpse-usage", "&c/spawncorpse [Player] - Spawns a corpse..."));
    this.corpsesDeleted = color(config.getString("corpses-deleted", "&a({amount}) Corpses deleted"));
    this.radiusNotNumber = color(config.getString("radius-not-number", "&cRadius must be a number"));
    this.removecorpseUsage = color(config.getString("removecorpse-usage", "&c/removecorpse [radius] - Removes any corpse(s)..."));
    this.corpseAlreadyLooted = color(config.getString("corpse-already-looted", "&cCorpse already looted"));
    this.lootInventoryTitle = color(config.getString("loot-inventory-title", "&6Loot"));
  }

  private static String color(String s) {
    return s == null ? "" : ChatColor.translateAlternateColorCodes('&', s);
  }

  @NotNull
  public String getNoPermission() {
    return noPermission;
  }

  @NotNull
  public String getPlayersOnly() {
    return playersOnly;
  }

  @NotNull
  public String getCorpseCreated() {
    return corpseCreated;
  }

  @NotNull
  public String getSpawncorpseUsage() {
    return spawncorpseUsage;
  }

  @NotNull
  public String getCorpsesDeleted(int amount) {
    return corpsesDeleted.replace("{amount}", String.valueOf(amount));
  }

  @NotNull
  public String getRadiusNotNumber() {
    return radiusNotNumber;
  }

  @NotNull
  public String getRemovecorpseUsage() {
    return removecorpseUsage;
  }

  @NotNull
  public String getCorpseAlreadyLooted() {
    return corpseAlreadyLooted;
  }

  @NotNull
  public String getLootInventoryTitle() {
    return lootInventoryTitle;
  }
}
