package com.cazcade.diagnostic.api;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public interface DiagnosticContext {

    void fire(String path, Diagnosis diagnosis);
}
