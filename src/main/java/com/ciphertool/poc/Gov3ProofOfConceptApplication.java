package com.ciphertool.poc;

import it.unimi.dsi.bits.TransformationStrategies;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.sux4j.mph.GOV3Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Gov3ProofOfConceptApplication implements CommandLineRunner {
    private static Logger LOG = LoggerFactory.getLogger(Gov3ProofOfConceptApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(Gov3ProofOfConceptApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
//        TH 233
//        HE 229
//        IN 228
        List<byte[]> keys = new ArrayList<>();
        keys.add("TH".getBytes());
        keys.add("HE".getBytes());
        keys.add("IN".getBytes());

        LongArrayList values = new LongArrayList();
        values.add(233);
        values.add(229);
        values.add(228);

        GOV3Function gov3Function = new GOV3Function.Builder<byte[]>()
                .transform(TransformationStrategies.byteArray())
                .keys(keys)
                .values(values)
                .build();

        LOG.info("Value: {}", gov3Function.getLong("TH".getBytes()));
        LOG.info("Value: {}", gov3Function.getLong("HE".getBytes()));
        LOG.info("Value: {}", gov3Function.getLong("IN".getBytes()));
    }
}