package com.cazcade.diagnostic.api;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public interface DiagnosisEvent<D extends Diagnosis> {

    D getDiagnosis();

    String getPath();
}
