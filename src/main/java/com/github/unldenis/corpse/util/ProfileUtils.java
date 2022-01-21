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

import org.jetbrains.annotations.*;

import java.util.*;

public class ProfileUtils {

    /**
     * Creates a random name which is exactly 16 chars long and only contains alphabetic and numeric
     * chars.
     * @return a randomly created minecraft name.
     */
    @NotNull
    public static String randomName() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }

}
