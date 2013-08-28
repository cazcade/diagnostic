package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;
import com.cazcade.diagnostic.api.DiagnosticCheck;
import com.cazcade.diagnostic.api.DiagnosticContext;

import java.lang.management.ManagementFactory;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class ExcessiveThreadsDiagnosticCheck implements DiagnosticCheck {

    private ExcessiveThreadsDiagnosis diagnosis= null;
    private int threadLimit= 300;

    @Override
    public void perform(DiagnosticContext selfDiagnosisService) {
        diagnosis= new ExcessiveThreadsDiagnosis(ManagementFactory.getThreadMXBean().getThreadCount(), threadLimit);

    }

    @Override
    public Diagnosis diagnosis() {
        return diagnosis;
    }

    @Override
    public String name() {
        return "thread.excessive";
    }


    public void setThreadLimit(int threadLimit) {
        this.threadLimit = threadLimit;
    }
}
