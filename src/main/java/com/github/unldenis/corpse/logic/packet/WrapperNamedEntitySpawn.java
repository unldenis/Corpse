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

package com.github.unldenis.corpse.logic.packet;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import com.github.unldenis.corpse.util.*;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.*;

import java.util.*;

public class WrapperNamedEntitySpawn implements IPacket {

  private final int id;
  private final UUID uuid;
  private final Location location;
  private PacketContainer packet;

  public WrapperNamedEntitySpawn(int entityID, @NotNull UUID uuid, @NotNull Location location) {
    this.id = entityID;
    this.uuid = uuid;
    this.location = location;
  }

  @Override
  public void load() {
    if (VersionUtil.isBelow(VersionUtil.VersionEnum.V1_20_R1)) {
        packet = ProtocolLibrary.getProtocolManager()
            .createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
    } else {
        packet = ProtocolLibrary.getProtocolManager()
            .createPacket(PacketType.Play.Server.SPAWN_ENTITY);
    }

        /*
            Unknown reason
            1.12.2 client packet error caused by this
            packet = new PacketContainer(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
        */

    if (VersionUtil.isCompatible(VersionUtil.VersionEnum.V1_8)) {
        packet.getModifier().writeDefaults();
        packet.getIntegers().
            write(0, this.id).
            write(1, (int) (this.location.getX() * 32)).
            write(2, (int) (this.location.getY() * 32)).
            write(3, (int) (this.location.getZ() * 32));

        packet.getUUIDs()
            .write(0, this.uuid);
    } else {
      packet.getIntegers()
          .write(0, this.id);
      packet.getUUIDs()
          .write(0, this.uuid);
      packet.getDoubles()
          .write(0, this.location.getX())
          .write(1, this.location.getY())
          .write(2, this.location.getZ());
      packet.getBytes()
          .write(0, (byte) (this.location.getYaw() * 256.0F / 360.0F))
          .write(1, (byte) (this.location.getPitch() * 256.0F / 360.0F));
      if (VersionUtil.isAbove(VersionUtil.VersionEnum.V1_20_R2)) {
          packet.getEntityTypeModifier()
              .write(0, EntityType.PLAYER);
      }
    }

  }

  @Nullable
  @Override
  public PacketContainer get() {
    return packet;
  }


}
