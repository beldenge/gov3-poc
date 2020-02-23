package com.ciphertool.poc;

import it.unimi.dsi.fastutil.objects.AbstractObject2LongFunction;
import it.unimi.dsi.sux4j.mph.GOV3Function;
import it.unimi.dsi.sux4j.mph.GOV4Function;
import it.unimi.dsi.sux4j.mph.TwoStepsGOV3Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger LOG = LoggerFactory.getLogger(Gov3ProofOfConceptApplication.class);

    @Value("${function-type}")
    private FunctionType functionType;

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
        FunctionData functionData = dataImporter.importData(inputDirectory);

        AbstractObject2LongFunction function = functionGenerator.generate(functionType, functionData.getKeys(), functionData.getValues());

        dumpFunction(function);
    }

    private void dumpFunction(AbstractObject2LongFunction function) throws IOException {
        if (function instanceof GOV3Function) {
            ((GOV3Function) function).dump(outputDirectory + "/GOV3Function.dat");
        } else if (function instanceof TwoStepsGOV3Function) {
            TwoStepsGOV3Function twoStepsGOV3Function = ((TwoStepsGOV3Function) function);

            Field firstFunctionField = ReflectionUtils.findField(TwoStepsGOV3Function.class, "firstFunction");
            GOV3Function firstFunction = (GOV3Function) ReflectionUtils.getField(firstFunctionField, twoStepsGOV3Function);

            if (firstFunction != null) {
                firstFunction.dump(outputDirectory + "/TwoStepsGOV3Function-first.dat");
            }

            Field secondFunctionField = ReflectionUtils.findField(TwoStepsGOV3Function.class, "secondFunction");
            GOV3Function secondFunction = (GOV3Function) ReflectionUtils.getField(secondFunctionField, twoStepsGOV3Function);

            secondFunction.dump(outputDirectory + "/TwoStepsGOV3Function-second.dat");
        } else if (function instanceof GOV4Function) {
            ((GOV4Function) function).dump(outputDirectory + "/GOV4Function.dat");
        }
    }
}