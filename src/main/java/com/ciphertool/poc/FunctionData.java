/**
 * Copyright 2020 George Belden
 *
 * This file is part of gov3-poc.
 *
 * gov3-poc is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * gov3-poc is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * gov3-poc. If not, see <http://www.gnu.org/licenses/>.
 */

package com.ciphertool.poc;

import it.unimi.dsi.fastutil.longs.LongBigArrayBigList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FunctionData {
    private List<byte[]> keys;
    private LongBigArrayBigList values;
}
