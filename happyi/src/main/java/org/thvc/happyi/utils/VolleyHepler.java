package org.thvc.happyi.utils;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.thvc.happyi.application.HappyiApplication;

public class VolleyHepler {
    private static VolleyHepler hepler;
    private RequestQueue queue;
    private ImageLoader imageLoader;

    private VolleyHepler() {
        queue = getRequestQueue();
        imageLoader = new ImageLoader(queue, AppImageCache.getInstance());
    }

    public static VolleyHepler getInstance() {
        if (hepler == null) {
            hepler = new VolleyHepler();
        }
        return hepler;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(HappyiApplication.getContext());
        }
        return queue;
    }

    public <T> void addRequest(Request<T> request) {
        getRequestQueue().add(request);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }
}
