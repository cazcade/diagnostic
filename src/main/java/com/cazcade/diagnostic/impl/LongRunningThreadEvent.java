package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.AbstractDiagnosisEvent;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class LongRunningThreadEvent extends AbstractDiagnosisEvent<LongRunningThreadDiagnosis>{
    public LongRunningThreadEvent(LongRunningThreadDiagnosis diagnosis, String path) {
        super(diagnosis, path);
    }
}
