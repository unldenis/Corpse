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

import java.util.*;

public class WrapperEntityDestroy implements IPacket {

  private final int id;
  private PacketContainer packet;

  public WrapperEntityDestroy(int entityID) {
    this.id = entityID;
  }

  @Override
  public void load() {
    packet = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
    if (VersionUtil.isBelow(VersionUtil.VersionEnum.V1_16)) {
      packet.getIntegerArrays().write(0, new int[]{this.id});
    } else {
      packet.getIntLists().write(0, Collections.singletonList(this.id));
    }
  }

  @Override
  public PacketContainer get() {
    return packet;
  }
}
