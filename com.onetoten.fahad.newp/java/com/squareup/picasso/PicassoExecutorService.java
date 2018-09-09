package com.squareup.picasso;

import android.net.NetworkInfo;
import com.onetoten.fahad.newp.R;
import com.onetoten.fahad.newp.Upload_Image;
import com.squareup.picasso.Picasso.Priority;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class PicassoExecutorService extends ThreadPoolExecutor {
    private static final int DEFAULT_THREAD_COUNT = 3;

    private static final class PicassoFutureTask extends FutureTask<BitmapHunter> implements Comparable<PicassoFutureTask> {
        private final BitmapHunter hunter;

        public PicassoFutureTask(BitmapHunter hunter) {
            super(hunter, null);
            this.hunter = hunter;
        }

        public int compareTo(PicassoFutureTask other) {
            Priority p1 = this.hunter.getPriority();
            Priority p2 = other.hunter.getPriority();
            return p1 == p2 ? this.hunter.sequence - other.hunter.sequence : p2.ordinal() - p1.ordinal();
        }
    }

    PicassoExecutorService() {
        super(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new PicassoThreadFactory());
    }

    void adjustThreadCount(NetworkInfo info) {
        if (info == null || !info.isConnectedOrConnecting()) {
            setThreadCount(DEFAULT_THREAD_COUNT);
            return;
        }
        switch (info.getType()) {
            case R.styleable.View_android_focusable /*0*/:
                switch (info.getSubtype()) {
                    case Upload_Image.REQUEST_IMAGE_CAPTURE /*1*/:
                    case R.styleable.View_paddingEnd /*2*/:
                        setThreadCount(1);
                        return;
                    case DEFAULT_THREAD_COUNT /*3*/:
                    case R.styleable.Toolbar_contentInsetStart /*4*/:
                    case R.styleable.Toolbar_contentInsetEnd /*5*/:
                    case R.styleable.Toolbar_contentInsetLeft /*6*/:
                    case R.styleable.Toolbar_titleMarginStart /*12*/:
                        setThreadCount(2);
                        return;
                    case R.styleable.Toolbar_titleMarginEnd /*13*/:
                    case R.styleable.Toolbar_titleMarginTop /*14*/:
                    case R.styleable.Toolbar_titleMarginBottom /*15*/:
                        setThreadCount(DEFAULT_THREAD_COUNT);
                        return;
                    default:
                        setThreadCount(DEFAULT_THREAD_COUNT);
                        return;
                }
            case Upload_Image.REQUEST_IMAGE_CAPTURE /*1*/:
            case R.styleable.Toolbar_contentInsetLeft /*6*/:
            case R.styleable.Toolbar_titleTextAppearance /*9*/:
                setThreadCount(4);
                return;
            default:
                setThreadCount(DEFAULT_THREAD_COUNT);
                return;
        }
    }

    private void setThreadCount(int threadCount) {
        setCorePoolSize(threadCount);
        setMaximumPoolSize(threadCount);
    }

    public Future<?> submit(Runnable task) {
        PicassoFutureTask ftask = new PicassoFutureTask((BitmapHunter) task);
        execute(ftask);
        return ftask;
    }
}
