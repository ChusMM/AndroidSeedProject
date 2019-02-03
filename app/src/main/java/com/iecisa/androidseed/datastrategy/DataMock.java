package com.iecisa.androidseed.datastrategy;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.iecisa.androidseed.api.HeroListWrapper;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.util.FileHandler;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DataMock extends DataStrategy implements FileHandler.ReadListener {
    private static final String TAG = DataMock.class.getSimpleName();

    private static final String HEROES_MOCK_FILE = "heroes_mock.json";

    private HeroesListener listener;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public DataMock(DataFactory dataFactory, Context context) {
        super(dataFactory, context);
    }

    @Override
    public void queryHeroes(@NonNull HeroesListener listener) {
        this.listener = listener;
        final FileHandler fileHandler = new FileHandler(this);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                Message message;
                try {
                    message = readFileFromAssets(HEROES_MOCK_FILE);
                    fileHandler.sendMessage(message);
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    message = buildFailedMsg(e);
                    fileHandler.sendMessage(message);
                }
            }
        });
    }

    @Override
    protected void deleteAllHeroes() { }

    @Override
    protected void saveHeroes(List<SuperHero> superHeroes) { }

    @SuppressWarnings("SameParameterValue")
    @WorkerThread
    private Message readFileFromAssets(String assetsFileName) throws IOException {
        InputStream inputStream = super.context.getAssets().open(assetsFileName);

        File temp = new File(context.getCacheDir() + "/temp.json");

        //noinspection TryFinallyCanBeTryWithResources
        try {
            FileUtils.copyInputStreamToFile(inputStream, temp);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            throw e;
        } finally {
            inputStream.close();
        }

        try {
            String json = FileUtils.readFileToString(temp, "UTF-8");
            return this.buildSuccessMsg(json);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            throw e;
        } finally {
            if (!temp.delete()) {
                Log.d(TAG, "Could not delete temp file");
            }
        }
    }

    private Message buildFailedMsg(Throwable throwable) {
        final Message msg = new Message();
        msg.what = FileHandler.WRITE_FAILED;
        Bundle bundle = new Bundle();
        bundle.putSerializable(FileHandler.THROWABLE_KEY, throwable);
        msg.setData(bundle);

        return msg;
    }

    private Message buildSuccessMsg(final String content) {
        final Message msg = new Message();
        msg.what = FileHandler.READ_OK;
        Bundle bundle = new Bundle();
        bundle.putString(FileHandler.FILE_CONTENT_KEY, content);
        msg.setData(bundle);

        return msg;
    }

    @Override
    public void readFinih(String content) {
        List<SuperHero> superHeroes = dataFactory.superHeroesFromHeroListWrapper(
                HeroListWrapper.fromJson(content));
        listener.onQueryHeroesOk(superHeroes);
    }

    @Override
    public void readFailed(Throwable t) {
        listener.onQueryHeroesFailed();
    }
}
