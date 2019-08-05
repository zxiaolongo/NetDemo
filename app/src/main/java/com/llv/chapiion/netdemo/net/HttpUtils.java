package com.llv.chapiion.netdemo.net;

import com.llv.chapiion.netdemo.net.client.MyOkHttpClient;
import com.llv.chapiion.netdemo.net.client.gson.GsonHandler;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {
    private static ResponseApi api;
    public static ResponseApi getInstance() {
        if (api == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConstants.SERVER)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(MyOkHttpClient.getSaveHttpClient())//自定义的OkhttpClient,添加拦截器，cookie,证书等
                    .addConverterFactory(GsonConverterFactory.create(GsonHandler.getNoExportGson()))//自定义gson转换方式
                    .build();
            api = retrofit.create(ResponseApi.class);
        }
        return api;
    }
}
