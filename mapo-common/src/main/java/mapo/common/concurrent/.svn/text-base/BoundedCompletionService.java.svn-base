/**
 * 
 */
package mapo.common.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Exactly like ExecutorCompletionService, except that uses a Semaphore to only
 * permit X tasks to run concurrently on the passed Executor
 * 
 * @author jmayaalv
 * 
 */
public class BoundedCompletionService<V> implements CompletionService<V> {

	private final Semaphore semaphore;
	private final Executor executor;
	private final BlockingQueue<Future<V>> completionQueue;

	// FutureTask to release Semaphore as completed
	@SuppressWarnings("unchecked")
	private class BoundedFuture extends FutureTask {
		BoundedFuture(Callable<V> c) {
			super(c);
		}

		BoundedFuture(Runnable t, V r) {
			super(t, r);
		}

		protected void done() {
			semaphore.release();
			completionQueue.add(this);
		}
	}

	public BoundedCompletionService(final Executor executor, int permits) {
		this.executor = executor;
		this.semaphore = new Semaphore(permits);
		this.completionQueue = new LinkedBlockingQueue<Future<V>>();
	}

	public Future<V> poll() {
		return this.completionQueue.poll();
	}

	public Future<V> poll(long timeout, TimeUnit unit) throws InterruptedException {
		return this.completionQueue.poll(timeout, unit);
	}

	@SuppressWarnings("unchecked")
	public Future<V> submit(Callable<V> task) {
		if (task == null)
			throw new NullPointerException();
		try {
			BoundedFuture f = new BoundedFuture(task);
			this.semaphore.acquire(); // waits
			this.executor.execute(f);
			return f;
		} catch (InterruptedException e) {
			// do nothing
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Future<V> submit(Runnable task, V result) {
		if (task == null)
			throw new NullPointerException();
		try {
			BoundedFuture f = new BoundedFuture(task, result);
			this.semaphore.acquire(); // waits
			this.executor.execute(f);
			return f;
		} catch (InterruptedException e) {
			// do nothing
		}
		return null;
	}

	public Future<V> take() throws InterruptedException {
		return this.completionQueue.take();
	}
}
