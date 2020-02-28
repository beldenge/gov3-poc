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
