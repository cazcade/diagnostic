package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;
import com.cazcade.diagnostic.api.DiagnosisEvent;

import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class DeadlockDiagnosis implements Diagnosis {

    private List<ThreadInfo> deadlockedThreads = new ArrayList<ThreadInfo>();

    @Override
    public String text() {
        StringBuilder builder= new StringBuilder();
        for (ThreadInfo deadlockedThread : deadlockedThreads) {
            builder.append("Thread ").append(deadlockedThread.getThreadName()).append('\n');
            builder.append("Lock owner ").append(deadlockedThread.getLockOwnerName()).append('\n');
            builder.append(Arrays.toString(deadlockedThread.getStackTrace())).append('\n');
        }
        return builder.toString();
    }

    @Override
    public boolean success() {
        return deadlockedThreads.size() == 0;
    }

    @Override
    public void repair() {
        synchronized (deadlockedThreads) {
            for (ThreadInfo deadlockedThread : deadlockedThreads) {
                ThreadUtil.getInstance().getThread(deadlockedThread.getThreadId()).interrupt();
            }
        }
    }

    @Override
    public DiagnosisEvent<? extends Diagnosis> event(String path) {
        return new DeadlockEvent(this, path);
    }

    public void addDeadlockedThread(ThreadInfo info) {
        deadlockedThreads.add(info);
    }
}
