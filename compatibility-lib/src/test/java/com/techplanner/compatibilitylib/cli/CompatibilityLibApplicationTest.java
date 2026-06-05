package com.techplanner.compatibilitylib.cli;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CompatibilityLibApplicationTest {

    @Test
    void shouldPrintUsageWhenHelpIsRequested() {
        String output = runMain(new String[]{"--help"});

        assertTrue(output.contains("compatibility-lib smoke runner"));
        assertTrue(output.contains("Usage: java -jar compatibility-lib.jar [--help]"));
    }

    @Test
    void shouldRunSmokeAnalysisByDefault() {
        String output = runMain(new String[0]);

        assertTrue(output.contains("compatibility-lib smoke run completed"));
        assertTrue(output.contains("Compatible:"));
        assertTrue(output.contains("Score:"));
    }

    private String runMain(String[] args) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(outputStream));
        try {
            CompatibilityLibApplication.main(args);
        } finally {
            System.setOut(originalOut);
        }

        return outputStream.toString();
    }
}