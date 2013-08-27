package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.AbstractDiagnosisEvent;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class GCEvent extends AbstractDiagnosisEvent<GCDiagnosis>{
    public GCEvent(GCDiagnosis diagnosis, String path) {
        super(diagnosis, path);
    }
}
