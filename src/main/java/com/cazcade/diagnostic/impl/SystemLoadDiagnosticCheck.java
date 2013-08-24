package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;
import com.cazcade.diagnostic.api.DiagnosticCheck;
import com.cazcade.diagnostic.api.DiagnosticContext;

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
