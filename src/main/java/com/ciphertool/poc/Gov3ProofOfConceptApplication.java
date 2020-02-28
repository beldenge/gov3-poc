package com.ciphertool.poc;

import it.unimi.dsi.fastutil.objects.AbstractObject2LongFunction;
import it.unimi.dsi.sux4j.mph.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;

@SpringBootApplication
public class Gov3ProofOfConceptApplication implements CommandLineRunner {
    private static final String OUTPUT_EXTENSION = ".dat";

    @Value("${function-type}")
    private FunctionType functionType;

    @Value("${compact:false}")
    private boolean compact;

    @Value("${key-length}")
    private int keyLength;

    @Value("${output-width:0}")
    private int outputWidth;

    @Value("${input-directory}")
    private String inputDirectory;

    @Value("${output-directory}")
    private String outputDirectory;

    @Autowired
    private DataImporter dataImporter;

    @Autowired
    private FunctionGenerator functionGenerator;

    public static void main(String[] args) {
        SpringApplication.run(Gov3ProofOfConceptApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        FunctionData functionData = dataImporter.importData(inputDirectory, keyLength);

        AbstractObject2LongFunction function = functionGenerator.generate(functionType, functionData.getKeys(), functionData.getValues(), compact, outputWidth);

        dumpFunction(function);
    }

    private void dumpFunction(AbstractObject2LongFunction function) throws IOException {
        // We have to check instanceof for each supported function because the dump() method doesn't seem to be exposed on any interface or parent class
        if (function instanceof GOV3Function) {
            ((GOV3Function) function).dump(outputDirectory + "/GOV3Function" + OUTPUT_EXTENSION);
        } else if (function instanceof TwoStepsGOV3Function) {
            TwoStepsGOV3Function twoStepsGOV3Function = ((TwoStepsGOV3Function) function);

            Field firstFunctionField = ReflectionUtils.findField(TwoStepsGOV3Function.class, "firstFunction");
            ReflectionUtils.makeAccessible(firstFunctionField);
            GOV3Function firstFunction = (GOV3Function) ReflectionUtils.getField(firstFunctionField, twoStepsGOV3Function);

            if (firstFunction != null) {
                firstFunction.dump(outputDirectory + "/TwoStepsGOV3Function-first" + OUTPUT_EXTENSION);
            }

            Field secondFunctionField = ReflectionUtils.findField(TwoStepsGOV3Function.class, "secondFunction");
            ReflectionUtils.makeAccessible(secondFunctionField);
            GOV3Function secondFunction = (GOV3Function) ReflectionUtils.getField(secondFunctionField, twoStepsGOV3Function);

            if (secondFunction != null) {
                secondFunction.dump(outputDirectory + "/TwoStepsGOV3Function-second" + OUTPUT_EXTENSION);
            }
        } else if (function instanceof GOV4Function) {
            ((GOV4Function) function).dump(outputDirectory + "/GOV4Function" + OUTPUT_EXTENSION);
        } else if (function instanceof GV3CompressedFunction) {
            ((GV3CompressedFunction) function).dump(outputDirectory + "/GV3CompressedFunction" + OUTPUT_EXTENSION);
        } else if (function instanceof GV4CompressedFunction) {
            ((GV4CompressedFunction) function).dump(outputDirectory + "/GV4CompressedFunction" + OUTPUT_EXTENSION);
        }
    }
}