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

package com.github.unldenis.corpse.logic;

import com.github.unldenis.corpse.logic.packet.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.concurrent.*;


public class PacketLoader {

    private final Collection<IPacket> packets = new CopyOnWriteArraySet<>();
    private final WrapperEntityDestroy wrapperEntityDestroy;
    private final WrapperEntityEquipment wrapperEntityEquipment;
    private final WrapperEntityMetadata wrapperEntityMetadata;
    private final WrapperNamedEntitySpawn wrapperNamedEntitySpawn;
    private final WrapperPlayerInfo wrapperPlayerInfoAdd;
    private final WrapperPlayerInfo wrapperPlayerInfoRemove;

    public PacketLoader(@NotNull Corpse corpse) {
        //init packets
        this.wrapperEntityDestroy = new WrapperEntityDestroy(corpse.id);
        this.wrapperEntityEquipment = new WrapperEntityEquipment(corpse.id, corpse.armorContents);
        this.wrapperEntityMetadata = new WrapperEntityMetadata(corpse.id);
        this.wrapperNamedEntitySpawn = new WrapperNamedEntitySpawn(corpse.id, corpse.uuid, corpse.location);
        this.wrapperPlayerInfoAdd = new WrapperPlayerInfo(true, corpse.profile, corpse.name);
        this.wrapperPlayerInfoRemove = new WrapperPlayerInfo(false, corpse.profile, corpse.name);
    }

    public void load() {
        //load packets
        packets.add(wrapperEntityDestroy);
        packets.add(wrapperEntityEquipment);
        packets.add(wrapperEntityMetadata);
        packets.add(wrapperNamedEntitySpawn);
        packets.add(wrapperPlayerInfoAdd);
        packets.add(wrapperPlayerInfoRemove);
        this.packets.forEach(IPacket::load);
    }

    public WrapperEntityDestroy getWrapperEntityDestroy() {
        return wrapperEntityDestroy;
    }

    public WrapperEntityEquipment getWrapperEntityEquipment() {
        return wrapperEntityEquipment;
    }

    public WrapperEntityMetadata getWrapperEntityMetadata() {
        return wrapperEntityMetadata;
    }

    public WrapperNamedEntitySpawn getWrapperNamedEntitySpawn() {
        return wrapperNamedEntitySpawn;
    }

    public WrapperPlayerInfo getWrapperPlayerInfoAdd() {
        return wrapperPlayerInfoAdd;
    }

    public WrapperPlayerInfo getWrapperPlayerInfoRemove() {
        return wrapperPlayerInfoRemove;
    }
}
