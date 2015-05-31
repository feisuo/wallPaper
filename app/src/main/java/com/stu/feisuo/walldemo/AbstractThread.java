package com.stu.feisuo.walldemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractThread extends Thread {
	protected boolean run;
	protected boolean pause;
	private Lock lockObject;
	private Condition conditionVariable;
	protected int poll;

	/**
	 * Create a thread and set the polling (wait time between executions)
	 * 
	 * @param poll
	 *            Time to wait in milliseconds between calls to doWork()
	 */
	public AbstractThread(int poll) {
		pause = false;
		lockObject = new ReentrantLock();
		conditionVariable = lockObject.newCondition();
		this.poll = poll;
	}

	/**
	 * Stops the thread. Call this in your onDestroy() and onSurfaceDestroy()
	 * methods.
	 */
	public final void stopThread() {
		run = false;
		pause = false;
		try {
			lockObject.lock();
			conditionVariable.signal();
		} finally {
			lockObject.unlock();
		}
	}

	/**
	 * Pauses the thread. Call this in your onVisibilityChanged() method.
	 */
	public final void pauseThread() {
		pause = true;
	}

	/**
	 * Resumes the thread. Call this in your onVisibilityChanged() method.
	 */
	public final void resumeThread() {
		pause = false;
		try {
			lockObject.lock();
			conditionVariable.signal();
		} finally {
			lockObject.unlock();
		}
	}

	@Override
	public final void run() {
		run = true;
		while (run) {
			if (pause) {
				try {
					lockObject.lock();
					conditionVariable.await();
				} catch (InterruptedException e) {
				} finally {
					lockObject.unlock();
				}
			}
			doStuff();
			try {
				sleep(poll);
			} catch (InterruptedException e) {
			}

		}
	}

	/**
	 * All the *stuff* you want to do in one iteration of this thread.
	 */
	protected abstract void doStuff();
}
