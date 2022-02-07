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

package com.github.unldenis.corpse.command;


import com.github.unldenis.corpse.api.*;
import com.github.unldenis.corpse.logic.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

public class SpawnCorpseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("corpses.spawn")) {
                if(args.length==0) {
                    new Corpse(player);
                    player.sendMessage(ChatColor.GREEN+"Corpse created");
                    return true;
                }else if(args.length==1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                    if(target.isOnline()) {
                        CorpseAPI.getInstance().spawnCorpse((Player) target, player.getLocation());
                    } else {
                        new Corpse(player.getLocation(), target, null);
                    }
                    player.sendMessage(ChatColor.GREEN+"Corpse created");
                    return true;
                }
                sender.sendMessage(ChatColor.RED+"/spawncorpse [Player] - Spawns a corpse of a player if the name is given else it just spawns a corpse of yourself.");
            }
        } else {
            sender.sendMessage(ChatColor.RED+"Only players can run this command");
        }
        return false;
    }
}
