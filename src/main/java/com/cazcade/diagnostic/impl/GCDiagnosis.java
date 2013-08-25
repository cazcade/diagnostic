package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.Diagnosis;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class GCDiagnosis implements Diagnosis {

    private Map<String, Long> diffs= new HashMap<String, Long>();

    public void add(String pool, long diff, long collectionCount, long collectionTime) {
        diffs.put(pool, diff);
    }

    @Override
    public String text() {
        StringBuilder builder= new StringBuilder();
        for (Map.Entry<String, Long> entry : diffs.entrySet()) {
            builder.append("Pool ").append(entry.getKey()).append(" collections since last check ").append(entry.getValue()).append("\n");
        }
        return builder.toString();
    }

    @Override
    public boolean success() {
        return  diffs.size() == 0;
    }

    @Override
    public void repair() {
        //TODO
    }
}
