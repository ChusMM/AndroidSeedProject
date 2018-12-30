package com.iecisa.androidseed.util;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {

    private final Activity mActivity;

    private final RequestOptions mDefaultRequestOptions;

    public ImageLoader(Activity activity) {
        mActivity = activity;

        mDefaultRequestOptions = new RequestOptions()
                .centerCrop();
    }

    public void loadImage(String uri, ImageView target) {
        Glide.with(mActivity)
                .net
                .load(uri)
                .apply(mDefaultRequestOptions)
                .into(target);
    }
}

/*    public static void loadFromUrl(final String url, final ImageView imageView) {
        try {
            Picasso.with(imageView.getContext()).load(url)
                    .fit()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() { }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            try {
                                Picasso.with(imageView.getContext()).load(url)
                                        .fit()
                                        .placeholder(R.drawable.placeholder)
                                        .error(R.drawable.placeholder)
                                        .into(imageView);
                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public static void loadFromUrlBy4_3Ratio(final String url, final ImageView imageView,
                                             final int placeHolder) {
        try {
            ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();

            final int widthPx = imageView.getMeasuredWidth() != 0 ?
                    imageView.getMeasuredWidth() : 500;
            final int resizedHeight =  (3 * widthPx) / 4;

            layoutParams.height = resizedHeight;
            imageView.setLayoutParams(layoutParams);

            imageView.setVisibility(View.VISIBLE);

            Picasso.with(imageView.getContext()).load(url)
                    .placeholder(placeHolder)
                    .error(placeHolder)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .resize(widthPx, resizedHeight)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() { }

                        @Override
                        public void onError() {
                            try {
                                Picasso.with(imageView.getContext()).load(url)
                                        .placeholder(placeHolder)
                                        .error(placeHolder)
                                        .resize(widthPx, resizedHeight)
                                        .into(imageView);
                            } catch (Exception e) {
                                Log.e(TAG, e.toString());
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    public static void setImageCircularBitmap(ImageView imageView, Bitmap bitmap) {
        try {
            Bitmap circularBitMap = getCroppedBitmap(bitmap);
            imageView.setImageBitmap(circularBitMap);
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
        }
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
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
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }
*/
