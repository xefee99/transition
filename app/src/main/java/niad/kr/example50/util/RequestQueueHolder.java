package niad.kr.example50.util;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by niad on 4/23/16.
 */
public class RequestQueueHolder {

    private static RequestQueueHolder instance;

    public static void init(Context context) {
        instance = new RequestQueueHolder(context);
    }

    public static RequestQueueHolder getInstance() {
        if (instance == null) {
            throw new RuntimeException("Call init(Context), before call getInstance() method");
        }
        return instance;
    }
    public static RequestQueue getRequestQueue() {
        return getInstance().getRequestQueueInner();
    }
    public static ImageLoader getImageLoader() {
        return instance.getImageLoaderInner();
    }


    private RequestQueue requestQueue;

    private ImageLoader imageLoader;
    private RequestQueueHolder(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        imageLoader = new ImageLoader(requestQueue, new BitmapLruCache(3000));
    }

    public RequestQueue getRequestQueueInner() {
        return this.requestQueue;
    }

    public ImageLoader getImageLoaderInner() {
        return imageLoader;
    }
}
