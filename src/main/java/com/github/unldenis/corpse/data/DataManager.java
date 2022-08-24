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

package com.github.unldenis.corpse.data;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.*;
import org.jetbrains.annotations.*;

import java.io.*;
import java.util.logging.Level;

public class DataManager {

  private Plugin plugin;
  private FileConfiguration dataConfig;
  private File configFile;
  private String nameFile;

  public DataManager(@NotNull Plugin plugin, @NotNull String nameFile) {
    this.dataConfig = null;
    this.configFile = null;
    this.plugin = plugin;
    this.nameFile = nameFile;
    this.saveDefaultConfig();
  }

  public void reloadConfig() {
    if (this.configFile == null) {
      this.configFile = new File(this.plugin.getDataFolder(), this.nameFile);
    }
    this.dataConfig = (FileConfiguration) YamlConfiguration.loadConfiguration(this.configFile);
    final InputStream defaultStream = this.plugin.getResource(this.nameFile);
    if (defaultStream != null) {
      final YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(
          (Reader) new InputStreamReader(defaultStream));
      this.dataConfig.setDefaults((Configuration) defaultConfig);
    }
  }

  public FileConfiguration getConfig() {
    if (this.dataConfig == null) {
      this.reloadConfig();
    }
    return this.dataConfig;
  }

  public void saveConfig() {
    if (this.dataConfig == null || this.configFile == null) {
      return;
    }
    try {
      this.getConfig().save(this.configFile);
    } catch (IOException e) {
      this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
    }
  }

  public void saveDefaultConfig() {
    if (this.configFile == null) {
      this.configFile = new File(this.plugin.getDataFolder(), this.nameFile);
    }
    if (!this.configFile.exists()) {
      this.plugin.saveResource(this.nameFile, false);
    }
  }
}