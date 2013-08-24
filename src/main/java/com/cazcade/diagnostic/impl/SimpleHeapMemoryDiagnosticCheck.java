package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;
import com.cazcade.diagnostic.api.DiagnosticCheck;
import com.cazcade.diagnostic.api.DiagnosticContext;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class SimpleHeapMemoryDiagnosticCheck implements DiagnosticCheck {
    private Diagnosis diagnosis;

    @Override
    public void perform(DiagnosticContext selfDiagnosisService) {
//        List<MemoryPoolMXBean> bean = ManagementFactory.getMemoryPoolMXBeans();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        long max = memoryMXBean.getHeapMemoryUsage().getMax();
        long used = memoryMXBean.getHeapMemoryUsage().getUsed();
        diagnosis= new SimpleHeapMemoryDiagnosis(max,used);
    }

    @Override
    public Diagnosis diagnosis() {
        return diagnosis;
    }

    @Override
    public String name() {
        return "memory.heap.simple";
    }
}
