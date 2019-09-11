package lf.com.oniondemo.DataServices.RemoteData;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import lf.com.oniondemo.Utils.Constants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TranVoNB on 3/10/2017.
 */

public class RetrofitClient {
    private static Retrofit retrofit;
    private static RequestInterceptor requestInterceptor;

    public static void init(String baseUrl){
        if (TextUtils.isEmpty(baseUrl)){
            throw new InvalidParameterException("baseUrl can not be null or empty");
        }

        //this use for logging and tracking request/response
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //this use for additional headers (authorization, access-token,....)
        requestInterceptor = new RequestInterceptor();
        requestInterceptor.addHeader("Content-Type", "application/json");
        requestInterceptor.addHeader("Accept", "application/json");

        final OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .readTimeout(Constants.REQUEST_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(requestInterceptor)//execute first
                .addInterceptor(loggingInterceptor)//execute second
                .build();

        Gson gson = new GsonBuilder()
                //.registerTypeAdapter(Date.class, new DateTypeDeserializer())//register type adapter if need, eg data time format
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public static <S> S createService(Class<S> serviceClass) {
        if (retrofit == null) {
            throw new IllegalStateException("Must call init() before use");
        }

        return retrofit.create(serviceClass);
    }

    public static void addHeader(String name, String value) {
        if (requestInterceptor != null) {
            requestInterceptor.addHeader(name, value);
        }
    }

    public static void removeHeader(String name) {
        if (requestInterceptor != null) {
            requestInterceptor.removeHeader(name);
        }
    }

    public static boolean hasHeader(String name) {
        return requestInterceptor != null && requestInterceptor.hasHeader(name);
    }

    private static class RequestInterceptor implements Interceptor {

        private Hashtable<String, String> headers;

        void addHeader(String key, String value) {
            headers.put(key, value);
        }

        void removeHeader(String key) {
            headers.remove(key);
        }

        boolean hasHeader(String key) {
            return headers.containsKey(key);
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            if (headers != null && headers.size() > 0) {
                Enumeration<String> keys = headers.keys();
                while (keys.hasMoreElements()) {
                    String key = keys.nextElement();
                    String value = headers.get(key);
                    builder.header(key, value);
                }
            }
            return chain.proceed(builder.build());
        }
    }
}
