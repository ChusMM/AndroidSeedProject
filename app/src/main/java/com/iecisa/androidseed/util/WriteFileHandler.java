package com.iecisa.androidseed.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;

public class WriteFileHandler extends Handler {
    private static final String TAG = WriteFileHandler.class.getSimpleName();

    interface Listener {
        void writeFinish(String filePath);
        void writeFailed(Throwable t);
    }

    public static final int WRITE_OK = 0;
    public static final int WRITE_FAILED = 1;

    public static final String FILE_PATH_KEY = "file_path_key";
    public static final String THROWABLE_KEY = "throwable_key";

    private final Listener listener;

    public WriteFileHandler(@NonNull Listener listener) {
        super(Looper.getMainLooper());
        this.listener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case WRITE_OK:
                final String path = msg.getData().getString(FILE_PATH_KEY);
                listener.writeFinish(path);
                break;
            case WRITE_FAILED:
                final Throwable t = (Throwable) msg.getData().getSerializable(THROWABLE_KEY);
                listener.writeFailed(t);
                break;
            default:
                Log.e(TAG, "Message not handled");
        }
    }
}
