package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;
import com.cazcade.diagnostic.api.DiagnosticCheck;
import com.cazcade.diagnostic.api.DiagnosticContext;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class DeadlockDiagnosticCheck implements DiagnosticCheck {
    ThreadGroup rootThreadGroup = null;

    private DeadlockDiagnosis diagnosis= null;

    @Override
    public void perform(DiagnosticContext selfDiagnosisService) {
        DeadlockDiagnosis newDiagnosis= new DeadlockDiagnosis();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] threadIds = bean.findDeadlockedThreads();

        if (threadIds != null) {
            ThreadInfo[] infos = bean.getThreadInfo(threadIds);

            for (ThreadInfo info : infos) {
                 newDiagnosis.addDeadlockedThread(info);

            }
        }
        this.diagnosis= newDiagnosis;
    }

    @Override
    public Diagnosis diagnosis() {
        return diagnosis;
    }

    @Override
    public String name() {
        return "thread.deadlock";
    }


}
