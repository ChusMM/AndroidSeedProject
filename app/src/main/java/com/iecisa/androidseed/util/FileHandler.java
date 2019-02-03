package com.iecisa.androidseed.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.annotation.NonNull;
import android.util.Log;

public class FileHandler extends Handler {
    private static final String TAG = FileHandler.class.getSimpleName();

    public interface WriteListener {
        void writeFinish(String filePath);
        void writeFailed(Throwable t);
    }

    public interface ReadListener {
        void readFinih(String content);
        void readFailed(Throwable t);
    }

    public static final int WRITE_OK = 0;
    public static final int WRITE_FAILED = 1;

    public static final int READ_OK = 2;
    public static final int READ_FAILED = 3;

    public static final String FILE_PATH_KEY = "file_path_key";
    public static final String FILE_CONTENT_KEY = "file_content_key";
    public static final String THROWABLE_KEY = "throwable_key";

    private WriteListener writeListener;
    private ReadListener readListener;

    public FileHandler(@NonNull WriteListener writeListener) {
        super(Looper.getMainLooper());
        this.writeListener = writeListener;
    }

    public FileHandler(@NonNull ReadListener readListener) {
        super(Looper.getMainLooper());
        this.readListener = readListener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        final Throwable t;
        switch (msg.what) {
            case WRITE_OK:
                final String path = msg.getData().getString(FILE_PATH_KEY);
                writeListener.writeFinish(path);
                break;
            case WRITE_FAILED:
                t = (Throwable) msg.getData().getSerializable(THROWABLE_KEY);
                writeListener.writeFailed(t);
                break;
            case READ_OK:
                final String content = msg.getData().getString(FILE_CONTENT_KEY);
                readListener.readFinih(content);
                break;
            case READ_FAILED:
                t = (Throwable) msg.getData().getSerializable(THROWABLE_KEY);
                readListener.readFailed(t);
                break;
            default:
                Log.e(TAG, "Message not handled");
        }
    }
}
