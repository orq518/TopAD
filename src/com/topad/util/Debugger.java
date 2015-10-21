package com.topad.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Environment;
import android.util.Log;

public class Debugger {
    protected static final String TAG = "Debugger";

    private static boolean disable = false;

    public static void open() {
        disable = false;
    }

    public static void close() {
        disable = true;
    }

    /**
     *   结尾不要'/'
     **/
    public static void setPathDir(String dir) {
        SAVE_LOG_DIR_PATH = dir;
        SAVE_LOG_PATH = SAVE_LOG_DIR_PATH + "/log";
    }

    /**
     * check if the log is disabled
     *
     * @return
     */
    public static boolean isEnabled() {
        return !disable;
    }

    public static void setEnable(boolean toggle) {
        disable = !toggle;
    }

    private static boolean isToFile = true;

    /**
     * 保存LOG日志的目录
     */
    private static String SAVE_LOG_DIR_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + TAG;

    /**
     * 保存LOG日志的路径
     */
    private static String SAVE_LOG_PATH = SAVE_LOG_DIR_PATH + "/log";

    private final static int defaultSaveDays = 7;

    private final static ThreadLocal<PostingThreadState> currentPostingThreadState = new ThreadLocal<PostingThreadState>() {
        @Override
        protected PostingThreadState initialValue() {
            return new PostingThreadState();
        }
    };

    private static AsyncPoster asyncPoster = new AsyncPoster();
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();


    /**
     * send a VERBOSE log message.
     *
     * @param msg
     */
    public static void v(String tag, String msg) {
        if (isEnabled()) {
            Log.v(tag, buildMessage(tag, msg));
        }
    }

    /**
     * send a VERBOSE log message and log the exception
     *
     * @param msg
     * @param throwable
     */
    public static void v(String tag, String msg, Throwable throwable) {
        if (isEnabled()) {
            Log.v(tag, buildMessage(tag, msg), throwable);
        }
    }

    /**
     * send a DEBUG log message
     *
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (isEnabled()) {
            Log.d(tag, buildMessage(tag, msg));
        }
    }

    /**
     * send a DEBUG log message and log the exception
     *
     * @param msg
     * @param throwable
     */
    public static void d(String tag, String msg, Throwable throwable) {
        if (isEnabled()) {
            Log.d(tag, buildMessage(tag, msg), throwable);
        }
    }

    /**
     * send a INFO log message
     *
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (isEnabled()) {
            Log.i(tag, buildMessage(tag, msg));
        }
    }

    /**
     * send a INFO log message and log the exception
     *
     * @param msg
     * @param throwable
     */
    public static void i(String tag, String msg, Throwable throwable) {
        if (isEnabled()) {
            Log.i(tag, buildMessage(tag, msg), throwable);
        }
    }

    /**
     * send a ERROR log message
     *
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (isEnabled()) {
            Log.e(tag, buildMessage(tag, msg));
        }
    }

    /**
     * send a ERROR log message and log the exception
     *
     * @param msg
     * @param throwable
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (isEnabled()) {
            Log.e(tag, buildMessage(tag, msg), throwable);
        }
    }

    /**
     * send a WARN log message
     *
     * @param msg
     */
    public static void w(String tag, String msg) {
        if (isEnabled()) {
            Log.w(tag, buildMessage(tag, msg));
        }
    }

    /**
     * send a WARN log message and log the exception
     *
     * @param msg
     * @param throwable
     */
    public static void w(String tag, String msg, Throwable throwable) {
        if (isEnabled()) {
            Log.w(tag, buildMessage(tag, msg), throwable);
        }
    }

    /**
     * build the complete message
     *
     * @param message
     * @return
     */
    private static String buildMessage(String tag, String message) {
        StackTraceElement ste = new Throwable().fillInStackTrace().getStackTrace()[2];
        int myTid = android.os.Process.myTid();
        final StringBuffer sb = new StringBuffer();
        sb.append("thread(");
        sb.append(myTid);
        sb.append(":");
        sb.append(android.os.Process.getThreadPriority(myTid));
        sb.append(":");
        sb.append(Thread.currentThread().getName());
        sb.append(")");
        sb.append(ste.getClassName());
        sb.append(".");
        sb.append(ste.getMethodName());
        sb.append("\r\n\n");
        sb.append(message);
        if (isToFile) {
            post(new LogEvent(tag, sb.toString()));
        }
        return sb.toString();
    }

    private static void post(LogEvent event) {
        PostingThreadState postingState = currentPostingThreadState.get();
        List<LogEvent> eventQueue = postingState.eventQueue;
        eventQueue.add(event);

        if (postingState.isPosting) {
            return;
        } else {
            postingState.isPosting = true;
            if (postingState.canceled) {
                return;
            }
            try {
                while (!eventQueue.isEmpty()) {
                    postSingleEvent(eventQueue.remove(0), postingState);
                }
            } finally {
                postingState.isPosting = false;
            }
        }
    }

    private static void postSingleEvent(LogEvent event, PostingThreadState postingState) throws Error {
        postingState.event = event;
        try {
            asyncPoster.enqueue(event);
        } finally {
            postingState.event = null;
            postingState.canceled = false;
        }

    }

    /** For ThreadLocal, much faster to set (and get multiple values). */
    private final static class PostingThreadState {
        List<LogEvent> eventQueue = new ArrayList<LogEvent>();
        boolean isPosting;
        boolean canceled;
        LogEvent event;
    }

    private static class LogEvent{
        String tag;
        String msgText;

        public LogEvent(String tag, String msgText){
            this.tag = tag;
            this.msgText = msgText;
        }
    }

    private static class AsyncPoster implements Runnable {

        private final PendingPostQueue queue;

        AsyncPoster() {
            queue = new PendingPostQueue();
        }

        public void enqueue(LogEvent event) {
            PendingPost pendingPost = PendingPost.obtainPendingPost(event);
            queue.enqueue(pendingPost);
            executorService.execute(this);
        }

        @Override
        public void run() {
            PendingPost pendingPost = queue.poll();
            if(pendingPost == null) {
                throw new IllegalStateException("No pending post available");
            }
            toFile(pendingPost.event.tag, pendingPost.event.msgText);
            PendingPost.releasePendingPost(pendingPost);
        }

    }

    private static final class PendingPost {

        private final static List<PendingPost> pendingPostPool = new ArrayList<PendingPost>();
        PendingPost next;
        LogEvent event;

        private PendingPost(LogEvent event) {
            this.event = event;
        }

        static PendingPost obtainPendingPost(LogEvent event) {
            synchronized (pendingPostPool) {
                int size = pendingPostPool.size();
                if (size > 0) {
                    PendingPost pendingPost = pendingPostPool.remove(size - 1);
                    pendingPost.event = event;
                    pendingPost.next = null;
                    return pendingPost;
                }
            }
            return new PendingPost(event);
        }

        static void releasePendingPost(PendingPost pendingPost) {
            pendingPost.event = null;
            pendingPost.next = null;
            synchronized (pendingPostPool) {
                // Don't let the pool grow indefinitely
                if (pendingPostPool.size() < 10000) {
                    pendingPostPool.add(pendingPost);
                }
            }
        }

    }

    private static final class PendingPostQueue {
        private PendingPost head;
        private PendingPost tail;

        synchronized void enqueue(PendingPost pendingPost) {
            if (pendingPost == null) {
                throw new NullPointerException("null cannot be enqueued");
            }
            if (tail != null) {
                tail.next = pendingPost;
                tail = pendingPost;
            } else if (head == null) {
                head = tail = pendingPost;
            } else {
                throw new IllegalStateException("Head present, but no tail");
            }
            notifyAll();
        }

        synchronized PendingPost poll() {
            PendingPost pendingPost = head;
            if (head != null) {
                head = head.next;
                if (head == null) {
                    tail = null;
                }
            }
            return pendingPost;
        }

        synchronized PendingPost poll(int maxMillisToWait) throws InterruptedException {
            if (head == null) {
                wait(maxMillisToWait);
            }
            return poll();
        }

    }

    private static void toFile(String tag, String message) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date(System.currentTimeMillis()));
        File dir = new File(SAVE_LOG_DIR_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File logDir = new File(SAVE_LOG_PATH);
        if (!logDir.exists()) {
            if (!logDir.mkdirs()) {
                logDir = new File(SAVE_LOG_PATH);
                if (!logDir.exists()) {
                    logDir.mkdirs();
                }
            }
        }

        String dateLogDirPath = SAVE_LOG_PATH + "/" + date;
        File dateLogDir = new File(dateLogDirPath);
        if (!dateLogDir.exists()) {
            if (!dateLogDir.mkdirs()) {
                dateLogDir = new File(dateLogDirPath);
                if (!dateLogDir.exists()) {
                    dateLogDir.mkdirs();
                }
            }
        }


        File file = new File(dateLogDirPath + "/" + tag + "_" + date + ".txt");
        if (dir.exists() && dir.isDirectory()) {
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        date = sdf.format(new Date(System.currentTimeMillis() - defaultSaveDays * 24 * 3600 * 1000));
        String oldDateLogDirPath = SAVE_LOG_PATH + "/" + date;
        File delFile = new File(oldDateLogDirPath);
        if (delFile.exists()) {
//            delFile.delete();
            deleteSDCardFolder(delFile);
        }

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(new Date(System.currentTimeMillis()));
            if (file != null && file.exists() && file.canWrite()) {
                fw = new FileWriter(file, true);
                bw = new BufferedWriter(fw);
                bw.append(time + " " + message);
                bw.newLine();
                bw.flush();
                fw.flush();
            } else {
//				Logger.e("Log file is not exist or can't write");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (fw != null) {
                    fw.close();
                }
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception e) {
                // ignore exceptions generated by close()
            }
        }
    }

    private static void deleteSDCardFolder(File dir) {
        File to = new File(dir.getAbsolutePath() + System.currentTimeMillis() + "_r");
        dir.renameTo(to);
        if (to.isDirectory()) {
            String[] children = to.list();
            for (int i = 0; i < children.length; i++) {
                File temp = new File(to, children[i]);
                if (temp.isDirectory()) {
                    deleteSDCardFolder(temp);
                } else {
                    boolean b = temp.delete();
                    if (b == false) {
                        Log.d("deleteSDCardFolder", "DELETE FAIL");
                    }
                }
            }
            to.delete();
        }
    }
}