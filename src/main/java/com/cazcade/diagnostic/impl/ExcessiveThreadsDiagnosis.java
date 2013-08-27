package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;
import com.cazcade.diagnostic.api.DiagnosisEvent;

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
        return "Threads "+threadCount+"/"+threadLimit;
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
