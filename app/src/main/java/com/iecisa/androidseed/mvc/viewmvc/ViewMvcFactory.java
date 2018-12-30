package com.iecisa.androidseed.mvc.viewmvc;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.iecisa.androidseed.mvc.heroes.HeroesListViewMvc;
import com.iecisa.androidseed.mvc.heroes.HeroesListViewMvcImpl;
import com.iecisa.androidseed.util.ImageLoader;
import com.iecisa.androidseed.util.ImageUtils;

public class ViewMvcFactory {

    private final LayoutInflater mLayoutInflater;
    private ImageLoader mImageLoader;
    private ImageUtils mImageUtils;

    //@Inject
    public ViewMvcFactory(LayoutInflater layoutInflater, ImageLoader imageLoader, ImageUtils imageUtils) {
        mLayoutInflater = layoutInflater;
        mImageLoader = imageLoader;
        mImageUtils = imageUtils;
    }

    /**
     * Instantiate a new implementation of MVC view. The returned instance will be casted to MVC view
     * type inferred by java's automatic type inference.
     * @param mvcViewClass the class of the required MVC view
     * @param container this container will be used as MVC view's parent. See {@link LayoutInflater#inflate(int, ViewGroup)}
     * @param <T> the type of the required MVC view
     * @return new instance of MVC view
     */
    public <T extends ViewMvc> T newInstance(Class<T> mvcViewClass, @Nullable ViewGroup container) {

        ViewMvc viewMvc;

        if (mvcViewClass == HeroesListViewMvc.class) {
            viewMvc = new HeroesListViewMvcImpl(mLayoutInflater, container, mImageLoader, mImageUtils);
        } else {
            throw new IllegalArgumentException("unsupported MVC view class " + mvcViewClass);
        }

        //noinspection unchecked
        return (T) viewMvc;
    }

}
