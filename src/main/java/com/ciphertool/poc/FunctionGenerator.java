package com.ciphertool.poc;

import it.unimi.dsi.bits.TransformationStrategies;
import it.unimi.dsi.bits.TransformationStrategy;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongBigArrayBigList;
import it.unimi.dsi.fastutil.objects.AbstractObject2LongFunction;
import it.unimi.dsi.sux4j.mph.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FunctionGenerator {
    private static final TransformationStrategy TRANSFORMATION_STRATEGY = TransformationStrategies.byteArray();

    public AbstractObject2LongFunction generate(FunctionType functionType, List<byte[]> keys, LongArrayList values, boolean compact) throws IOException {
        switch (functionType) {
            case GOV3:
                GOV3Function.Builder<byte[]> builder = new GOV3Function.Builder<>()
                        .transform(TRANSFORMATION_STRATEGY)
                        .keys(keys)
                        .values(values);

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
                        .values(values)
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
