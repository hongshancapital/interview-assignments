package com.domain.utils;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 基础线程服务抽象类
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
public abstract class ServiceThread implements Runnable {

    private Thread thread; //线程对象

    private final AtomicBoolean started = new AtomicBoolean(false);;  //线程CAS 标记是否启动
    protected volatile boolean stopped = false;  //线程停止标记

    public ServiceThread() {

    }
    public abstract String getServiceName(); //线程名称
    /**
     * 线程启动
     */
    public void start() {
        if (!started.compareAndSet(false, true)) {
            return;
        }
        stopped = false;
        this.thread = new Thread(this, getServiceName());
        this.thread.start();
    }
    /**
     * 线程关闭
     */
    public void shutdown() {
        isShutdown();
        if (!started.compareAndSet(true, false)) {
            return;
        }
        this.stopped = true;
        this.shutdown();
    }

    /**
     * 线程停止
     */
    public void stop() {
        if (!started.compareAndSet(true, false)) {
            return;
        }
        this.stopped = true;
        this.stop();
    }


    public boolean isStopped() {
        return stopped;
    }

    protected void isShutdown(){ }
}
