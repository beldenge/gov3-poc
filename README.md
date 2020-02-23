# Overview

This is a proof of concept project for generating functions for ngram count data using the Sux4j library:

http://sux.di.unimi.it/

It is implemented as a command-line application with Spring Boot.

## Prerequisites
It requires Java 9 or greater, as that's what the latest version of Sux4j is compiled with.

## Running
You can run it with the command:

`java -jar path/to/gov3-poc-1.0.0-SNAPSHOT.jar`

By default it generates a GOV3Function implementation.  It also supports TwoStepsGOV3Function and GOV4Function.  This can be toggled by passing the parameter 'function-type' as follows:

`java -jar -Dfunction-type=GOV4 path/to/gov3-poc-1.0.0-SNAPSHOT.jar`

Function Type | Parameter Value
--- | ---
GOV3Function | GOV3
TwoStepsGOV3Function | TWO_STEPS_GOV3
GOV4Function | GOV4

By default it will read all '.txt' files in the directory (non-recursive) from which it is run.
It will also output a '.dat' file which is the serialized function, which by default will be written to the directory from which it is run.

These parameters can be changed on the command line also, by passing the parameters 'input-directory' and 'output-directory', .e.g:

`java -jar -Dinput-directory=some/different/directory -Doutput-directory=yet/another/directory path/to/gov3-poc-1.0.0-SNAPSHOT.jar`

## Data Format
The input files are expected to be space-delimited with the ngram as the first element and the count as the second element.

An example file is provided in the src/test/resources directory of this repository.

## Performance tuning

As it is run as a Java application, you can supply more memory with the "-Xms" and "-Xmx" parameters as well.  For example to give it a min and max of 8GB, you can do the following:

`java -jar -Xms8G -Xmx8G path/to/gov3-poc-1.0.0-SNAPSHOT.jar`