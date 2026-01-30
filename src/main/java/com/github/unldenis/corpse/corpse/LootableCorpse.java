package com.github.unldenis.corpse.corpse;

import com.github.unldenis.corpse.CorpsePlugin;
import io.github.retrooper.packetevents.util.SpigotReflectionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Experimental
public class LootableCorpse extends Corpse {


    private final List<ItemStack> items;
    private boolean opened = false;

    public LootableCorpse(Location location, Player player, List<ItemStack> items) {
        super(location, SpigotReflectionUtil.getUserProfile(player), null, player.getName());
        this.items = items;
    }


    public void open(Player player) {
        if(opened) {
            player.sendMessage(CorpsePlugin.getInstance().getMessages().getCorpseAlreadyLooted());
            return;
        }
        opened = true;

        Inventory inventory = Bukkit.createInventory(null, 54, CorpsePlugin.getInstance().getMessages().getLootInventoryTitle());
        for (int i = 0; i < items.size() && i < inventory.getSize(); i++) {
            inventory.setItem(i, items.get(i));
        }
        player.openInventory(inventory);
    }



}
