package com.ciphertool.poc;

import it.unimi.dsi.fastutil.longs.LongArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FunctionData {
    private List<byte[]> keys;
    private LongArrayList values;
}
