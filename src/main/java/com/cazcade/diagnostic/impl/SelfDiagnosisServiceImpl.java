package com.cazcade.diagnostic.impl;

import com.cazcade.diagnostic.api.*;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class SelfDiagnosisServiceImpl implements SelfDiagnosisService, DiagnosticContext {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SelfDiagnosisServiceImpl.class);
    private int frequencyInSeconds = 30;
    //    private final Map<Pattern, DiagnosisListener> listeners= new HashMap<Pattern, DiagnosisListener>();
    private List<Listener> listeners = new CopyOnWriteArrayList<Listener>();
    private Timer timer;
    private List<DiagnosticCheck> checks = new ArrayList<DiagnosticCheck>();

    public void setFrequencyInSeconds(int frequencyInSeconds) {
        this.frequencyInSeconds = frequencyInSeconds;
    }

    public void setListeners(Map<String, DiagnosisListener> newListeners) {
        listeners.clear();
        for (Map.Entry<String, DiagnosisListener> entry : newListeners.entrySet()) {
            listeners.add(new Listener(Pattern.compile(entry.getKey()), entry.getValue()));
        }
    }

    public void setChecks(List<DiagnosticCheck> checks) {
        this.checks = checks;
    }

    public void init() {
        timer = new Timer("Diagnosis");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                for (DiagnosticCheck check : checks) {
                    check.perform(SelfDiagnosisServiceImpl.this);
                    //attempt self fix
                    if (check.diagnosis() != null && !check.diagnosis().success()) {
                        fire(check.name() + ".WARN", check.diagnosis());
                        log.warn("Check {} FAILED, attempting repair, diagnosis was {}", check.name(), check.diagnosis().text());
                        check.diagnosis().repair();
                        fire(check.name() + ".REPAIR", check.diagnosis());
                    }
                    //re-check
                    check.perform(SelfDiagnosisServiceImpl.this);
                    if (!check.diagnosis().success()) {
                        log.error("Check {} FAILED,  repair failed, diagnosis was {}", check.name(), check.diagnosis().text());
                        fire(check.name() + ".ERROR", check.diagnosis());
                    } else {
                        log.debug("Check {} PASSED", check.name());
                        fire(check.name() + ".OK", check.diagnosis());
                    }
                }
                } finally {
                    timer.schedule(this,frequencyInSeconds * 1000);
                }
            }
        }, frequencyInSeconds * 1000);
    }

    public void fire(String path, Diagnosis diagnosis) {
        for (Listener listener : listeners) {
            if (listener.pattern.matcher(path).matches()) {
                try {
                    listener.diagnosisListener.handle(diagnosis.event(path));
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            } else {
                log.debug("{} did not match {}", path, listener.pattern.toString());
            }
        }
    }

    @Override
    public int registerCheck(DiagnosticCheck check) {
        checks.add(check);
        return checks.indexOf(check);
    }

    @Override
    public List<Diagnosis> diagnose() {
        List<Diagnosis> diagnosises = new ArrayList<Diagnosis>();
        for (DiagnosticCheck check : checks) {
            diagnosises.add(check.diagnosis());
        }
        return diagnosises;
    }

    @Override
    public int listen(Pattern regex, DiagnosisListener listener) {
        Listener e = new Listener(regex, listener);
        listeners.add(e);
        return listeners.indexOf(e);
    }

    @Override
    public void removeListener(int id) {
        listeners.remove(id);
    }

    public void destroy() {
        timer.cancel();
    }

    private static class Listener {
        private Pattern pattern;
        private DiagnosisListener diagnosisListener;

        private Listener(Pattern pattern, DiagnosisListener diagnosisListener) {
            this.pattern = pattern;
            this.diagnosisListener = diagnosisListener;
        }


    }
}
