package com.leo.store;

/**
 * 检查主cache中无效key的守护进程
 * @author LeoZhang
 *
 */
public class MemoryDeamon implements Runnable {

	@Override
	public void run() {
		while (true) {//设置为守护进程，自然可以优雅终止
			try {
				Thread.sleep(5 * 60 * 1000L);
				MemStore.checkAndCleanMainMap();
			} catch(InterruptedException e1) {
				return;
			} catch (Exception e2) {
				// ignore
			}
		}
	}

}
