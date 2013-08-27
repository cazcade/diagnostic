package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.AbstractDiagnosisEvent;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class SystemLoadEvent extends AbstractDiagnosisEvent<SystemLoadDiagnosis>{
    public SystemLoadEvent(SystemLoadDiagnosis diagnosis, String path) {
        super(diagnosis, path);
    }
}
