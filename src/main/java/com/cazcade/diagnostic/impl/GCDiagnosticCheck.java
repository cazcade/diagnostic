package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;
import com.cazcade.diagnostic.api.DiagnosticCheck;
import com.cazcade.diagnostic.api.DiagnosticContext;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class GCDiagnosticCheck implements DiagnosticCheck {
    Map<String, AtomicLong> map = new HashMap<String, AtomicLong>();
    private int threshold = 2;
    private GCDiagnosis diagnosis;

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public void perform(DiagnosticContext selfDiagnosisService) {
        diagnosis = new GCDiagnosis();
        List<GarbageCollectorMXBean> memoryMXBean = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean garbageCollectorMXBean : memoryMXBean) {
            long count = garbageCollectorMXBean.getCollectionCount();
            long collectionCount = count;
            String name = garbageCollectorMXBean.getName();
            AtomicLong previousCount = map.get(name);
            if (previousCount != null) {
                long diff = collectionCount - previousCount.get();
                if (diff > threshold) {
                    diagnosis.add(name, diff, count, garbageCollectorMXBean.getCollectionTime());
                }
            }
            map.put(name, new AtomicLong(count));
        }
    }

    @Override
    public Diagnosis diagnosis() {
        return diagnosis;
    }

    @Override
    public String name() {
        return "memory.gc.rate";
    }
}
