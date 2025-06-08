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

package com.github.unldenis.corpse;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.settings.PacketEventsSettings;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientInteractEntity;
import com.github.unldenis.corpse.command.*;
import com.github.unldenis.corpse.data.*;
import com.github.unldenis.corpse.corpse.*;
import com.github.unldenis.corpse.event.AsyncCorpseInteractEvent;
import com.github.unldenis.corpse.manager.*;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.*;
import org.bukkit.scheduler.*;
import org.jetbrains.annotations.*;

import java.util.Optional;


public class CorpsePlugin extends JavaPlugin {

  private static CorpsePlugin instance;

  private DataManager configYml;
  private CorpsePool pool;

  @NotNull
  public static CorpsePlugin getInstance() {
    return instance;
  }

  @Override
  public void onLoad() {
    //Initialize PacketEvents API
    PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this, new PacketEventsSettings().checkForUpdates(false)));
    //On Bukkit, calling this here is essential, hence the name "load"
    PacketEvents.getAPI().load();

    /* We will register our packet listeners in the onLoad method */
    PacketEvents.getAPI().getEventManager().registerListener(new PacketListener() {
      @Override
      public void onPacketReceive(PacketReceiveEvent event) {
        if(event.getPacketType() != PacketType.Play.Client.INTERACT_ENTITY) {
          return;
        }

        Player player = event.getPlayer();
        if (player == null) {
          return;
        }

        if(pool == null) {
          // If the pool is not initialized, we cannot proceed
          return;
        }

        // Create a packet wrapper for the received packet
        WrapperPlayClientInteractEntity packet = new WrapperPlayClientInteractEntity(event);

        // Check if the interaction is with a corpse
        Corpse corpse = pool.getCorpse(packet.getEntityId()).orElse(null);
        if(corpse == null) {
          return;
        }

        // Call the custom event asynchronously
        Bukkit.getPluginManager().callEvent(new AsyncCorpseInteractEvent(player, corpse, packet.getAction()));
      }
    }, PacketListenerPriority.NORMAL);
  }

  @Override
  public void onEnable() {
    //Initialize!
    PacketEvents.getAPI().init();

    CorpsePlugin.instance = this;

    //register commands
    this.getCommand("spawncorpse").setExecutor(new SpawnCorpseCommand());
    this.getCommand("removecorpse").setExecutor(new RemoveCorpseCommand());

    //load config
    configYml = new DataManager(this, "config.yml");

    //load instance
    pool = CorpsePool.getInstance();
  }

  @Override
  public void onDisable() {
    BukkitTask task = pool.getTickTask();
    if (task != null) {
      task.cancel();
    }
    for (Corpse c : pool.getCorpses()) {
      c.getSeeingPlayers()
          .forEach(c::hide);
    }

    //Terminate the instance (clean up process)
    PacketEvents.getAPI().terminate();
  }

  @NotNull
  public FileConfiguration getConfigYml() {
    return configYml.getConfig();
  }
}
