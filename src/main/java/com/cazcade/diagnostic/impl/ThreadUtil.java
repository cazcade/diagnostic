package com.cazcade.diagnostic.impl;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * @author <a href="http://uk.linkedin.com/in/neilellis">Neil Ellis</a>
 * @todo document.
 */
public class ThreadUtil {


    private static final ThreadUtil threadUtil = new ThreadUtil();
    private ThreadGroup rootThreadGroup;

    private ThreadUtil() {
    }

    public static ThreadUtil getInstance() {
        return threadUtil;
    }

    public static String translate(String name) {
        if (name.matches("qtp.*acceptor-.*-ServerConnector.*")) {
            return "Jetty Acceptor";
        }
        if (name.matches("qtp.*selector.*")) {
            return "Jetty Selector";
        }
        if (name.matches("qtp\\d+-\\d+")) {
            return "Queued Thread Pool (no name)";
        }
        if (name.matches("Timer-\\d+")) {
            return "Timer (no name)";
        }
        if (name.matches("New Relic .*")) {
            return "New Relic";
        }
        if (name.matches("pool-\\d+-thread-\\d+")) {
            return "Thread Pool (no name)";
        }
        return name.replace("-+", " ").replace("\\d+", " ").replace("\\s+", " ");
    }

    ThreadGroup getRootThreadGroup() {
        if (rootThreadGroup != null)
            return rootThreadGroup;
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        ThreadGroup ptg;
        while ((ptg = tg.getParent()) != null)
            tg = ptg;
        return tg;
    }

    public Thread[] getAllThreads() {
        final ThreadGroup root = getRootThreadGroup();
        final ThreadMXBean thbean = ManagementFactory.getThreadMXBean();
        int nAlloc = thbean.getThreadCount();
        int n = 0;
        Thread[] threads;
        do {
            nAlloc *= 2;
            threads = new Thread[nAlloc];
            n = root.enumerate(threads, true);
        } while (n == nAlloc);
        return java.util.Arrays.copyOf(threads, n);
    }

    public Thread getThread(final long id) {
        final Thread[] threads = getAllThreads();
        for (Thread thread : threads)
            if (thread.getId() == id)
                return thread;
        return null;
    }
}
