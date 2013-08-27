package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;
import com.cazcade.diagnostic.api.DiagnosticCheck;
import com.cazcade.diagnostic.api.DiagnosticContext;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class ExcessiveThreadsDiagnosticCheck implements DiagnosticCheck {
    ThreadGroup rootThreadGroup = null;

    private ExcessiveThreadsDiagnosis diagnosis= null;

    @Override
    public void perform(DiagnosticContext selfDiagnosisService) {
        DeadlockDiagnosis newDiagnosis= new DeadlockDiagnosis();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        diagnosis= new ExcessiveThreadsDiagnosis(bean.getThreadCount());

    }

    @Override
    public Diagnosis diagnosis() {
        return diagnosis;
    }

    @Override
    public String name() {
        return "thread.excessive";
    }


}
