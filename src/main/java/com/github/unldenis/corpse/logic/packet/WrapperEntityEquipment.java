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
import org.bukkit.inventory.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class WrapperEntityEquipment implements IPacket {

    private PacketContainer[] packetContainers;

    private final int id;
    private final ItemStack[] armorContents;

    public WrapperEntityEquipment(int entityID, @Nullable ItemStack[] armorContents) {
        this.id = entityID;
        this.armorContents = armorContents;
    }

    @Override
    public void load() {
        if(packetContainers != null) {
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
            packet.getIntegers().write(0, this.id);
            if(VersionUtil.isCompatible(VersionUtil.VersionEnum.V1_8))  {
                packetContainers = new PacketContainer[4];
                ItemStack temp;
                for(int j=0; j<this.armorContents.length; j++) {
                    temp = this.armorContents[j];
                    if(temp != null) {
                        PacketContainer cloned = packet.deepClone();
                        cloned.getIntegers().write(1, j+1);
                        cloned.getItemModifier().write(0, this.armorContents[j]);
                        packetContainers[j] = cloned;
                    }
                }
                //remove null packets
                packetContainers = Arrays.stream(packetContainers).filter(Objects::nonNull).toArray(PacketContainer[]::new);

            } else {
                packetContainers = new PacketContainer[1];
                List<Pair<EnumWrappers.ItemSlot, ItemStack>> pairList = new ArrayList<>();
                if(this.armorContents[3] != null) {
                    pairList.add(new Pair<>(EnumWrappers.ItemSlot.HEAD, this.armorContents[3]));
                }
                if(this.armorContents[2] != null) {
                    pairList.add(new Pair<>(EnumWrappers.ItemSlot.CHEST, this.armorContents[2]));
                }
                if(this.armorContents[1] != null) {
                    pairList.add(new Pair<>(EnumWrappers.ItemSlot.LEGS, this.armorContents[1]));
                }
                if(this.armorContents[0] != null) {
                    pairList.add(new Pair<>(EnumWrappers.ItemSlot.FEET, this.armorContents[0]));
                }
                packet.getSlotStackPairLists().write(0, pairList);
                packetContainers[0] = packet;
            }
        } else {
            packetContainers = new PacketContainer[0];
        }
    }

    @Override
    public PacketContainer get() {
        return null;
    }


    @NotNull
    @Override
    public PacketContainer[] getMore() {
        return this.packetContainers;
    }
}
