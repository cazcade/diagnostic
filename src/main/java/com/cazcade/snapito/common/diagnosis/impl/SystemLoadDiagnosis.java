package com.cazcade.snapito.common.diagnosis.impl;

import com.cazcade.snapito.common.diagnosis.api.Diagnosis;
import com.cazcade.snapito.common.diagnosis.api.DiagnosticContext;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class SystemLoadDiagnosis implements Diagnosis {
    public static final double THRESHOLD = 3.0;
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
