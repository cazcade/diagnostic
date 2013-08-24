package com.cazcade.snapito.common.diagnosis.api;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public interface Diagnosis {

    String text();
    boolean success();
    void repair();
}
