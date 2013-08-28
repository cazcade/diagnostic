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
public class LongRunningThreadDiagnosis implements Diagnosis {
    private final List<ThreadInfo> longRunningThreads = new ArrayList<ThreadInfo>();

    @Override
    public String text() {
        StringBuilder builder= new StringBuilder();
        for (ThreadInfo threadInfo : longRunningThreads) {
            builder.append("Thread ").append(threadInfo.getThreadName()).append(" waiting for ").append(threadInfo.getBlockedTime()/1000).append("s \n");
            builder.append(Arrays.toString(threadInfo.getStackTrace())).append('\n');
        }
        return builder.toString();
    }

    @Override
    public boolean success() {
        return longRunningThreads.size() == 0;
    }

    @Override
    public void repair() {
        synchronized (longRunningThreads) {
            for (ThreadInfo deadlockedThread : longRunningThreads) {
                ThreadUtil.getInstance().getThread(deadlockedThread.getThreadId()).interrupt();
            }
        }
    }

    @Override
    public DiagnosisEvent<? extends Diagnosis> event(String path) {
        return new LongRunningThreadEvent(this, path);
    }



    public void addLongBlockedThread(ThreadInfo info) {
        longRunningThreads.add(info);
    }
}
