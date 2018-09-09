package com.squareup.picasso;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

class Stats {
    private static final int BITMAP_DECODE_FINISHED = 2;
    private static final int BITMAP_TRANSFORMED_FINISHED = 3;
    private static final int CACHE_HIT = 0;
    private static final int CACHE_MISS = 1;
    private static final int DOWNLOAD_FINISHED = 4;
    private static final String STATS_THREAD_NAME = "Picasso-Stats";
    long averageDownloadSize;
    long averageOriginalBitmapSize;
    long averageTransformedBitmapSize;
    final Cache cache;
    long cacheHits;
    long cacheMisses;
    int downloadCount;
    final Handler handler;
    int originalBitmapCount;
    final HandlerThread statsThread = new HandlerThread(STATS_THREAD_NAME, 10);
    long totalDownloadSize;
    long totalOriginalBitmapSize;
    long totalTransformedBitmapSize;
    int transformedBitmapCount;

    private static class StatsHandler extends Handler {
        private final Stats stats;

        public StatsHandler(Looper looper, Stats stats) {
            super(looper);
            this.stats = stats;
        }

        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case Stats.CACHE_HIT /*0*/:
                    this.stats.performCacheHit();
                    return;
                case Stats.CACHE_MISS /*1*/:
                    this.stats.performCacheMiss();
                    return;
                case Stats.BITMAP_DECODE_FINISHED /*2*/:
                    this.stats.performBitmapDecoded((long) msg.arg1);
                    return;
                case Stats.BITMAP_TRANSFORMED_FINISHED /*3*/:
                    this.stats.performBitmapTransformed((long) msg.arg1);
                    return;
                case Stats.DOWNLOAD_FINISHED /*4*/:
                    this.stats.performDownloadFinished((Long) msg.obj);
                    return;
                default:
                    Picasso.HANDLER.post(new Runnable() {
                        public void run() {
                            throw new AssertionError("Unhandled stats message." + msg.what);
                        }
                    });
                    return;
            }
        }
    }

    Stats(Cache cache) {
        this.cache = cache;
        this.statsThread.start();
        Utils.flushStackLocalLeaks(this.statsThread.getLooper());
        this.handler = new StatsHandler(this.statsThread.getLooper(), this);
    }

    void dispatchBitmapDecoded(Bitmap bitmap) {
        processBitmap(bitmap, BITMAP_DECODE_FINISHED);
    }

    void dispatchBitmapTransformed(Bitmap bitmap) {
        processBitmap(bitmap, BITMAP_TRANSFORMED_FINISHED);
    }

    void dispatchDownloadFinished(long size) {
        this.handler.sendMessage(this.handler.obtainMessage(DOWNLOAD_FINISHED, Long.valueOf(size)));
    }

    void dispatchCacheHit() {
        this.handler.sendEmptyMessage(CACHE_HIT);
    }

    void dispatchCacheMiss() {
        this.handler.sendEmptyMessage(CACHE_MISS);
    }

    void shutdown() {
        this.statsThread.quit();
    }

    void performCacheHit() {
        this.cacheHits++;
    }

    void performCacheMiss() {
        this.cacheMisses++;
    }

    void performDownloadFinished(Long size) {
        this.downloadCount += CACHE_MISS;
        this.totalDownloadSize += size.longValue();
        this.averageDownloadSize = getAverage(this.downloadCount, this.totalDownloadSize);
    }

    void performBitmapDecoded(long size) {
        this.originalBitmapCount += CACHE_MISS;
        this.totalOriginalBitmapSize += size;
        this.averageOriginalBitmapSize = getAverage(this.originalBitmapCount, this.totalOriginalBitmapSize);
    }

    void performBitmapTransformed(long size) {
        this.transformedBitmapCount += CACHE_MISS;
        this.totalTransformedBitmapSize += size;
        this.averageTransformedBitmapSize = getAverage(this.originalBitmapCount, this.totalTransformedBitmapSize);
    }

    StatsSnapshot createSnapshot() {
        return new StatsSnapshot(this.cache.maxSize(), this.cache.size(), this.cacheHits, this.cacheMisses, this.totalDownloadSize, this.totalOriginalBitmapSize, this.totalTransformedBitmapSize, this.averageDownloadSize, this.averageOriginalBitmapSize, this.averageTransformedBitmapSize, this.downloadCount, this.originalBitmapCount, this.transformedBitmapCount, System.currentTimeMillis());
    }

    private void processBitmap(Bitmap bitmap, int what) {
        this.handler.sendMessage(this.handler.obtainMessage(what, Utils.getBitmapBytes(bitmap), CACHE_HIT));
    }

    private static long getAverage(int count, long totalSize) {
        return totalSize / ((long) count);
    }
}
