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

package com.github.unldenis.corpse.util;

import org.bukkit.Location;

public class BedUtil {

    /**
     * Get bed location.
     * @param location corpse's location
     * @return fake bed location
     */
    public static Location getBedLocation(Location location) {
        Location loc = location.clone();
        loc.setY(1);
        return loc;
    }

    /**
     * Get corpse facing.
     * @param yaw location yaw
     * @return bed block facing
     */
    public static int yawToFacing(float yaw) {
        int facing = 2;
        if(yaw >= -45 && yaw <= 45) {
            facing = 0;
        }
        else if(yaw >= 45 && yaw <=135) {
            facing = 1;
        }
        else if(yaw <= -45 && yaw >=-135) {
            facing = 3;
        }
        else if(yaw <= -135 && yaw >=-225) {
            facing = 2;
        }
        else if(yaw <= -225 && yaw >=-315) {
            facing = 1;
        }
        else if(yaw >= 135 && yaw <= 225) {
            facing = 2;
        }
        else if(yaw >= 225 && yaw <= 315) {
            facing = 3;
        }
        else if (yaw >= 315) {
            facing = 0;
        }
        else if (yaw <= -315) {
            facing = 0;
        }
        return facing;
    }

}
