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
import com.comphenix.protocol.wrappers.*;
import com.github.unldenis.corpse.util.*;

public class WrapperEntityMetadata implements IPacket {

  private final int id;
  private PacketContainer packet;

  public WrapperEntityMetadata(int entityID) {
    this.id = entityID;
  }

  @Override
  public void load() {
    packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
    packet.getIntegers().write(0, this.id);
    WrappedDataWatcher watcher = new WrappedDataWatcher();

    if (VersionUtil.isAbove(VersionUtil.VersionEnum.V1_13)) {
      WrappedDataWatcher.WrappedDataWatcherObject visible = new WrappedDataWatcher.WrappedDataWatcherObject(
          6, WrappedDataWatcher.Registry.get(EnumWrappers.getEntityPoseClass()));
      watcher.setObject(visible, EnumWrappers.EntityPose.SLEEPING.toNms());
            /*
            //Works and set location a little higher
            WrappedDataWatcher.WrappedDataWatcherObject bed = new WrappedDataWatcher.WrappedDataWatcherObject(14, WrappedDataWatcher.Registry.getBlockPositionSerializer(true));
            watcher.setObject(bed, Optional.of(BlockPosition.getConverter().getGeneric(new BlockPosition(corpse.location.toVector()))));
             */
      int indexSkinLayer = VersionUtil.isAbove(VersionUtil.VersionEnum.V1_17) ? 17 : 16;
      WrappedDataWatcher.WrappedDataWatcherObject skinLayers = new WrappedDataWatcher.WrappedDataWatcherObject(
          indexSkinLayer, WrappedDataWatcher.Registry.get(Byte.class));
      watcher.setObject(skinLayers, (byte) (0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40 | 0x80));
    } else {
      watcher.setObject(10, (byte) (0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40 | 0x80));
    }
    packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
  }

  @Override
  public PacketContainer get() {
    return packet;
  }
}
