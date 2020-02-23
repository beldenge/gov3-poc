package com.ciphertool.poc;

import it.unimi.dsi.bits.TransformationStrategies;
import it.unimi.dsi.bits.TransformationStrategy;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongBigArrayBigList;
import it.unimi.dsi.fastutil.objects.AbstractObject2LongFunction;
import it.unimi.dsi.sux4j.mph.GOV3Function;
import it.unimi.dsi.sux4j.mph.GOV4Function;
import it.unimi.dsi.sux4j.mph.TwoStepsGOV3Function;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class FunctionGenerator {
    private static final TransformationStrategy TRANSFORMATION_STRATEGY = TransformationStrategies.byteArray();

    public AbstractObject2LongFunction generate(FunctionType functionType, List<byte[]> keys, LongArrayList values) throws IOException {
        switch (functionType) {
            case GOV3:
                return new GOV3Function.Builder<byte[]>()
                        .transform(TRANSFORMATION_STRATEGY)
                        .keys(keys)
                        .values(values)
                        .build();
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
        }

        throw new IllegalArgumentException("FunctionType " + functionType.name() + " is not supported.");
    }
}
