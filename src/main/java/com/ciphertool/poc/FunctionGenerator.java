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

import it.unimi.dsi.bits.TransformationStrategies;
import it.unimi.dsi.bits.TransformationStrategy;
import it.unimi.dsi.fastutil.longs.LongBigArrayBigList;
import it.unimi.dsi.fastutil.objects.AbstractObject2LongFunction;
import it.unimi.dsi.sux4j.mph.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FunctionGenerator {
    private static final TransformationStrategy TRANSFORMATION_STRATEGY = TransformationStrategies.byteArray();

    public AbstractObject2LongFunction generate(FunctionType functionType, List<byte[]> keys, LongBigArrayBigList values, boolean compact, int outputWidth) throws IOException {
        switch (functionType) {
            case GOV3:
                GOV3Function.Builder<byte[]> builder = new GOV3Function.Builder<>()
                        .transform(TRANSFORMATION_STRATEGY)
                        .keys(keys)
                        .values(values, outputWidth);

                if (compact) {
                    builder = builder.compacted();
                }

                return builder.build();
            case TWO_STEPS_GOV3:
                return new TwoStepsGOV3Function.Builder<byte[]>()
                        .transform(TRANSFORMATION_STRATEGY)
                        .keys(keys)
                        .values(new LongBigArrayBigList(values))
                        .build();
            case GOV4:
                return new GOV4Function.Builder<byte[]>()
                        .transform(TRANSFORMATION_STRATEGY)
                        .keys(keys)
                        .values(values, outputWidth)
                        .build();
            case GV3_COMPRESSED:
                return new GV3CompressedFunction.Builder<byte[]>()
                        .transform(TRANSFORMATION_STRATEGY)
                        .keys(keys)
                        .values(values)
                        .build();
            case GV4_COMPRESSED:
                return new GV4CompressedFunction.Builder<byte[]>()
                        .transform(TRANSFORMATION_STRATEGY)
                        .keys(keys)
                        .values(values)
                        .build();
        }

        throw new IllegalArgumentException("FunctionType " + functionType.name() + " is not supported.");
    }
}
