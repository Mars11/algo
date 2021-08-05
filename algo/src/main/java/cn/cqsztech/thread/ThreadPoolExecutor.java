//package cn.cqsztech.thread;
//
//
//import java.util.concurrent.*;
//import java.util.concurrent.locks.AbstractQueuedSynchronizer;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.ReentrantLock;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.*;
//public class ThreadPoolExecutor extends AbstractExecutorService {
//    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
//    private static final int COUNT_BITS = Integer.SIZE - 3;
//    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
//
//    // runState is stored in the high-order bits
//    private static final int RUNNING    = -1 << COUNT_BITS;
//    private static final int SHUTDOWN   =  0 << COUNT_BITS;
//    private static final int STOP       =  1 << COUNT_BITS;
//    private static final int TIDYING    =  2 << COUNT_BITS;
//    private static final int TERMINATED =  3 << COUNT_BITS;
//
//    // Packing and unpacking ctl
//    private static int runStateOf(int c)     { return c & ~CAPACITY; }
//    private static int workerCountOf(int c)  { return c & CAPACITY; }
//    private static int ctlOf(int rs, int wc) { return rs | wc; }
//
//    /*
//     * Bit field accessors that don't require unpacking ctl.
//     * These depend on the bit layout and on workerCount being never negative.
//     */
//
//    private static boolean runStateLessThan(int c, int s) {
//        return c < s;
//    }
//
//    private static boolean runStateAtLeast(int c, int s) {
//        return c >= s;
//    }
//
//    private static boolean isRunning(int c) {
//        return c < SHUTDOWN;
//    }
//
//    /**
//     * Attempts to CAS-increment the workerCount field of ctl.
//     */
//    private boolean compareAndIncrementWorkerCount(int expect) {
//        return ctl.compareAndSet(expect, expect + 1);
//    }
//
//    /**
//     * Attempts to CAS-decrement the workerCount field of ctl.
//     */
//    private boolean compareAndDecrementWorkerCount(int expect) {
//        return ctl.compareAndSet(expect, expect - 1);
//    }
//
//    /**
//     * Decrements the workerCount field of ctl. This is called only on
//     * abrupt termination of a thread (see processWorkerExit). Other
//     * decrements are performed within getTask.
//     */
//    private void decrementWorkerCount() {
//        do {} while (! compareAndDecrementWorkerCount(ctl.get()));
//    }
//
//
//    private final BlockingQueue<Runnable> workQueue;
//
//
//    private final ReentrantLock mainLock = new ReentrantLock();
//
//
//    private final HashSet<Worker> workers = new HashSet<Worker>();
//
//
//    private final Condition termination = mainLock.newCondition();
//
//
//    private int largestPoolSize;
//
//
//    private long completedTaskCount;
//
//
//
//    private volatile ThreadFactory threadFactory;
//
//    /**
//     * Handler called when saturated or shutdown in execute.
//     */
//    private volatile RejectedExecutionHandler handler;
//
//
//    private volatile long keepAliveTime;
//
//    private volatile boolean allowCoreThreadTimeOut;
//
//
//    private volatile int corePoolSize;
//
//
//    private volatile int maximumPoolSize;
//
//    private static final RejectedExecutionHandler defaultHandler =
//            new java.util.concurrent.ThreadPoolExecutor.AbortPolicy();
//
//    private static final RuntimePermission shutdownPerm =
//            new RuntimePermission("modifyThread");
//
//
//    private final class Worker
//            extends AbstractQueuedSynchronizer
//            implements Runnable
//    {
//        /**
//         * This class will never be serialized, but we provide a
//         * serialVersionUID to suppress a javac warning.
//         */
//        private static final long serialVersionUID = 6138294804551838833L;
//
//        /** Thread this worker is running in.  Null if factory fails. */
//        final Thread thread;
//        /** Initial task to run.  Possibly null. */
//        Runnable firstTask;
//        /** Per-thread task counter */
//        volatile long completedTasks;
//
//        /**
//         * Creates with given first task and thread from ThreadFactory.
//         * @param firstTask the first task (null if none)
//         */
//        Worker(Runnable firstTask) {
//            setState(-1); // inhibit interrupts until runWorker
//            this.firstTask = firstTask;
//            this.thread = getThreadFactory().newThread(this);
//        }
//
//        /** Delegates main run loop to outer runWorker  */
//        public void run() {
//            runWorker(this);
//        }
//
//        // Lock methods
//        //
//        // The value 0 represents the unlocked state.
//        // The value 1 represents the locked state.
//
//        protected boolean isHeldExclusively() {
//            return getState() != 0;
//        }
//
//        protected boolean tryAcquire(int unused) {
//            if (compareAndSetState(0, 1)) {
//                setExclusiveOwnerThread(Thread.currentThread());
//                return true;
//            }
//            return false;
//        }
//
//        protected boolean tryRelease(int unused) {
//            setExclusiveOwnerThread(null);
//            setState(0);
//            return true;
//        }
//
//        public void lock()        { acquire(1); }
//        public boolean tryLock()  { return tryAcquire(1); }
//        public void unlock()      { release(1); }
//        public boolean isLocked() { return isHeldExclusively(); }
//
//        void interruptIfStarted() {
//            Thread t;
//            if (getState() >= 0 && (t = thread) != null && !t.isInterrupted()) {
//                try {
//                    t.interrupt();
//                } catch (SecurityException ignore) {
//                }
//            }
//        }
//    }
//
//    /*
//     * Methods for setting control state
//     */
//
//    /**
//     * Transitions runState to given target, or leaves it alone if
//     * already at least the given target.
//     *
//     * @param targetState the desired state, either SHUTDOWN or STOP
//     *        (but not TIDYING or TERMINATED -- use tryTerminate for that)
//     */
//    private void advanceRunState(int targetState) {
//        for (;;) {
//            int c = ctl.get();
//            if (runStateAtLeast(c, targetState) ||
//                    ctl.compareAndSet(c, ctlOf(targetState, workerCountOf(c))))
//                break;
//        }
//    }
//
//
//    final void tryTerminate() {
//        for (;;) {
//            int c = ctl.get();
//            if (isRunning(c) ||
//                    runStateAtLeast(c, TIDYING) ||
//                    (runStateOf(c) == SHUTDOWN && ! workQueue.isEmpty()))
//                return;
//            if (workerCountOf(c) != 0) { // Eligible to terminate
//                interruptIdleWorkers(ONLY_ONE);
//                return;
//            }
//
//            final ReentrantLock mainLock = this.mainLock;
//            mainLock.lock();
//            try {
//                if (ctl.compareAndSet(c, ctlOf(TIDYING, 0))) {
//                    try {
//                        terminated();
//                    } finally {
//                        ctl.set(ctlOf(TERMINATED, 0));
//                        termination.signalAll();
//                    }
//                    return;
//                }
//            } finally {
//                mainLock.unlock();
//            }
//            // else retry on failed CAS
//        }
//    }
//
//
//    private void checkShutdownAccess() {
//        SecurityManager security = System.getSecurityManager();
//        if (security != null) {
//            security.checkPermission(shutdownPerm);
//            final ReentrantLock mainLock = this.mainLock;
//            mainLock.lock();
//            try {
//                for (Worker w : workers)
//                    security.checkAccess(w.thread);
//            } finally {
//                mainLock.unlock();
//            }
//        }
//    }
//
//
//    private void interruptWorkers() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            for (Worker w : workers)
//                w.interruptIfStarted();
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//
//    private void interruptIdleWorkers(boolean onlyOne) {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            for (Worker w : workers) {
//                Thread t = w.thread;
//                if (!t.isInterrupted() && w.tryLock()) {
//                    try {
//                        t.interrupt();
//                    } catch (SecurityException ignore) {
//                    } finally {
//                        w.unlock();
//                    }
//                }
//                if (onlyOne)
//                    break;
//            }
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    /**
//     * Common form of interruptIdleWorkers, to avoid having to
//     * remember what the boolean argument means.
//     */
//    private void interruptIdleWorkers() {
//        interruptIdleWorkers(false);
//    }
//
//    private static final boolean ONLY_ONE = true;
//
//    /*
//     * Misc utilities, most of which are also exported to
//     * ScheduledThreadPoolExecutor
//     */
//
//    /**
//     * Invokes the rejected execution handler for the given command.
//     * Package-protected for use by ScheduledThreadPoolExecutor.
//     */
//    final void reject(Runnable command) {
//        handler.rejectedExecution(command, this);
//    }
//
//    /**
//     * Performs any further cleanup following run state transition on
//     * invocation of shutdown.  A no-op here, but used by
//     * ScheduledThreadPoolExecutor to cancel delayed tasks.
//     */
//    void onShutdown() {
//    }
//
//    /**
//     * State check needed by ScheduledThreadPoolExecutor to
//     * enable running tasks during shutdown.
//     *
//     * @param shutdownOK true if should return true if SHUTDOWN
//     */
//    final boolean isRunningOrShutdown(boolean shutdownOK) {
//        int rs = runStateOf(ctl.get());
//        return rs == RUNNING || (rs == SHUTDOWN && shutdownOK);
//    }
//
//    /**
//     * Drains the task queue into a new list, normally using
//     * drainTo. But if the queue is a DelayQueue or any other kind of
//     * queue for which poll or drainTo may fail to remove some
//     * elements, it deletes them one by one.
//     */
//    private List<Runnable> drainQueue() {
//        BlockingQueue<Runnable> q = workQueue;
//        ArrayList<Runnable> taskList = new ArrayList<Runnable>();
//        q.drainTo(taskList);
//        if (!q.isEmpty()) {
//            for (Runnable r : q.toArray(new Runnable[0])) {
//                if (q.remove(r))
//                    taskList.add(r);
//            }
//        }
//        return taskList;
//    }
//
//    /*
//     * Methods for creating, running and cleaning up after workers
//     */
//
//    private boolean addWorker(Runnable firstTask, boolean core) {
//        retry:
//        for (;;) {
//            int c = ctl.get();
//            int rs = runStateOf(c);
//
//            // Check if queue empty only if necessary.
//            if (rs >= SHUTDOWN &&
//                    ! (rs == SHUTDOWN &&
//                            firstTask == null &&
//                            ! workQueue.isEmpty()))
//                return false;
//
//            for (;;) {
//                int wc = workerCountOf(c);
//                if (wc >= CAPACITY ||
//                        wc >= (core ? corePoolSize : maximumPoolSize))
//                    return false;
//                if (compareAndIncrementWorkerCount(c))
//                    break retry;
//                c = ctl.get();  // Re-read ctl
//                if (runStateOf(c) != rs)
//                    continue retry;
//                // else CAS failed due to workerCount change; retry inner loop
//            }
//        }
//
//        boolean workerStarted = false;
//        boolean workerAdded = false;
//        Worker w = null;
//        try {
//            w = new Worker(firstTask);
//            final Thread t = w.thread;
//            if (t != null) {
//                final ReentrantLock mainLock = this.mainLock;
//                mainLock.lock();
//                try {
//                    // Recheck while holding lock.
//                    // Back out on ThreadFactory failure or if
//                    // shut down before lock acquired.
//                    int rs = runStateOf(ctl.get());
//
//                    if (rs < SHUTDOWN ||
//                            (rs == SHUTDOWN && firstTask == null)) {
//                        if (t.isAlive()) // precheck that t is startable
//                            throw new IllegalThreadStateException();
//                        workers.add(w);
//                        int s = workers.size();
//                        if (s > largestPoolSize)
//                            largestPoolSize = s;
//                        workerAdded = true;
//                    }
//                } finally {
//                    mainLock.unlock();
//                }
//                if (workerAdded) {
//                    t.start();
//                    workerStarted = true;
//                }
//            }
//        } finally {
//            if (! workerStarted)
//                addWorkerFailed(w);
//        }
//        return workerStarted;
//    }
//
//    /**
//     * Rolls back the worker thread creation.
//     * - removes worker from workers, if present
//     * - decrements worker count
//     * - rechecks for termination, in case the existence of this
//     *   worker was holding up termination
//     */
//    private void addWorkerFailed(Worker w) {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            if (w != null)
//                workers.remove(w);
//            decrementWorkerCount();
//            tryTerminate();
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//
//    private void processWorkerExit(Worker w, boolean completedAbruptly) {
//        if (completedAbruptly) // If abrupt, then workerCount wasn't adjusted
//            decrementWorkerCount();
//
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            completedTaskCount += w.completedTasks;
//            workers.remove(w);
//        } finally {
//            mainLock.unlock();
//        }
//
//        tryTerminate();
//
//        int c = ctl.get();
//        if (runStateLessThan(c, STOP)) {
//            if (!completedAbruptly) {
//                int min = allowCoreThreadTimeOut ? 0 : corePoolSize;
//                if (min == 0 && ! workQueue.isEmpty())
//                    min = 1;
//                if (workerCountOf(c) >= min)
//                    return; // replacement not needed
//            }
//            addWorker(null, false);
//        }
//    }
//
//
//    private Runnable getTask() {
//        boolean timedOut = false; // Did the last poll() time out?
//
//        for (;;) {
//            int c = ctl.get();
//            int rs = runStateOf(c);
//
//            // Check if queue empty only if necessary.
//            if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
//                decrementWorkerCount();
//                return null;
//            }
//
//            int wc = workerCountOf(c);
//
//            // Are workers subject to culling?
//            boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;
//
//            if ((wc > maximumPoolSize || (timed && timedOut))
//                    && (wc > 1 || workQueue.isEmpty())) {
//                if (compareAndDecrementWorkerCount(c))
//                    return null;
//                continue;
//            }
//
//            try {
//                Runnable r = timed ?
//                        workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
//                        workQueue.take();
//                if (r != null)
//                    return r;
//                timedOut = true;
//            } catch (InterruptedException retry) {
//                timedOut = false;
//            }
//        }
//    }
//
//
//    final void runWorker(ThreadPoolExecutor.Worker w) {
//        Thread wt = Thread.currentThread();
//        Runnable task = w.firstTask;
//        w.firstTask = null;
//        w.unlock(); // allow interrupts
//        boolean completedAbruptly = true;
//        try {
//            while (task != null || (task = getTask()) != null) {
//                w.lock();
//                // If pool is stopping, ensure thread is interrupted;
//                // if not, ensure thread is not interrupted.  This
//                // requires a recheck in second case to deal with
//                // shutdownNow race while clearing interrupt
//                if ((runStateAtLeast(ctl.get(), STOP) ||
//                        (Thread.interrupted() &&
//                                runStateAtLeast(ctl.get(), STOP))) &&
//                        !wt.isInterrupted())
//                    wt.interrupt();
//                try {
//                    beforeExecute(wt, task);
//                    Throwable thrown = null;
//                    try {
//                        task.run();
//                    } catch (RuntimeException x) {
//                        thrown = x; throw x;
//                    } catch (Error x) {
//                        thrown = x; throw x;
//                    } catch (Throwable x) {
//                        thrown = x; throw new Error(x);
//                    } finally {
//                        afterExecute(task, thrown);
//                    }
//                } finally {
//                    task = null;
//                    w.completedTasks++;
//                    w.unlock();
//                }
//            }
//            completedAbruptly = false;
//        } finally {
//            processWorkerExit(w, completedAbruptly);
//        }
//    }
//
//
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue) {
//        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
//                Executors.defaultThreadFactory(), defaultHandler);
//    }
//
//
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              ThreadFactory threadFactory) {
//        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
//                threadFactory, defaultHandler);
//    }
//
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              RejectedExecutionHandler handler) {
//        this(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
//                Executors.defaultThreadFactory(), handler);
//    }
//
//    public ThreadPoolExecutor(int corePoolSize,
//                              int maximumPoolSize,
//                              long keepAliveTime,
//                              TimeUnit unit,
//                              BlockingQueue<Runnable> workQueue,
//                              ThreadFactory threadFactory,
//                              RejectedExecutionHandler handler) {
//        if (corePoolSize < 0 ||
//                maximumPoolSize <= 0 ||
//                maximumPoolSize < corePoolSize ||
//                keepAliveTime < 0)
//            throw new IllegalArgumentException();
//        if (workQueue == null || threadFactory == null || handler == null)
//            throw new NullPointerException();
//        this.corePoolSize = corePoolSize;
//        this.maximumPoolSize = maximumPoolSize;
//        this.workQueue = workQueue;
//        this.keepAliveTime = unit.toNanos(keepAliveTime);
//        this.threadFactory = threadFactory;
//        this.handler = handler;
//    }
//
//
//    public void execute(Runnable command) {
//        if (command == null)
//            throw new NullPointerException();
//
//        int c = ctl.get();
//        if (workerCountOf(c) < corePoolSize) {
//            if (addWorker(command, true))
//                return;
//            c = ctl.get();
//        }
//        if (isRunning(c) && workQueue.offer(command)) {
//            int recheck = ctl.get();
//            if (! isRunning(recheck) && remove(command))
//                reject(command);
//            else if (workerCountOf(recheck) == 0)
//                addWorker(null, false);
//        }
//        else if (!addWorker(command, false))
//            reject(command);
//    }
//
//
//    public void shutdown() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            checkShutdownAccess();
//            advanceRunState(SHUTDOWN);
//            interruptIdleWorkers();
//            onShutdown(); // hook for ScheduledThreadPoolExecutor
//        } finally {
//            mainLock.unlock();
//        }
//        tryTerminate();
//    }
//
//
//    public List<Runnable> shutdownNow() {
//        List<Runnable> tasks;
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            checkShutdownAccess();
//            advanceRunState(STOP);
//            interruptWorkers();
//            tasks = drainQueue();
//        } finally {
//            mainLock.unlock();
//        }
//        tryTerminate();
//        return tasks;
//    }
//
//    public boolean isShutdown() {
//        return ! isRunning(ctl.get());
//    }
//
//
//    public boolean isTerminating() {
//        int c = ctl.get();
//        return ! isRunning(c) && runStateLessThan(c, TERMINATED);
//    }
//
//    public boolean isTerminated() {
//        return runStateAtLeast(ctl.get(), TERMINATED);
//    }
//
//    public boolean awaitTermination(long timeout, TimeUnit unit)
//            throws InterruptedException {
//        long nanos = unit.toNanos(timeout);
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            for (;;) {
//                if (runStateAtLeast(ctl.get(), TERMINATED))
//                    return true;
//                if (nanos <= 0)
//                    return false;
//                nanos = termination.awaitNanos(nanos);
//            }
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//
//    protected void finalize() {
//        shutdown();
//    }
//
//
//    public void setThreadFactory(ThreadFactory threadFactory) {
//        if (threadFactory == null)
//            throw new NullPointerException();
//        this.threadFactory = threadFactory;
//    }
//
//
//    public ThreadFactory getThreadFactory() {
//        return threadFactory;
//    }
//
//
//    public void setRejectedExecutionHandler(RejectedExecutionHandler handler) {
//        if (handler == null)
//            throw new NullPointerException();
//        this.handler = handler;
//    }
//
//    /**
//     * Returns the current handler for unexecutable tasks.
//     *
//     * @return the current handler
//     * @see #setRejectedExecutionHandler(RejectedExecutionHandler)
//     */
//    public RejectedExecutionHandler getRejectedExecutionHandler() {
//        return handler;
//    }
//
//
//    public void setCorePoolSize(int corePoolSize) {
//        if (corePoolSize < 0)
//            throw new IllegalArgumentException();
//        int delta = corePoolSize - this.corePoolSize;
//        this.corePoolSize = corePoolSize;
//        if (workerCountOf(ctl.get()) > corePoolSize)
//            interruptIdleWorkers();
//        else if (delta > 0) {
//            // We don't really know how many new threads are "needed".
//            // As a heuristic, prestart enough new workers (up to new
//            // core size) to handle the current number of tasks in
//            // queue, but stop if queue becomes empty while doing so.
//            int k = Math.min(delta, workQueue.size());
//            while (k-- > 0 && addWorker(null, true)) {
//                if (workQueue.isEmpty())
//                    break;
//            }
//        }
//    }
//
//    /**
//     * Returns the core number of threads.
//     *
//     * @return the core number of threads
//     * @see #setCorePoolSize
//     */
//    public int getCorePoolSize() {
//        return corePoolSize;
//    }
//
//    /**
//     * Starts a core thread, causing it to idly wait for work. This
//     * overrides the default policy of starting core threads only when
//     * new tasks are executed. This method will return {@code false}
//     * if all core threads have already been started.
//     *
//     * @return {@code true} if a thread was started
//     */
//    public boolean prestartCoreThread() {
//        return workerCountOf(ctl.get()) < corePoolSize &&
//                addWorker(null, true);
//    }
//
//    /**
//     * Same as prestartCoreThread except arranges that at least one
//     * thread is started even if corePoolSize is 0.
//     */
//    void ensurePrestart() {
//        int wc = workerCountOf(ctl.get());
//        if (wc < corePoolSize)
//            addWorker(null, true);
//        else if (wc == 0)
//            addWorker(null, false);
//    }
//
//    /**
//     * Starts all core threads, causing them to idly wait for work. This
//     * overrides the default policy of starting core threads only when
//     * new tasks are executed.
//     *
//     * @return the number of threads started
//     */
//    public int prestartAllCoreThreads() {
//        int n = 0;
//        while (addWorker(null, true))
//            ++n;
//        return n;
//    }
//
//    /**
//     * Returns true if this pool allows core threads to time out and
//     * terminate if no tasks arrive within the keepAlive time, being
//     * replaced if needed when new tasks arrive. When true, the same
//     * keep-alive policy applying to non-core threads applies also to
//     * core threads. When false (the default), core threads are never
//     * terminated due to lack of incoming tasks.
//     *
//     * @return {@code true} if core threads are allowed to time out,
//     *         else {@code false}
//     *
//     * @since 1.6
//     */
//    public boolean allowsCoreThreadTimeOut() {
//        return allowCoreThreadTimeOut;
//    }
//
//
//    public void allowCoreThreadTimeOut(boolean value) {
//        if (value && keepAliveTime <= 0)
//            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
//        if (value != allowCoreThreadTimeOut) {
//            allowCoreThreadTimeOut = value;
//            if (value)
//                interruptIdleWorkers();
//        }
//    }
//
//
//    public void setMaximumPoolSize(int maximumPoolSize) {
//        if (maximumPoolSize <= 0 || maximumPoolSize < corePoolSize)
//            throw new IllegalArgumentException();
//        this.maximumPoolSize = maximumPoolSize;
//        if (workerCountOf(ctl.get()) > maximumPoolSize)
//            interruptIdleWorkers();
//    }
//
//
//    public int getMaximumPoolSize() {
//        return maximumPoolSize;
//    }
//
//    public void setKeepAliveTime(long time, TimeUnit unit) {
//        if (time < 0)
//            throw new IllegalArgumentException();
//        if (time == 0 && allowsCoreThreadTimeOut())
//            throw new IllegalArgumentException("Core threads must have nonzero keep alive times");
//        long keepAliveTime = unit.toNanos(time);
//        long delta = keepAliveTime - this.keepAliveTime;
//        this.keepAliveTime = keepAliveTime;
//        if (delta < 0)
//            interruptIdleWorkers();
//    }
//
//
//    public long getKeepAliveTime(TimeUnit unit) {
//        return unit.convert(keepAliveTime, TimeUnit.NANOSECONDS);
//    }
//
//
//    public BlockingQueue<Runnable> getQueue() {
//        return workQueue;
//    }
//
//    public boolean remove(Runnable task) {
//        boolean removed = workQueue.remove(task);
//        tryTerminate(); // In case SHUTDOWN and now empty
//        return removed;
//    }
//
//    public void purge() {
//        final BlockingQueue<Runnable> q = workQueue;
//        try {
//            Iterator<Runnable> it = q.iterator();
//            while (it.hasNext()) {
//                Runnable r = it.next();
//                if (r instanceof Future<?> && ((Future<?>)r).isCancelled())
//                    it.remove();
//            }
//        } catch (ConcurrentModificationException fallThrough) {
//            // Take slow path if we encounter interference during traversal.
//            // Make copy for traversal and call remove for cancelled entries.
//            // The slow path is more likely to be O(N*N).
//            for (Object r : q.toArray())
//                if (r instanceof Future<?> && ((Future<?>)r).isCancelled())
//                    q.remove(r);
//        }
//
//        tryTerminate(); // In case SHUTDOWN and now empty
//    }
//
//
//    public int getPoolSize() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            // Remove rare and surprising possibility of
//            // isTerminated() && getPoolSize() > 0
//            return runStateAtLeast(ctl.get(), TIDYING) ? 0
//                    : workers.size();
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//
//    public int getActiveCount() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            int n = 0;
//            for (Worker w : workers)
//                if (w.isLocked())
//                    ++n;
//            return n;
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//
//    public int getLargestPoolSize() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            return largestPoolSize;
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//    public long getTaskCount() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            long n = completedTaskCount;
//            for (Worker w : workers) {
//                n += w.completedTasks;
//                if (w.isLocked())
//                    ++n;
//            }
//            return n + workQueue.size();
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//
//    public long getCompletedTaskCount() {
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            long n = completedTaskCount;
//            for (Worker w : workers)
//                n += w.completedTasks;
//            return n;
//        } finally {
//            mainLock.unlock();
//        }
//    }
//
//
//    public String toString() {
//        long ncompleted;
//        int nworkers, nactive;
//        final ReentrantLock mainLock = this.mainLock;
//        mainLock.lock();
//        try {
//            ncompleted = completedTaskCount;
//            nactive = 0;
//            nworkers = workers.size();
//            for (Worker w : workers) {
//                ncompleted += w.completedTasks;
//                if (w.isLocked())
//                    ++nactive;
//            }
//        } finally {
//            mainLock.unlock();
//        }
//        int c = ctl.get();
//        String rs = (runStateLessThan(c, SHUTDOWN) ? "Running" :
//                (runStateAtLeast(c, TERMINATED) ? "Terminated" :
//                        "Shutting down"));
//        return super.toString() +
//                "[" + rs +
//                ", pool size = " + nworkers +
//                ", active threads = " + nactive +
//                ", queued tasks = " + workQueue.size() +
//                ", completed tasks = " + ncompleted +
//                "]";
//    }
//
//
//
//
//    protected void beforeExecute(Thread t, Runnable r) { }
//
//
//    protected void afterExecute(Runnable r, Throwable t) { }
//
//    /**
//     * Method invoked when the Executor has terminated.  Default
//     * implementation does nothing. Note: To properly nest multiple
//     * overridings, subclasses should generally invoke
//     * {@code super.terminated} within this method.
//     */
//    protected void terminated() { }
//
//    /* Predefined RejectedExecutionHandlers */
//
//    /**
//     * A handler for rejected tasks that runs the rejected task
//     * directly in the calling thread of the {@code execute} method,
//     * unless the executor has been shut down, in which case the task
//     * is discarded.
//     */
//    public static class CallerRunsPolicy implements RejectedExecutionHandler {
//        /**
//         * Creates a {@code CallerRunsPolicy}.
//         */
//        public CallerRunsPolicy() { }
//
//        /**
//         * Executes task r in the caller's thread, unless the executor
//         * has been shut down, in which case the task is discarded.
//         *
//         * @param r the runnable task requested to be executed
//         * @param e the executor attempting to execute this task
//         */
//        public void rejectedExecution(Runnable r, java.util.concurrent.ThreadPoolExecutor e) {
//            if (!e.isShutdown()) {
//                r.run();
//            }
//        }
//    }
//
//    /**
//     * A handler for rejected tasks that throws a
//     * {@code RejectedExecutionException}.
//     */
//    public static class AbortPolicy implements RejectedExecutionHandler {
//        /**
//         * Creates an {@code AbortPolicy}.
//         */
//        public AbortPolicy() { }
//
//        /**
//         * Always throws RejectedExecutionException.
//         *
//         * @param r the runnable task requested to be executed
//         * @param e the executor attempting to execute this task
//         * @throws RejectedExecutionException always
//         */
//        public void rejectedExecution(Runnable r, java.util.concurrent.ThreadPoolExecutor e) {
//            throw new RejectedExecutionException("Task " + r.toString() +
//                    " rejected from " +
//                    e.toString());
//        }
//    }
//
//    /**
//     * A handler for rejected tasks that silently discards the
//     * rejected task.
//     */
//    public static class DiscardPolicy implements RejectedExecutionHandler {
//        /**
//         * Creates a {@code DiscardPolicy}.
//         */
//        public DiscardPolicy() { }
//
//        /**
//         * Does nothing, which has the effect of discarding task r.
//         *
//         * @param r the runnable task requested to be executed
//         * @param e the executor attempting to execute this task
//         */
//        public void rejectedExecution(Runnable r, java.util.concurrent.ThreadPoolExecutor e) {
//        }
//    }
//
//    /**
//     * A handler for rejected tasks that discards the oldest unhandled
//     * request and then retries {@code execute}, unless the executor
//     * is shut down, in which case the task is discarded.
//     */
//    public static class DiscardOldestPolicy implements RejectedExecutionHandler {
//        /**
//         * Creates a {@code DiscardOldestPolicy} for the given executor.
//         */
//        public DiscardOldestPolicy() { }
//
//        /**
//         * Obtains and ignores the next task that the executor
//         * would otherwise execute, if one is immediately available,
//         * and then retries execution of task r, unless the executor
//         * is shut down, in which case task r is instead discarded.
//         *
//         * @param r the runnable task requested to be executed
//         * @param e the executor attempting to execute this task
//         */
//        public void rejectedExecution(Runnable r, java.util.concurrent.ThreadPoolExecutor e) {
//            if (!e.isShutdown()) {
//                e.getQueue().poll();
//                e.execute(r);
//            }
//        }
//    }
//}
