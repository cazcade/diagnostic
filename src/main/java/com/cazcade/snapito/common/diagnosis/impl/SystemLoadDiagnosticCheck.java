package com.cazcade.snapito.common.diagnosis.impl;

import com.cazcade.snapito.common.diagnosis.api.Diagnosis;
import com.cazcade.snapito.common.diagnosis.api.DiagnosticCheck;
import com.cazcade.snapito.common.diagnosis.api.DiagnosticContext;

import java.lang.management.ManagementFactory;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class SystemLoadDiagnosticCheck implements DiagnosticCheck {
    private Diagnosis diagnosis;

    @Override
    public void perform(DiagnosticContext context) {
//        List<MemoryPoolMXBean> bean = ManagementFactory.getMemoryPoolMXBeans();
        diagnosis= new SystemLoadDiagnosis(context,ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage());
    }

    @Override
    public Diagnosis diagnosis() {
        return diagnosis;
    }

    @Override
    public String name() {
        return "system.load";
    }
}
