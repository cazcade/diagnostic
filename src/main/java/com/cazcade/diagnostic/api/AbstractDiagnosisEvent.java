package com.cazcade.diagnostic.api;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public abstract class AbstractDiagnosisEvent<D extends Diagnosis> implements DiagnosisEvent<D>  {
    private D diagnosis;
    private String path;

    @Override
    public String getPath() {
        return path;
    }


    public AbstractDiagnosisEvent(D diagnosis, String path) {
        this.diagnosis = diagnosis;
        this.path = path;
    }

    @Override
    public D getDiagnosis() {
        return diagnosis;
    }
}
