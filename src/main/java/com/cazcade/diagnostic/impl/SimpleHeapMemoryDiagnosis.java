package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;

import java.lang.management.ManagementFactory;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class SimpleHeapMemoryDiagnosis implements Diagnosis {

    public static final double THRESHOLD = 0.8;
    private final long max;
    private final long used;

    public SimpleHeapMemoryDiagnosis(long max, long used) {
        this.max = max;
        this.used = used;
    }

    @Override
    public String text() {
        return "Heap usage: "+ DiagnosticUtil.humanReadableByteCount(used, false)+"/"+ DiagnosticUtil.humanReadableByteCount(max, false);
    }

    @Override
    public boolean success() {
        return used < (max * THRESHOLD);
    }

    @Override
    public void repair() {
        ManagementFactory.getMemoryMXBean().gc();
    }
}
