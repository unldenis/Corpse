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

import com.github.unldenis.corpse.manager.*;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.atomic.*;

public class RemoveCorpseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("corpses.remove")) {
                if(args.length==1) {
                    try {
                        double radius = Math.pow(Double.parseDouble(args[0]), 2);

                        CorpsePool pool = CorpsePool.getInstance();
                        AtomicInteger count = new AtomicInteger(0);
                        pool.getCorpses()
                                .stream()
                                .filter(corpse -> corpse.getLocation().distanceSquared(player.getLocation()) <= radius)
                                .forEach(corpse -> {
                                    pool.remove(corpse.getId());
                                    count.incrementAndGet();
                                });
                        player.sendMessage("(" + count.get() + ") " + ChatColor.GREEN+"Corpses deleted");
                        return true;
                    }catch (NumberFormatException e) {
                        player.sendMessage(ChatColor.RED + "Radius must be a number");
                    }
                    return true;
                }
                sender.sendMessage(ChatColor.RED+"/removecorpse [radius] - Removes any coprse(s) in a radius of you.");
            }
        } else {
            sender.sendMessage(ChatColor.RED+"Only players can run this command");
        }


        return false;
    }
}
