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
public class LongRunningThreadDiagnosticCheck implements DiagnosticCheck {
    ThreadGroup rootThreadGroup = null;

    private Diagnosis diagnosis= null;

    @Override
    public void perform(DiagnosticContext selfDiagnosisService) {
        LongRunningThreadDiagnosis newDiagnosis= new LongRunningThreadDiagnosis();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] threadIds = bean.getAllThreadIds();

        if (threadIds != null) {
            ThreadInfo[] infos = bean.getThreadInfo(threadIds);

            for (ThreadInfo info : infos) {
                if(info.getBlockedTime() > 60 *1000)
                 newDiagnosis.addLongBlockedThread(info);

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
