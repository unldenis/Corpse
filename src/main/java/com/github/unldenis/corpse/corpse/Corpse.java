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

package com.github.unldenis.corpse.corpse;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.pose.EntityPose;
import com.github.retrooper.packetevents.protocol.player.TextureProperty;
import com.github.retrooper.packetevents.protocol.player.UserProfile;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerTeams;
import com.github.unldenis.corpse.CorpsePlugin;
import com.github.unldenis.corpse.manager.CorpsePool;
import com.github.unldenis.corpse.util.BedUtil;
import com.github.unldenis.corpse.util.ProfileUtils;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public class Corpse {

    protected final int id;
    protected final Location location;
    protected final UserProfile profile;
    private final Collection<Player> seeingPlayers = new CopyOnWriteArraySet<>();
    private final CorpsePool pool;

    private CorpseNPC internalNPC;
    private boolean hasArmor = false;

    @ApiStatus.Internal
    public Corpse(
            @NotNull Location location,
            @NotNull List<TextureProperty> textures,
            @Nullable ItemStack[] armorContents,
            @Nullable String name
    ) {
        pool = CorpsePool.getInstance();

        this.id = pool.getFreeEntityId();
        this.location = location;
        this.profile = new UserProfile(UUID.randomUUID(), name != null ? name : ProfileUtils.randomName(), textures);

        internalNPC = new CorpseNPC(profile, id,
                pool.isShowTags() ? null : WrapperPlayServerTeams.NameTagVisibility.NEVER);

        internalNPC.setLocation(SpigotConversionUtil.fromBukkitLocation(location));
        if (pool.isRenderArmor() && armorContents != null) {
            if (armorContents[0] != null)
                internalNPC.setBoots(SpigotConversionUtil.fromBukkitItemStack(armorContents[0]));
            if (armorContents[1] != null)
                internalNPC.setLeggings(SpigotConversionUtil.fromBukkitItemStack(armorContents[1]));
            if (armorContents[2] != null)
                internalNPC.setChestplate(SpigotConversionUtil.fromBukkitItemStack(armorContents[2]));
            if (armorContents[3] != null)
                internalNPC.setHelmet(SpigotConversionUtil.fromBukkitItemStack(armorContents[3]));

            hasArmor = true;
        }

        //pool take care
        pool.takeCareOf(this);

        //remove eventually corpse after X seconds
        int time = pool.getTimeRemove();
        if (time > -1) {
            Bukkit.getScheduler()
                    .runTaskLaterAsynchronously(CorpsePlugin.getInstance(), () -> pool.remove(this.id),
                            20L * time);
        }

    }

    public Corpse(@NotNull Player player) {
        this(player.getLocation(), SpigotReflectionUtil.getUserProfile(player), player.getInventory().getArmorContents(), player.getName());
    }

    public Corpse(
            @NotNull Location location,
            @NotNull OfflinePlayer offlinePlayer,
            @Nullable ItemStack[] armorContents
    ) {
        this(location, new ArrayList<>(), armorContents, offlinePlayer.getName());
    }

    @ApiStatus.Internal
    public void show(@NotNull Player player) {
        this.seeingPlayers.add(player);

        Object channel = PacketEvents.getAPI().getProtocolManager().getChannel(player.getUniqueId());

        // Spawn npc
        internalNPC.spawn(channel);

        // set npc sleeping metadata
        if (PacketEvents.getAPI().getServerManager().getVersion().isOlderThanOrEquals(ServerVersion.V_1_12_2)) {

            player.sendBlockChange(BedUtil.getBedLocation(location), Material.valueOf("BED_BLOCK"),
                    (byte) BedUtil.yawToFacing(location.getYaw()));
//                sendPackets(player,
//                        this.packetLoader.getWrapperBed().get(),
//                        this.packetLoader.getWrapperEntityTeleport()
//                                .get());  // Set the correct height of the player lying down


        } else {
            List<EntityData<?>> entityData = new ArrayList<>();
            entityData.add(new EntityData<>(6, EntityDataTypes.ENTITY_POSE, EntityPose.SLEEPING));
            WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(id, entityData);
            PacketEvents.getAPI().getProtocolManager().sendPacket(channel, packet);
        }

        // show armor
        if (hasArmor) {
            internalNPC.updateEquipment(channel);
            CorpsePlugin.getInstance().getLogger().info("Sent updateEquipment packet with helmet " + internalNPC.getHelmet());
        }

    }

    @ApiStatus.Internal
    public void hide(@NotNull Player player) {
        Object channel = PacketEvents.getAPI().getProtocolManager().getChannel(player.getUniqueId());
        ;

        internalNPC.despawn(channel);

        this.seeingPlayers.remove(player);
    }

    public boolean isShownFor(@NotNull Player player) {
        return this.seeingPlayers.contains(player);
    }

    public int getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return profile.getName();
    }

    @NotNull
    public Location getLocation() {
        return location;
    }

    @NotNull
    public Collection<Player> getSeeingPlayers() {
        return Collections.unmodifiableCollection(this.seeingPlayers);
    }

}
