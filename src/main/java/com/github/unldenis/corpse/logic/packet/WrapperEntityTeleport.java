package com.github.unldenis.corpse.logic.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.github.unldenis.corpse.util.VersionUtil;
import org.bukkit.Location;
import org.jetbrains.annotations.Nullable;

public class WrapperEntityTeleport implements IPacket {

    private PacketContainer packet;
    private final int id;
    private final Location loc;

    public WrapperEntityTeleport(int entityID, Location location) {
        this.id = entityID;
        this.loc = location;
    }

    @Override
    public void load() {
        packet = new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);
        packet.getIntegers().write(0, this.id);
        if (VersionUtil.isAbove(VersionUtil.VersionEnum.V1_9)) {
            packet.getDoubles().write(0, loc.getX());
            packet.getDoubles().write(1, loc.getY() + 0.15);
            packet.getDoubles().write(2, loc.getZ());
        } else {
            packet.getIntegers().write(1, (int) Math.floor(loc.getX() * 32.0D));
            packet.getIntegers().write(2, (int) Math.floor((loc.getY() + 0.15) * 32.0D));
            packet.getIntegers().write(3, (int) Math.floor(loc.getZ() * 32.0D));
        }
    }

    @Nullable
    @Override
    public PacketContainer get() {
        return this.packet;
    }
}
