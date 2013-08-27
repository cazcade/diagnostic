package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.AbstractDiagnosisEvent;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class SimpleHeapMemoryEvent extends AbstractDiagnosisEvent<SimpleHeapMemoryDiagnosis>{
    public SimpleHeapMemoryEvent(SimpleHeapMemoryDiagnosis diagnosis, String path) {
        super(diagnosis, path);
    }
}
