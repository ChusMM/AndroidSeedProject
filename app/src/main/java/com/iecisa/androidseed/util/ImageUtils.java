package com.iecisa.androidseed.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.iecisa.androidseed.BuildConfig;
import com.iecisa.androidseed.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageUtils {

    private static final String TAG = ImageUtils.class.getCanonicalName();

    private final Context context;
    private final ExecutorService executorService;

    public ImageUtils(Context context) {
        this.context = context;
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @UiThread
    public void writeImageInLocalStorage(final Bitmap bitmap,
                                         final String imageName,
                                         final WriteFileHandler.Listener listener) {

        final WriteFileHandler handler = new WriteFileHandler(listener);

        final File directory = new File(context.getCacheDir() + "/SeedImages");
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                Log.e(TAG, "Unable to create Image");
                final Throwable t = new IOException("Cannot create image Directory");
                handler.sendMessage(this.buildFailedMsg(t));
            }
        }

        this.executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    final String path = ImageUtils.this.writeInto(directory, imageName, bitmap);
                    bitmap.recycle();
                    handler.sendMessage(buildSuccessMsg(path));
                } catch (IOException | NullPointerException e) {
                    Log.e(TAG, e.toString());
                    handler.sendMessage(buildFailedMsg(e));
                }
            }
        });
    }

    private Message buildFailedMsg(Throwable throwable) {
        final Message msg = new Message();
        msg.what = WriteFileHandler.WRITE_FAILED;
        Bundle bundle = new Bundle();
        bundle.putSerializable(WriteFileHandler.THROWABLE_KEY, throwable);
        msg.setData(bundle);

        return msg;
    }

    private Message buildSuccessMsg(final String imagePath) {
        final Message msg = new Message();
        msg.what = WriteFileHandler.WRITE_FAILED;
        Bundle bundle = new Bundle();
        bundle.putString(WriteFileHandler.FILE_PATH_KEY, imagePath);
        msg.setData(bundle);

        return msg;
    }

    @UiThread
    public void actionShare(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        Uri uri = FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider",
                new File(imageFile.getAbsolutePath()));

        intent.putExtra(Intent.EXTRA_STREAM, uri);

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_image_via)));
    }

    @SuppressLint("WrongThread")
    @WorkerThread
    private String writeInto(final File directory, final String imageName,
                           final Bitmap bitmap) throws IOException, NullPointerException {

        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            throw new IOException("Write file called from main thread");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        File imageFile = new File(directory, imageName);
        boolean isCreated = imageFile.createNewFile();

        if (isCreated) {
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);

            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.close();
        } else {
            throw new IOException("Could not create image file");
        }

        return imageFile.getAbsolutePath();
    }
}

