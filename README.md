# Overview

This is a proof of concept project for generating functions for ngram count data using the Sux4j library:

http://sux.di.unimi.it/

It is implemented as a command-line application with Spring Boot.

## Prerequisites
It requires Java 9 or greater, as that's what the latest version of Sux4j is compiled with.

## Running
You can run it with the command:

`java -jar -Dkey-length=3 -Dfunction-type=GOV3 path/to/gov3-poc-1.1.1-SNAPSHOT.jar`

The key-length and function-type parameters are required.  The key-length parameter specifies how many characters to allocate for the key in each line of the data file.

It supports the following functions: GOV3Function, TwoStepsGOV3Function, GOV4Function, GV3CompressedFunction, and GV4CompressedFunction.  Pass the appropriate 'function-type' as follows:

Function Type | Parameter Value
--- | ---
GOV3Function | GOV3
TwoStepsGOV3Function | TWO_STEPS_GOV3
GOV4Function | GOV4
GV3CompressedFunction | GV3_COMPRESSED
GV4CompressedFunction | GV4_COMPRESSED

The GOV3Function also supports a compacted option, which can be specified as follows.  It defaults to false, and it will simply be ignored for any other function type.

`java -jar -Dkey-length=3 -Dfunction-type=GOV3 -Dcompacted=true path/to/gov3-poc-1.1.1-SNAPSHOT.jar`

By default it will read all '.txt' files in the directory (non-recursive) from which it is run.
It will also output a '.dat' file which is the serialized function, which by default will be written to the directory from which it is run.

These parameters can be changed on the command line also, by passing the parameters 'input-directory' and 'output-directory', .e.g:

`java -jar -Dinput-directory=some/different/directory -Doutput-directory=yet/another/directory path/to/gov3-poc-1.1.1-SNAPSHOT.jar`

## Data Format
The input files are expected to be space-delimited with the ngram as the first element and the count as the second element.

An example file is provided in the src/test/resources directory of this repository.

## Performance tuning

As it is run as a Java application, you can supply more memory with the "-Xms" and "-Xmx" parameters as well.  For example to give it a min and max of 8GB, you can do the following:

`java -jar -Xms8G -Xmx8G path/to/gov3-poc-1.1.1-SNAPSHOT.jar`

According to the Sux4j [documentation](http://sux.di.unimi.it/docs/it/unimi/dsi/sux4j/mph/GOV3Function.html), by default the function generators will use no more than 4 threads.  To increase the number of threads, pass the 'it.unimi.dsi.sux4j.mph.threads' parameter on the command-line as follows:

`java -jar -Dit.unimi.dsi.sux4j.mph.threads=8 path/to/gov3-poc-1.1.1-SNAPSHOT.jar`