package org.plan.research;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class CoverageTracker {

    public static final TreeSet<String> coverage = new TreeSet<String>();
    public static final TreeSet<String> fullCoverage = new TreeSet<String>();

    public static void logCoverage(String methodSignature, String lineNumber) {
        coverage.add(methodSignature + ":" + lineNumber);
    }

    public static void logFullCoverage(String methodSignature, String lineNumber) {
        fullCoverage.add(methodSignature + ":" + lineNumber);
    }
}
