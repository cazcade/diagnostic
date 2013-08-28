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
    private final int threadCount;
    private  int threadLimit;



    public ExcessiveThreadsDiagnosis(int threadCount, int threadLimit) {
        super();
        this.threadCount = threadCount;
        this.threadLimit = threadLimit;
    }

    @Override
    public String text() {
        StringBuilder builder= new StringBuilder();

        Thread[] threads = ThreadUtil.getInstance().getAllThreads();
        Multiset<String> counter= HashMultiset.create();
        for (Thread thread : threads) {
            counter.add(translate(thread.getName()));
        }
        for (String name : counter.elementSet()) {
            builder.append("Thread ").append(name).append(" : ").append(counter.count(name)).append("\n");
        }

        return builder.append("Total Threads ").append(threadCount).append("/").append(threadLimit).append("\n").toString();
    }

    private String translate(String name) {
        if(name.matches("qtp.*acceptor-.*-ServerConnector.*")) {
            return "Jetty Acceptor";
        }
        if(name.matches("qtp.*selector.*")) {
            return "Jetty Selector";
        }
        return name;
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
