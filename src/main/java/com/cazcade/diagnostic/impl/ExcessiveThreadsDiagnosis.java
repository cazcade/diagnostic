package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;
import com.cazcade.diagnostic.api.DiagnosisEvent;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class ExcessiveThreadsDiagnosis implements Diagnosis {
    public static final double ANALYSIS_THRESHOLD = .6;
    private final int threadCount;
    private final Thread[] threads;
    private int threadLimit;


    public ExcessiveThreadsDiagnosis(Thread[] threads, int threadLimit) {
        super();
        this.threads = threads;
        this.threadCount = threads.length;
        this.threadLimit = threadLimit;
    }

    @Override
    public String text() {
        StringBuilder builder = new StringBuilder();

        if (threadCount > threadLimit * ANALYSIS_THRESHOLD) {
            Thread[] ts = threads;
            Multiset<String> counter = HashMultiset.create();
            for (Thread thread : ts) {
                counter.add(ThreadUtil.translate(thread.getName()));
            }
            for (String name : counter.elementSet()) {
                builder.append("Thread ").append(name).append(" : ").append(counter.count(name)).append("\n");
            }
        }

        return builder.append("Total Threads ").append(threadCount).append("/").append(threadLimit).append("\n").toString();
    }

    @Override
    public boolean success() {
        return threadCount < threadLimit;
    }

    @Override
    public void repair() {
    }

    @Override
    public DiagnosisEvent<? extends Diagnosis> event(String path) {
        return new ExcessiveThreadsEvent(this, path);
    }
}
