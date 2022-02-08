package com.github.unldenis.corpse.logic.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Location;

public class WrapperBed implements IPacket {

    private PacketContainer packet;
    private final int id;
    private final Location loc;

    public WrapperBed(int entityID, Location location) {
        this.id = entityID;
        this.loc = location;
    }

    @Override
    public void load() {
        packet = new PacketContainer(PacketType.Play.Server.BED);
        packet.getIntegers().write(0, this.id);
        packet.getBlockPositionModifier().write(0,
                new BlockPosition(loc.getBlockX(), 1, loc.getBlockZ()));
    }

    @Override
    public PacketContainer get() {
        return packet;
    }
}