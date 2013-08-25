package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;
import com.cazcade.diagnostic.api.DiagnosticContext;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class SystemLoadDiagnosis implements Diagnosis {
    public static final double THRESHOLD = 9.0;
    private final DiagnosticContext context;
    private final double systemLoadAverage;

    public SystemLoadDiagnosis(DiagnosticContext context, double systemLoadAverage) {
        this.context = context;
        this.systemLoadAverage = systemLoadAverage;
    }

    @Override
    public String text() {
        return "Load "+systemLoadAverage+"/"+THRESHOLD;
    }

    @Override
    public boolean success() {
        return  systemLoadAverage < THRESHOLD;
    }

    @Override
    public void repair() {
    }
}
