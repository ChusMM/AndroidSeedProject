package com.iecisa.androidseed.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import androidx.annotation.DrawableRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iecisa.androidseed.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public class ImageLoader {
    private static final String TAG = ImageLoader.class.getSimpleName();

    public void loadFromUrl(final String uri, final ImageView target) {
        this.getOfflineInstance(uri)
                .into(target, new Callback() {
                    @Override
                    public void onSuccess() { }

                    @Override
                    public void onError(Exception e) {
                        //Try again online if cache failed
                        ImageLoader.this.getStandardInstance(uri).into(target);
                    }
                });
    }

    private RequestCreator getStandardInstance(String uri) {
        return this.getStandardInstance(uri, R.drawable.placeholder);
    }

    private RequestCreator getStandardInstance(final String uri, @DrawableRes final int placeHolder) {
        return Picasso.get()
                .load(uri)
                .placeholder(placeHolder)
                .error(placeHolder);
    }

    private RequestCreator getOfflineInstance(String uri) {
        return this.getStandardInstance(uri).networkPolicy(NetworkPolicy.OFFLINE);
    }

    private RequestCreator getOfflineInstance(String uri, @DrawableRes int placeHolder) {
        return this.getStandardInstance(uri, placeHolder).networkPolicy(NetworkPolicy.OFFLINE);
    }

    public void loadFromUrlBy43AspectRatio(final String uri,
                                           final ImageView target,
                                           final int placeHolder) {

        ViewGroup.LayoutParams layoutParams = target.getLayoutParams();

        final int widthPx = target.getMeasuredWidth() != 0 ?
                target.getMeasuredWidth() : 500;
        final int resizedHeight = (3 * widthPx) / 4;

        layoutParams.height = resizedHeight;
        target.setLayoutParams(layoutParams);

        target.setVisibility(View.VISIBLE);

        this.getOfflineInstance(uri, placeHolder)
                .resize(widthPx, resizedHeight)
                .into(target, new Callback() {
                    @Override
                    public void onSuccess() { }

                    @Override
                    public void onError(Exception e) {
                        ImageLoader.this.getStandardInstance(uri, placeHolder)
                                .resize(widthPx, resizedHeight)
                                .into(target);

                    }
                });
    }

    public void setImageCircularBitmap(ImageView imageView, Bitmap bitmap) {
        try {
            Bitmap circularBitMap = getCroppedBitmap(bitmap);
            imageView.setImageBitmap(circularBitMap);
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle((float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2,
                (float) bitmap.getWidth() / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }
}
