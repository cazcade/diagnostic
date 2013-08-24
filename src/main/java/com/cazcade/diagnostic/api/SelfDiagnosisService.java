package com.cazcade.diagnostic.api;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public interface SelfDiagnosisService {
    List<Diagnosis> diagnose();

    int listen(Pattern regex, DiagnosisListener listener);

    void remove(int listener);
}
